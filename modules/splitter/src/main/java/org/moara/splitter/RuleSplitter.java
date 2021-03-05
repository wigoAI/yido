/*
 * Copyright (C) 2020 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moara.splitter;


import com.seomse.commons.data.BeginEnd;
import org.moara.splitter.processor.ConditionTerminatorProcessor;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorProcessor;
import org.moara.splitter.utils.Area;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 구분기 구현체
 *
 * @author wjrmffldrhrl
 */
class RuleSplitter implements Splitter {

    protected final TerminatorProcessor terminatorProcessor;
    protected final ExceptionAreaProcessor[] exceptionAreaProcessors;
    protected final List<String> exceptionWords;

    /**
     * 구분기 생성자
     * package-private 하기 때문에  SplitterManager에서만 접근 가능하다.
     *
     * @param terminatorProcessor 구분 동작을 수행하는 processor
     * @param exceptionAreaProcessors 예외 영역을 지정해주는 processor, 두 개 이상 적용시킬 수 있다.
     * @param exceptionWords 구분 수행 시 포함하지 않을 예외 단어들
     */
    RuleSplitter(TerminatorProcessor terminatorProcessor, List<ExceptionAreaProcessor> exceptionAreaProcessors,
                 List<String> exceptionWords) {
        this.terminatorProcessor = terminatorProcessor;
        this.exceptionAreaProcessors = exceptionAreaProcessors.toArray(new ExceptionAreaProcessor[0]);
        this.exceptionWords = exceptionWords;
    }

    @Override
    public BeginEnd[] split(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text is null or empty");
        }

        int[] splitPoint = getSplitPoint(text);

        return doSplit(splitPoint, text);
    }

    private int[] getSplitPoint(String text) {
        List<Area> exceptionAreas = new ArrayList<>();

        for (ExceptionAreaProcessor exceptionAreaProcessor : exceptionAreaProcessors) {
            exceptionAreas.addAll(exceptionAreaProcessor.find(text));
        }

        return terminatorProcessor.find(text, exceptionAreas);
    }

    private Area[] doSplit(int[] splitPoints, String inputData) {

        List<Area> areaList = new ArrayList<>();

        int beginPoint = 0;
        int endPoint;
        for (int splitPoint : splitPoints) {
            Area targetArea = getAreaWithOutEmpty(beginPoint, splitPoint, inputData);

            beginPoint = targetArea.getBegin();
            endPoint = targetArea.getEnd();

            // 해당 영역이 비어있으면 구분 영역으로 취급하지 않고 스킵한다.
            if (beginPoint == endPoint) { continue; }

            areaList.add(targetArea);
            beginPoint = endPoint;

        }

        if (beginPoint < inputData.length()) {
            Area targetArea = getAreaWithOutEmpty(beginPoint, inputData.length(), inputData);

            if (targetArea.getBegin() != targetArea.getEnd()) {
                areaList.add(targetArea);
            }
        }

        return areaList.toArray(new Area[0]);
    }

    /**
     * 구분점으로 나눈 문장에서 공백문자 (" ", "\n", "\t")를 제거하고
     * 구분기가 구분점을 포함하지 않는 설정일 때 해당 구분문자들도 제거한다.
     * @param begin 구분점 시작
     * @param end 구분점 끝
     * @param inputData 구분할 데이터
     * @return 제외 문자들이 제거된 인덱스 Area
     */
    private Area getAreaWithOutEmpty(int begin, int end, String inputData) {
        String targetString = inputData.substring(begin, end);

        findBegin:
        for (int i = 0; i < targetString.length();) {

            // 모든 제외 문자를 체크해도 없다면 Pass
            for (String exceptionWord : exceptionWords) {

                // 있다면 구분점과 체크 인덱스를 이동
                if (targetString.startsWith(exceptionWord, i)) {
                    begin += exceptionWord.length();
                    i += exceptionWord.length();
                    continue findBegin;
                }
            }
            break;
        }

        findEnd:
        for (int i = targetString.length() - 1; i > -1;) {
            for (String exceptionWord : exceptionWords) {
                if (targetString.startsWith(exceptionWord, i - exceptionWord.length() + 1)) {
                    end -= exceptionWord.length();
                    i -= exceptionWord.length();
                    continue findEnd;
                }
            }
            break;
        }

        if (begin >= end) {
            return new Area(begin);
        }

        return new Area(begin, end);
    }


    /**
     * 메모리 상의 구분 조건 추가
     * @param additionalSplitCondition 추가할 구분 조건
     */
    public void addSplitConditionInMemory(SplitCondition additionalSplitCondition) {
        ((ConditionTerminatorProcessor) terminatorProcessor).addSplitConditions(additionalSplitCondition);
    }

    /**
     * 구분 조건 제거
     * @param unnecessarySplitCondition 제거할 구분 조건
     */
    public void deleteSplitConditionInMemory(SplitCondition unnecessarySplitCondition) {

        ((ConditionTerminatorProcessor) terminatorProcessor).deleteSplitConditions(unnecessarySplitCondition);
    }

    /**
     * 유효성 추가
     * @param additionalValidations 추가할 유효성
     */
    public void addValidationInMemory(Validation additionalValidations) {
        ((ConditionTerminatorProcessor) terminatorProcessor).addValidation(additionalValidations);
    }

    /**
     * 유효성 제거
     * @param unnecessaryValidations 제거할 유효성
     */
    public void deleteValidationInMemory(Validation unnecessaryValidations) {
        ((ConditionTerminatorProcessor) terminatorProcessor).deleteValidation(unnecessaryValidations);
    }

    /**
     * Thread test
     */
    public static void main(String[] args) {

        String data = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다. 역시 토끼정 본 점 답죠?ㅎㅅㅎ 건물은 크지만 간판이 없기 때문에 지나칠 수 있으니 조심하세요 강남 토끼정의 내부 인테리어. 평일 저녁이었지만 강남역 맛집 답게 사람들이 많았어요. 전체적으로 편안하고 아늑한 공간으로 꾸며져 있었습니다ㅎㅎ 한 가지 아쉬웠던 건 조명이 너무 어두워 눈이 침침했던… 저희는 3층에 자리를 잡고 음식을 주문했습니다. 총 5명이서 먹고 싶은 음식 하나씩 골라 다양하게 주문했어요 첫 번째 준비된 메뉴는 토끼정 고로케와 깻잎 불고기 사라다를 듬뿍 올려 먹는 맛있는 밥입니다. 여러가지 메뉴를 한 번에 시키면 준비되는 메뉴부터 가져다 주더라구요. 토끼정 고로케 금방 튀겨져 나와 겉은 바삭하고 속은 촉촉해 맛있었어요! 깻잎 불고기 사라다는 불고기, 양배추, 버섯을 볶아 깻잎을 듬뿍 올리고 우엉 튀김을 곁들여 밥이랑 함께 먹는 메뉴입니다. 사실 전 고기를 안 먹어서 무슨 맛인지 모르겠지만.. 다들 엄청 잘 드셨습니다ㅋㅋ 이건 제가 시킨 촉촉한 고로케와 크림스튜우동. 강남 토끼정에서 먹은 음식 중에 이게 제일 맛있었어요!!! 크림소스를 원래 좋아하기도 하지만, 느끼하지 않게 부드럽고 달달한 스튜와 쫄깃한 우동면이 너무 잘 어울려 계속 손이 가더라구요. 사진을 보니 또 먹고 싶습니다 간사이 풍 연어 지라시입니다. 일본 간사이 지방에서 많이 먹는 떠먹는 초밥(지라시스시)이라고 하네요. 밑에 와사비 마요밥 위에 연어들이 담겨져 있어 코끝이 찡할 수 있다고 적혀 있는데, 난 와사비 맛 1도 모르겠던데…? 와사비를 안 좋아하는 저는 불행인지 다행인지 연어 지라시를 매우 맛있게 먹었습니다ㅋㅋㅋ 다음 메뉴는 달짝지근한 숯불 갈비 덮밥입니다! 간장 양념에 구운 숯불 갈비에 양파, 깻잎, 달걀 반숙을 터트려 비벼 먹으면 그 맛이 크.. (물론 전 안 먹었지만…다른 분들이 그렇다고 하더라구요ㅋㅋㅋㅋㅋㅋㅋ) 마지막 메인 메뉴 양송이 크림수프와 숯불떡갈비 밥입니다. 크림리조또를 베이스로 위에 그루통과 숯불로 구운 떡갈비가 올라가 있어요! 크림스튜 우동 만큼이나 대박 맛있습니다…ㅠㅠㅠㅠㅠㅠ (크림 소스면 다 좋아하는 거 절대 아닙니다ㅋㅋㅋㅋㅋㅋ) 강남 토끼정 요리는 다 맛있지만 크림소스 요리를 참 잘하는 거 같네요 요건 물만 마시기 아쉬워 시킨 뉴자몽과 밀키소다 딸기통통! 유자와 자몽의 맛을 함께 느낄 수 있는 뉴자몽은 상큼함 그 자체였어요. 하치만 저는 딸기통통 밀키소다가 더 맛있었습니다ㅎㅎ 밀키소다는 토끼정에서만 만나볼 수 있는 메뉴라고 하니 한 번 드셔보시길 추천할게요!! 강남 토끼정은 강남역 맛집답게 모든 음식들이 대체적으로 맛있었어요! 건물 위치도 강남 대로변에서 조금 떨어져 있어 내부 인테리어처럼 아늑한 느낌도 있었구요ㅎㅎ 기회가 되면 다들 꼭 들러보세요~";
//        String data = "오늘은 오빠와 오랜만에 충장로 맛집탐방을 다녀왔습니다~~ 날도 더우니 충장로 냉면 맛집으로 소문난 청년냉면에 가봤습니당!! 지도로 보면 어렵지만, 엔씨웨이브 신호등 건너서 바로 쭉 들어오다 보면 타코야키 맛집있고 조각피자 파는 그 골목으로 들어오다 보면입간판 세워져 있는 곳으로 우회전 해서 들어오면 됩니다!! (사거리 가기 전 좁은골목으로 우회전!) 요기 렌즈미, 점프노래방 사이 골목입니다! 청년냉면 내부 모습입니다~~ 정말 깔끔하죠?? 인테리어도 약간 한옥스타일? 대나무도 중간중간 있고~ 넓은 홀에 쇼파좌석도 있고 테이블 좌석도 있었어요! 구시청 맛집 청년냉면 메뉴판입니다~ 물냉,비냉, 코다리물냉,비냉 있구요~ 세트메뉴로 매운치즈등갈비찜+냉면도 있네요^^ (저희는 이걸로 주문!) 그리고 사이드메뉴로 왕만두와 석쇠구이, 대패삼겹도 있었어요!! (왕만두도 하나 추가주문ㅋㅋ) SNS 이벤트도 진행중이였어요^^ 충장로 냉면 맛집 청년냉면은 순수 \"자가제면\"으로 면, 다대기, 육수를 매장에서 직접 만드신대요!! 꿩고기 육수라니~~~~!! 정말 기대되는데요!! 그리고 한가지 더! 바로 테이블마다 무선충전기가 있다는 점^^ (오빠의 S10+는 무선충전이 되는데 제 노트8은 안되더라구요 ㅠㅠ) 주문을 하니 밑반찬이 차려졌습니다! 저희가 제일 좋아하는 흑임자드레싱 샐러드와~ 고추장아찌, 김치, 나물들이 나왔네요!! 매운치즈등갈비찜+냉면 세트와 왕만두가 나왔습니다!!! 만두는 원래 5개 나오는데 한개씩 먹어버렸네요 ㅎㅎ 등갈비찜은 식지않게 고체연료에 불 붙여서 올려주셨구요~~ 매콤해보이는 비쥬얼 최고죠~ㅋㅋ 사이드메뉴인 왕 만두 다섯개!! 크기가 정말 크더라구요 ㅎㅎ 냉면 나오기 전에 먼저 나왔는데 애피타이저로 딱인것같아요!! 꽉찬 속에 피도 얇아서 정말 맛있었어요!! 무조건 추천입니당 ㅎㅎㅎ 냉면에는 만두는 진리쥬~ 그리고 냉면과 함께 먹을 매운치즈등갈비찜!! 다 익혀 나와서 치즈만 녹으면 바로 먹으면 되데요 다른 냉면집에서는 석쇠구이랑 냉면을 같이 먹는데 충장로 냉면 맛집 청년냉면은 매운치즈등갈비찜과의 콜라보!! 매콤한 등갈비 냄새가 너무 좋았어요!! 약간 산장식닭갈비 냄새도 나면서 기분좋은 냄새~ㅎㅎ 양도 꽤 많죠?!! 2인분에 등갈비가 10개? 12개? 잘 기억은 안나지만 꽤 많이 들어있었어요^^ 치즈가 어느정도 녹고 한번 먹어볼까요~ 세상에... 살이 너무너무 부드러워서 젓가락으로 잘 벗겨지더라구요!! 등갈비 먹으면 뼈에 붙은 질긴 살이 항상 먹기 힘들었는데 구시청 맛집 청년냉면의 치즈등갈비찜은 젓가락만 대도 사르르 살이 알아서 떨어지더라구요!! 제가 주문한 비냉!! (육수는 따로 더 달라고 했어요^^) 과연 꿩고기 냉면의 맛은 어떨까요?? 들깨향이 고소하게 느껴지더라구요! 제일 특이한건 면발이였는데, 어느정도 쫄깃한건 좋지만 너무 끈질긴 면들 있잖아요??ㅎㅎ 청년냉면 냉면은 딱 적당히 쫄깃해서 먹기 좋았어요!! 오빠의 물냉!! 살얼음 낀 육수가 정말 시원해보이쥬~~~ 국물 간도 딱 맞아서 그냥 들고 후룩후룩 마셔도 짜지않고 맛있어요!! 역시 물냉면 면도 탱글탱글 너무 질기지 않은!! 꿩고기 육수라 그런지 더 감칠맛이 나더라구요~~ 물냉면 육수 진짜 장난아닙니다...! 계속 마시고 싶어요ㅠㅠ 냉면과도 잘 어울리는데, 그냥 공기밥 시켜서 같이 먹어도 너무 좋겠더라구요!! 저렴한 가격에 냉면과 갈비찜까지 같이 먹을 수 있는게 장점^^ 그리고나서 볶음밥까지 추가 주문해서 먹었습니당^^ 1인분인데도 양이 꽤 많죠ㅎㅎ 고기를 다 뜯어먹고 양념에 비벼도 되고, 한두개 정도 남겨서 고기만 발라서 같이 비벼먹어도 좋겠더라구요!!! 김치도 송송 썰고 김가루 쏵~ 뿌려서 먹는 볶음밥!!! 양념이 맛있으니 볶음밥도 당연히 맛있겠쥬ㅎㅎ 충장로 냉면 맛집 청년냉면!!! 직접뽑은 수제냉면과 꿩고기육수, 맛있는 매운치즈등갈비와 왕만두까지!!! 이제 슬슬 더워지는데, 저렴한 가격으로 시원하고 맛있는 수제냉면 먹으러 꼭 가보시길 추천드립니당~ㅎㅎ";
//        String data = "어느 날 부턴가 의문의 목소리가 엘사를 부르고, 평화로운 아렌델 왕국을 위협한다. 트롤은 모든 것은 과거에서 시작되었음을 알려주며 엘사의 힘의 비밀과 진실을 찾아 떠나야한다고 조언한다. 위험에 빠진 아렌델 왕국을 구해야만 하는 엘사와 안나는 숨겨진 과거의 진실을 찾아 크리스토프, 올라프 그리고 스벤과 함께 위험천만한 놀라운 모험을 떠나게 된다. 자신의 힘을 두려워했던 엘사는 이제 이 모험을 헤쳐나가기에 자신의 힘이 충분하다고 믿어야만 하는데… 본편이 너무 잘 완성되면, 그다음 후속편에 대한 기대는 자연히 커질 수 밖에 없다. 그로 인해 제아무리 최선을 다해 만든 후속편이라 해도 달라진 팬들의 눈높이를 채우기란 여간 쉬운 일이 아니다. <겨울왕국 2> 역시 너무 잘만든 전작의 영향 탓인지 이를 넘어서야 한다는 부담감 탓에 아쉬운 문제점들을 대거 드러냈다. 그렇지만 결과적으로 나쁜 후속편은 아니었으며, 부담 없이 볼만한 요소와 재미는 여전해 <겨울왕국>의 열혈팬이라면 충분히 흥미롭게 감상할 것이다. 아렌델 왕국의 기원과 엘사의 마법에 대한 근원을 이야기했다는 점에서 <겨울왕국 2>의 이야기는 매우 야심 찬 방향으로 흘러가게 된다. 이제는 완벽한 가족으로서의 모습을 보여준 엘사, 안나, 크리스토프, 올라프 그리고 스벤의 모습을 보여주며 시종일관 행복한 모습만 보여줄것 같았던 영화는 갑자기 왕국을 덮친 사건을 통해 다시한 번 등장인물들의 새로운 모험을 예고한다. 전체적으로 새로운 모험을 예고하고 있지만, <겨울왕국 2>는 달라진 캐릭터들의 상황과 관계를 중점으로 이야기를 진행한다. 자신의 마법 능력을 통해 왕국의 위기를 극복하려는 엘사, 언니를 잊지않기 위해 고군분투 하는 안나, 그런 안나에게 속마음을 고백하려는 크리스토프 등 얽히고 설킨 관계가 이야기 흐름의 중심적인 역할을 하게된다. 그만큼 캐릭터들의 감정과 행동이 이야기의 행방을 좌지우지하는 '열쇠'가 되는데, 문제는 이 핵심 캐릭터들이 조금만 엇나가더라도 이야기는 엉망이 될 수 있다는 점이다. 안타깝게도 이번 후속편은 일부 캐릭터들의 부조화스러운 문제를 노출해 이야기 흐름에 영향을 주는 단점을 드러냈다. 다소 지나치게 느껴지는 가족애적인 행동, 이야기에 큰 영향을 주지 않는 캐릭터의 핵심적 행동을 끝까지 이어가려 한 탓에 전형적인 이야기를 이어가게 되는 문제점, 그로인해 감초같은 일부 캐릭터들의 존재감이 애매해져 전편과는 다른 형태의 이야기가 되어버렸다. 시리즈의 장점이라 할 수 있는 뮤지컬 음악은 여전히 좋고 매력적이지만, 아쉽게도 전작의 'Let it go'와 같은 떼창을 불러올 정도는 아니다. 전작이 유년시절, 인물들의 상황에 따른 적절한 음악을 만들어냈던것과 달리, <겨울왕국 2>의 주제곡들은 인물의 혼란스러운 감정, 의지, 내면과 같은 지극히 개인적 상황을 중심으로 노래한다. 멋지게 감상할 수 있는 노래들이지만, 전작의 경쾌하고, 귀엽고, 귀에 남을 후렴구가 담긴 곡이 없어 이번에는 듣는데 집중해야 한다. 전작의 노래들이 히트한것을 의식한 탓에 너무 많은 음악을 선보이려다 상황에 어울리지 않은 음악들을 난립한 점도 아쉽게 다가온다. 그런 상황속에서도 우리의 핵심적인 캐릭터들은 여전히 매력이 넘친다. 마법을 자유자재로 사용하며 잠재된 위험들을 헤쳐나가며 핵심 사건을 향해 나아가는 엘사는 이제 이 시리즈의 히어로 같은 존재이며, 안나 역시 전작보다 성장한 모습을 보여줘 중요한 순간에 큰 활약을 선보인다. <겨울왕국>은 이번 영화에서도 두 자매의 존재감과 관계가 큰 역할을 하고 있음을 보여주며, 두 사람의 활약과 관계가 다른 작품에서 보기 드문 정서와 이야기를 만들어내고 있음을 보여준다. 덕분에 단점으로 다가왔던 이야기의 흐름도 이러한 특성 덕분에 후반부의 특별한 상황과 결과를 만들어내는 복선이 되었고, 이 시리즈의 지속적인 장기화를 기대해도 될 법한 흐름을 만들어 냈다.  스케일이 커지고 새로운 마법, 상황을 그린만큼 이를 구현한 시각효과와 그림은 아름답고 우아하다. 중간중간 아쉬움을 느꼈더라도 이번 시리즈의 볼거리와 스케일을 감상한다면 그 아쉬움을 충분히 걷어낼 수 있다. 전작의 아성을 뛰어넘거나 완벽한 작품은 아니지만, <겨울왕국>만의 매력과 재미만큼은 여전한 작품이었으며, 마지막에 등장한 쿠키 영상을 통해 여전히 이 시리즈가 매력 있는 작품임을 각인시켜준다.";
        Splitter splitter = SplitterManager.getInstance().getSplitter();

        long total = 0;
        for(int i = 0 ; i < 100 ; i++) {
            long start = System.nanoTime();
            BeginEnd[] splitResult = splitter.split(data);
            long end = System.nanoTime();

            System.out.print("start : " + start + " end : " + end);
            System.out.println( " -> 실행 시간 : " + ( end - start )/1000000.0 + "ms");

            if (i > 4) {
                total += (end - start);

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("평균 : " + (total / 5)/1000000.0 );



    }
}