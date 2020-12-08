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

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.utils.Sentence;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 구분기 구현체
 *
 * @author wjrmffldrhrl
 */
class SentenceSplitter implements Splitter {

    protected final TerminatorAreaProcessor terminatorAreaProcessor;
    protected final ExceptionAreaProcessor[] exceptionAreaProcessors;

    /**
     * 구분기 생성자
     * package-private 하기 때문에  SplitterManager에서만 접근 가능하다.
     *
     *
     * @param terminatorAreaProcessor 구분 동작을 수행하는 processor
     * @param exceptionAreaProcessors 예외 영역을 지정해주는 processor, 두 개 이상 적용시킬 수 있다.
     */
    SentenceSplitter(TerminatorAreaProcessor terminatorAreaProcessor,
                     List<ExceptionAreaProcessor> exceptionAreaProcessors) {
        this.terminatorAreaProcessor = terminatorAreaProcessor;
        this.exceptionAreaProcessors = exceptionAreaProcessors.toArray(new ExceptionAreaProcessor[0]);
    }

    @Override
    public Sentence[] split(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text is null or empty");
        }

        List<Integer> splitPoint = getSplitPoint(text);

        return doSplit(splitPoint, text);

    }

    private List<Integer> getSplitPoint(String text) {
        List<Area> exceptionAreas = new ArrayList<>();
        for (ExceptionAreaProcessor exceptionAreaProcessor : exceptionAreaProcessors) {
            exceptionAreas.addAll(exceptionAreaProcessor.find(text));
        }

        return terminatorAreaProcessor.find(text, exceptionAreas);
    }


    private Sentence[] doSplit(List<Integer> splitPoint, String inputData) {
        Sentence[] result = new Sentence[splitPoint.size() + 1];
        int startIndex = 0;
        int resultIndex = 0;

        for(int point : splitPoint) {
            Sentence sentence = new Sentence(startIndex, point, inputData.substring(startIndex, point).trim());

            result[resultIndex++] = sentence;
            startIndex = point;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }

    public void addSplitConditions(List<SplitCondition> additionalSplitCondition) {
        terminatorAreaProcessor.addSplitConditions(additionalSplitCondition);
    }

    public void deleteSplitConditions(List<SplitCondition> unnecessarySplitCondition) {
        terminatorAreaProcessor.deleteSplitConditions(unnecessarySplitCondition);
    }

    public void addValidation(List<Validation> additionalValidations) {
        terminatorAreaProcessor.addValidation(additionalValidations);
    }

    public void deleteValidation(List<Validation> unnecessaryValidations) {
        terminatorAreaProcessor.deleteValidation(unnecessaryValidations);
    }



    /**
     * Thread test
     */
    public static void main(String[] args) {
        String data = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다. 역시 토끼정 본 점 답죠?ㅎㅅㅎ 건물은 크지만 간판이 없기 때문에 지나칠 수 있으니 조심하세요 강남 토끼정의 내부 인테리어. 평일 저녁이었지만 강남역 맛집 답게 사람들이 많았어요. 전체적으로 편안하고 아늑한 공간으로 꾸며져 있었습니다ㅎㅎ 한 가지 아쉬웠던 건 조명이 너무 어두워 눈이 침침했던... 저희는 3층에 자리를 잡고 음식을 주문했습니다. 총 5명이서 먹고 싶은 음식 하나씩 골라 다양하게 주문했어요 첫 번째 준비된 메뉴는 토끼정 고로케와 깻잎 불고기 사라다를 듬뿍 올려 먹는 맛있는 밥입니다. 여러가지 메뉴를 한 번에 시키면 준비되는 메뉴부터 가져다 주더라구요. 토끼정 고로케 금방 튀겨져 나와 겉은 바삭하고 속은 촉촉해 맛있었어요! 깻잎 불고기 사라다는 불고기, 양배추, 버섯을 볶아 깻잎을 듬뿍 올리고 우엉 튀김을 곁들여 밥이랑 함께 먹는 메뉴입니다. 사실 전 고기를 안 먹어서 무슨 맛인지 모르겠지만.. 다들 엄청 잘 드셨습니다ㅋㅋ 이건 제가 시킨 촉촉한 고로케와 크림스튜우동. 강남 토끼정에서 먹은 음식 중에 이게 제일 맛있었어요!!! 크림소스를 원래 좋아하기도 하지만, 느끼하지 않게 부드럽고 달달한 스튜와 쫄깃한 우동면이 너무 잘 어울려 계속 손이 가더라구요. 사진을 보니 또 먹고 싶습니다 간사이 풍 연어 지라시입니다. 일본 간사이 지방에서 많이 먹는 떠먹는 초밥(지라시스시)이라고 하네요. 밑에 와사비 마요밥 위에 연어들이 담겨져 있어 코끝이 찡할 수 있다고 적혀 있는데, 난 와사비 맛 1도 모르겠던데...? 와사비를 안 좋아하는 저는 불행인지 다행인지 연어 지라시를 매우 맛있게 먹었습니다ㅋㅋㅋ 다음 메뉴는 달짝지근한 숯불 갈비 덮밥입니다! 간장 양념에 구운 숯불 갈비에 양파, 깻잎, 달걀 반숙을 터트려 비벼 먹으면 그 맛이 크.. (물론 전 안 먹었지만...다른 분들이 그렇다고 하더라구요ㅋㅋㅋㅋㅋㅋㅋ) 마지막 메인 메뉴 양송이 크림수프와 숯불떡갈비 밥입니다. 크림리조또를 베이스로 위에 그루통과 숯불로 구운 떡갈비가 올라가 있어요! 크림스튜 우동 만큼이나 대박 맛있습니다...ㅠㅠㅠㅠㅠㅠ (크림 소스면 다 좋아하는 거 절대 아닙니다ㅋㅋㅋㅋㅋㅋ) 강남 토끼정 요리는 다 맛있지만 크림소스 요리를 참 잘하는 거 같네요 요건 물만 마시기 아쉬워 시킨 뉴자몽과 밀키소다 딸기통통! 유자와 자몽의 맛을 함께 느낄 수 있는 뉴자몽은 상큼함 그 자체였어요. 하치만 저는 딸기통통 밀키소다가 더 맛있었습니다ㅎㅎ 밀키소다는 토끼정에서만 만나볼 수 있는 메뉴라고 하니 한 번 드셔보시길 추천할게요!! 강남 토끼정은 강남역 맛집답게 모든 음식들이 대체적으로 맛있었어요! 건물 위치도 강남 대로변에서 조금 떨어져 있어 내부 인테리어처럼 아늑한 느낌도 있었구요ㅎㅎ 기회가 되면 다들 꼭 들러보세요~";
        List<String> data1000 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            data1000.add(data);
        }
        System.out.println("copy done ");

        Thread[] t = new Thread[6];

        for (int a = 0; a < t.length; a++) {
            int finalA = a;
            t[a] = new Thread(() -> {

                Splitter splitter = SplitterManager.getSplitterManager().getSplitter();
                for (int i = finalA * (data1000.size() / t.length); i < (finalA + 1) * data1000.size() / t.length; i++) {

                    splitter.split(data1000.get(i));
                }

            });
        }
//        long start = System.currentTimeMillis();
        for (Thread thread : t) {
            System.out.println("start");
            thread.start();
        }

//        long end = System.currentTimeMillis();

//        System.out.println( "Thread 실행 시간 : " + ( end - start )/1000.0 );

    }
}
