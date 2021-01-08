package org.moara.ner;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.person.PersonNamedEntityRecognizerManager;
import org.moara.ner.person.ReporterEntity;

import java.util.Arrays;
import java.util.List;

public class NamedEntityRecognizerTest {
    @Test
    public void testSimpleTargetWord() {
        String corpus = "【춘천=뉴시스】박종우 기자 = 선거법 위반 혐의를 받고 있는 이재수 춘천시장이 4일 오전 강원 춘천시 춘천지법 101호 법정에서 열리는 국민참여재판을 받기위해 출석하고 있다. 2019.04.04. jongwoo425@newsis.com【춘천=뉴시스】김경목 기자 = 춘천지검은 4일 공직선거법 위반 혐의로 불구속 기소돼 1심 재판에 회부된 이재수 춘천시장에게 벌금 250만원을 구형했다.\\r\\n\\r\\n이 시장은 춘천지법 재판부에 국민참여재판을 신청해 이날 결심 공판에 출석해 재판을 받았다.\\r\\n\\r\\n이 시장은 지난해 6·13 지방선거 선거운동 과정에서 예비후보 신분으로 춘천시청 부서들을 방문하면서 명함을 나눠주고 지지를 호소해 공직선거법상 호별방문 금지 조항을 위반한 혐의를 받고 있다.\\r\\n\\r\\nphoto31@newsis.com \\r\\n\\r\\n<저작권자ⓒ 공감언론 뉴시스통신사. 무단전재-재배포 금지.> ";

        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);
        NamedEntity[] answerList = {new ReporterEntity("박종우", 8, 11), new ReporterEntity("김경목", 139, 142)};
        Assertions.assertEquals(answerList.length, namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertTrue(namedEntity.equals(answerList[0]) || namedEntity.equals(answerList[1]));
        }

    }

    @Test
    public void testMultiEntity() {
        String corpus = "담양군까지 케이블카 운영을 추진하면서 이용객 감소가 불가피해졌다.\r\n\r\n울산·창원·포항·전주=이보람·안원준·장영태·김동욱 기자 boram@segye.com\r\n\r\n";
        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");


        List<String> answerList = Arrays.asList("이보람", "안원준", "장영태", "김동욱");
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertTrue(answerList.contains(namedEntity.getText()));
        }


        corpus = "직접 챙기는 상황이면 아무래도 밑에선 휴가를 다녀오기 부담스럽다\\\"며 \\\"단체장이 휴가를 미루는 바람에 덩달아 일정을 미루는 직원들도 있다\\\"고 말했다.   \\r\\n \\r\\n최모란·최은경·심석용 기자,[전국종합] moran@joongang.co.kr \\r\\n\\r\\n";
        namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");;

        answerList = Arrays.asList("최모란", "최은경", "심석용");
        namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertTrue(answerList.contains(namedEntity.getText()));
        }


        corpus = " 다시 효율적으로 (수사) 할 수 있을 방안을 찾겠다”고 밝혔다.  \\r\\n \\r\\n김민상?박사라?박태인 기자 kim.minsang@joongang.co.kr\\r\\n\\r\\n ■ 영장 기각 사유(박정길 영장전담";
        namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");;

        answerList = Arrays.asList("김민상", "박사라", "박태인");
        namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertTrue(answerList.contains(namedEntity.getText()));
        }

    }

    @Test
    public void testNoSpaceReporterRecognize() {
        String corpus = "이번 행사는 코로나19 관련 방역수칙 준수하에 진행됐다.\\n\\n/이태규기자 classic@sedaily.com\\n\\n▶ 엄마 기자 취재수첩 [서지혜의 건강한 육아]\\n\\n▶ 中투자 알짜정보만 쏙쏙  [니하오 중국증시]\\n\\n▶ 네이버 채널에서 '서울경제' 구독해주세요!\\n\\n저작권자 ⓒ 서울경제, 무단 전재 및 재배포 금지";
        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");;


        List<String> answerList = Arrays.asList("이태규");
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);


        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertTrue(answerList.contains(namedEntity.getText()));
        }
        Assertions.assertEquals(answerList.size(), namedEntities.length);
    }

}
