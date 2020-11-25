package org.moara.splitter.areaProcessor;

import com.github.wjrmffldrhrl.Area;
import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.processor.BracketAreaProcessor;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.role.*;
import org.moara.splitter.utils.Config;

import java.util.List;

import static org.junit.Assert.*;

public class AreaProcessorTest {

    @Test
    public void testExceptionAreaProcessor() {

        ExceptionAreaProcessor exceptionAreaProcessor = new BracketAreaProcessor();
        String inputData = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다. 역시 토끼정 본 점 답죠?ㅎㅅㅎ 건물은 크지만 간판이 없기 때문에 지나칠 수 있으니 조심하세요 강남 토끼정의 내부 인테리어. 평일 저녁이었지만 강남역 맛집 답게 사람들이 많았어요. 전체적으로 편안하고 아늑한 공간으로 꾸며져 있었습니다ㅎㅎ 한 가지 아쉬웠던 건 조명이 너무 어두워 눈이 침침했던... 저희는 3층에 자리를 잡고 음식을 주문했습니다. 총 5명이서 먹고 싶은 음식 하나씩 골라 다양하게 주문했어요 첫 번째 준비된 메뉴는 토끼정 고로케와 깻잎 불고기 사라다를 듬뿍 올려 먹는 맛있는 밥입니다. 여러가지 메뉴를 한 번에 시키면 준비되는 메뉴부터 가져다 주더라구요. 토끼정 고로케 금방 튀겨져 나와 겉은 바삭하고 속은 촉촉해 맛있었어요! 깻잎 불고기 사라다는 불고기, 양배추, 버섯을 볶아 깻잎을 듬뿍 올리고 우엉 튀김을 곁들여 밥이랑 함께 먹는 메뉴입니다. 사실 전 고기를 안 먹어서 무슨 맛인지 모르겠지만.. 다들 엄청 잘 드셨습니다ㅋㅋ 이건 제가 시킨 촉촉한 고로케와 크림스튜우동. 강남 토끼정에서 먹은 음식 중에 이게 제일 맛있었어요!!! 크림소스를 원래 좋아하기도 하지만, 느끼하지 않게 부드럽고 달달한 스튜와 쫄깃한 우동면이 너무 잘 어울려 계속 손이 가더라구요. 사진을 보니 또 먹고 싶습니다 간사이 풍 연어 지라시입니다. 일본 간사이 지방에서 많이 먹는 떠먹는 초밥(지라시스시)이라고 하네요. 밑에 와사비 마요밥 위에 연어들이 담겨져 있어 코끝이 찡할 수 있다고 적혀 있는데, 난 와사비 맛 1도 모르겠던데...? 와사비를 안 좋아하는 저는 불행인지 다행인지 연어 지라시를 매우 맛있게 먹었습니다ㅋㅋㅋ 다음 메뉴는 달짝지근한 숯불 갈비 덮밥입니다! 간장 양념에 구운 숯불 갈비에 양파, 깻잎, 달걀 반숙을 터트려 비벼 먹으면 그 맛이 크.. (물론 전 안 먹었지만...다른 분들이 그렇다고 하더라구요ㅋㅋㅋㅋㅋㅋㅋ) 마지막 메인 메뉴 양송이 크림수프와 숯불떡갈비 밥입니다. 크림리조또를 베이스로 위에 그루통과 숯불로 구운 떡갈비가 올라가 있어요! 크림스튜 우동 만큼이나 대박 맛있습니다...ㅠㅠㅠㅠㅠㅠ (크림 소스면 다 좋아하는 거 절대 아닙니다ㅋㅋㅋㅋㅋㅋ) 강남 토끼정 요리는 다 맛있지만 크림소스 요리를 참 잘하는 거 같네요 요건 물만 마시기 아쉬워 시킨 뉴자몽과 밀키소다 딸기통통! 유자와 자몽의 맛을 함께 느낄 수 있는 뉴자몽은 상큼함 그 자체였어요. 하치만 저는 딸기통통 밀키소다가 더 맛있었습니다ㅎㅎ 밀키소다는 토끼정에서만 만나볼 수 있는 메뉴라고 하니 한 번 드셔보시길 추천할게요!! 강남 토끼정은 강남역 맛집답게 모든 음식들이 대체적으로 맛있었어요! 건물 위치도 강남 대로변에서 조금 떨어져 있어 내부 인테리어처럼 아늑한 느낌도 있었구요ㅎㅎ 기회가 되면 다들 꼭 들러보세요~";

        int[] answerStart = {834, 1045, 1190};
        int[] answerEnd = {841, 1085, 1221};


        int index = 0;
        for (Area area : exceptionAreaProcessor.find(inputData)) {
            Assert.assertEquals(area.getStart(), answerStart[index]);
            Assert.assertEquals(area.getEnd(), answerEnd[index++]);
        }

    }

    @Test
    public void testOtherTerminatorAreaProcessor() {
        String[] validationList = {"V_N_B_TEST"};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_TEST", validationList);

        TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, new Config());
        String inputData = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다. ";
        int[] answer = {28, 62, 93, 73, 12, 25, 43};

        int index = 0;
        for (int i : terminatorAreaProcessor.find(inputData)) {
            assertEquals(i, answer[index++]);
        }
        assertNotEquals(0, terminatorAreaProcessor.find(inputData).size());

        inputData = "대회 결과, 대상은 신평고 3학년 최지민 학생이 차지했으며, 조리부문 금상과 제빵부문 금상은 신평고 3학년 김기중 학생이, 송탄고 3학년 황유진 학생이 각각 수상했다. 수상자에게는 상장과 메달, 상금 및 장학증서가 수여됐으며 대상은 상금 30만 원과 장학증서 15만 원, 금상은 상금 20만 원과 장학증서 100만 원 등이 전달됐다.";
        for (int i : terminatorAreaProcessor.find(inputData)) {

            assertEquals(i, answer[index++]);
        }
        assertNotEquals(0, terminatorAreaProcessor.find(inputData).size());

        inputData = "계획은 구성원들에게 함께 나아가야할 방향을 제시함과 동시에 목적지에 도달하기 위한 수단과 방법을 제공하는 안내서이자 설계도와 같다. 좋은 안내서가 목적한 곳에 무사히 도달할 수 있는데 중요한 역할을 하는 것처럼, 국민과 도민의 삶을 변화시키는 것에는 중앙정부와 지방정부에서 세우는 계획들이 무척 중요하다고 할 것이다.            ";

        for (int i : terminatorAreaProcessor.find(inputData)) {

            assertEquals(i, answer[index++]);
        }
        assertNotEquals(0, terminatorAreaProcessor.find(inputData).size());


        inputData = "안녕하세요 반갑습니다. 저는 조승현입니다!!! 여러분들을 만나서 너무 반가워요 제가 공부를 더 했다면 좋았을텐데...";

        for (int i : terminatorAreaProcessor.find(inputData)) {

            assertEquals(i, answer[index++]);
        }
        assertNotEquals(0, terminatorAreaProcessor.find(inputData).size());
    }

    @Test
    public void testFindSplitPointWithRegx() {
        String data = "지금부터 우리학교 규칙을 설명하겠습니다. 앞에 게시판을 보면 1. 교실에서는 조용히 하기 2. 복도에서 뛰어다니지 않기 3. 지각하면 벌금내기 입니다.";
        int[] answers = {34, 50, 67};
        String[] validationList = {};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("RSP_N_F_001", validationList);

        TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, new Config());

        assertEquals(answers.length, terminatorAreaProcessor.find(data).size());

        int answerIndex = 0;
        for (int splitPoint : terminatorAreaProcessor.find(data)) {
            assertEquals(answers[answerIndex++], splitPoint);

        }

    }

}