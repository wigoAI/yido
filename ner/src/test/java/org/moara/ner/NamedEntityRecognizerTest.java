package org.moara.ner;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NamedEntityRecognizerTest {
    @Test
    public void testReporterRecognize() {
        String corpus = "【춘천=뉴시스】박종우 기자 = 선거법 위반 혐의를 받고 있는 이재수 춘천시장이 4일 오전 강원 춘천시 춘천지법 101호 법정에서 열리는 국민참여재판을 받기위해 출석하고 있다. 2019.04.04. jongwoo425@newsis.com【춘천=뉴시스】김경목 기자 = 춘천지검은 4일 공직선거법 위반 혐의로 불구속 기소돼 1심 재판에 회부된 이재수 춘천시장에게 벌금 250만원을 구형했다.\\r\\n\\r\\n이 시장은 춘천지법 재판부에 국민참여재판을 신청해 이날 결심 공판에 출석해 재판을 받았다.\\r\\n\\r\\n이 시장은 지난해 6·13 지방선거 선거운동 과정에서 예비후보 신분으로 춘천시청 부서들을 방문하면서 명함을 나눠주고 지지를 호소해 공직선거법상 호별방문 금지 조항을 위반한 혐의를 받고 있다.\\r\\n\\r\\nphoto31@newsis.com \\r\\n\\r\\n<저작권자ⓒ 공감언론 뉴시스통신사. 무단전재-재배포 금지.> ";

        NamedEntityRecognizer namedEntityRecognizer = new ReporterRecognizer();

        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);
        List<String> answerList = Arrays.asList("박종우", "김경목");
        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }


        corpus = "[앵커]\\n\\n탈북민 단체가 그젯밤 경기도 파주에서 기습적으로 대북전단을 살포했습니다.\\n\\n대북 전단을 두고 북한의 반발이 거세지면서, 우리 정부가  대북전단 살포를 전면 금지했는데, 경찰의 감시를 피해 기습적으로 날려보낸 겁니다.\\n\\n김경진 기자가 보도합니다.\\n\\n[리포트]\\n\\n깜깜한 밤하늘 위로, 커다란 현수막이 달린 투명한 비닐 풍선이 올라갑니다.\\n\\n22일 밤 11시에서 12시 사이,  자유북한운동연합 회원 6명이  경기도 파주에서 기습적으로  대북전단을 살포했다고 주장하며 공개한 영상입니다.\\n\\n이 단체는 대북전단 50만 장과  소책자 500권, 1달러짜리 지폐 2천 장, SD 카드 천 개를 20개의 대형 풍선에 담아 보냈다고 주장했습니다.\\n\\n그런데 이 가운데 하나로 추정되는 풍선이 어제 오전 강원도 홍천에서 발견됐습니다.\\n\\n풍선에 달린 현수막이 탈북단체가 공개한 영상의 현수막과 같습니다.\\n\\n하지만 전단은 80여 장만 달려있었습니다.\\n\\n발견된 곳은  경기도 파주에서 동쪽으로 70여 킬로미터 가량 떨어진 곳입니다.\\n\\n풍선을 날린 곳이 파주가 맞다면 밤사이 서풍을 타고 날아간 셈입니다.\\n\\n[이병유/최초 신고자 : \\\"딱 삐라(전단) 같아서 사진을 보니까, 김정은, 김여정이 사진이 보이길래 바로 신고한 거죠.\\\"]\\n\\n풍향 등을 고려했을 땐, 북쪽으로 날아간 전단은 없는 것으로 당국은 파악하고 있다고 밝혔습니다.\\n\\n또 탈북 단체가 1달러 지폐를 넣었고, 전단의 양도 50만 장이라고 주장했지만  정황상 신뢰도가 낮은 것으로 보고 있습니다.\\n\\n[홍천경찰서 관계자/음성변조 : \\\"(지폐라든가 이런 것도 같이 들어 있었나요?)그런 것은 전혀 없고요. 풍선하고 현수막 사진, 비닐 봉지 안에 대북전단이 100매 안 되게 (있었습니다.)\\\"]\\n\\n통일부는  남북 간 긴장을 고조시키고 지역 주민의 안전을 위협한다며 전단 살포 행위에 대해  엄정한 대응조치를 경고했습니다.\\n\\nKBS 뉴스 김경진입니다.\",";
        namedEntityRecognizer = new ReporterRecognizer();

        namedEntities = namedEntityRecognizer.recognize(corpus);
        answerList = Arrays.asList("김경진");
        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }

    }

    @Test
    public void testReportersRecognize() {
        String corpus = "담양군까지 케이블카 운영을 추진하면서 이용객 감소가 불가피해졌다.\r\n\r\n울산·창원·포항·전주=이보람·안원준·장영태·김동욱 기자 boram@segye.com\r\n\r\n";
        NamedEntityRecognizer namedEntityRecognizer = new ReporterRecognizer();


        List<String> answerList = Arrays.asList("이보람", "안원준", "장영태", "김동욱");
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }


        corpus = "직접 챙기는 상황이면 아무래도 밑에선 휴가를 다녀오기 부담스럽다\\\"며 \\\"단체장이 휴가를 미루는 바람에 덩달아 일정을 미루는 직원들도 있다\\\"고 말했다.   \\r\\n \\r\\n최모란·최은경·심석용 기자,[전국종합] moran@joongang.co.kr \\r\\n\\r\\n";
        namedEntityRecognizer = new ReporterRecognizer();

        answerList = Arrays.asList("최모란", "최은경", "심석용");
        namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }


        corpus = " 다시 효율적으로 (수사) 할 수 있을 방안을 찾겠다”고 밝혔다.  \\r\\n \\r\\n김민상?박사라?박태인 기자 kim.minsang@joongang.co.kr\\r\\n\\r\\n ■ 영장 기각 사유(박정길 영장전담";
        namedEntityRecognizer = new ReporterRecognizer();

        answerList = Arrays.asList("김민상", "박사라", "박태인");
        namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }

    }

    @Test
    public void testNoSpaceReporterRecognize() {
        String corpus = "이번 행사는 코로나19 관련 방역수칙 준수하에 진행됐다.\\n\\n/이태규기자 classic@sedaily.com\\n\\n▶ 엄마 기자 취재수첩 [서지혜의 건강한 육아]\\n\\n▶ 中투자 알짜정보만 쏙쏙  [니하오 중국증시]\\n\\n▶ 네이버 채널에서 '서울경제' 구독해주세요!\\n\\n저작권자 ⓒ 서울경제, 무단 전재 및 재배포 금지";
        NamedEntityRecognizer namedEntityRecognizer = new ReporterRecognizer();


        List<String> answerList = Arrays.asList("이태규");
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);


        for (NamedEntity namedEntity : namedEntities) {
            System.out.println("reporter : " + namedEntity.getValue());
            Assertions.assertTrue(answerList.contains(namedEntity.getValue()));
        }
        Assertions.assertEquals(answerList.size(), namedEntities.length);
    }

}
