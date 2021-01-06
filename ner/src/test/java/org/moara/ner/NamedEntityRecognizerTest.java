package org.moara.ner;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamedEntityRecognizerTest {
    @Test
    public void testReporterRecognize() {
        String corpus = "【춘천=뉴시스】박종우 기자 = 선거법 위반 혐의를 받고 있는 이재수 춘천시장이 4일 오전 강원 춘천시 춘천지법 101호 법정에서 열리는 국민참여재판을 받기위해 출석하고 있다. 2019.04.04. jongwoo425@newsis.com【춘천=뉴시스】김경목 기자 = 춘천지검은 4일 공직선거법 위반 혐의로 불구속 기소돼 1심 재판에 회부된 이재수 춘천시장에게 벌금 250만원을 구형했다.\\r\\n\\r\\n이 시장은 춘천지법 재판부에 국민참여재판을 신청해 이날 결심 공판에 출석해 재판을 받았다.\\r\\n\\r\\n이 시장은 지난해 6·13 지방선거 선거운동 과정에서 예비후보 신분으로 춘천시청 부서들을 방문하면서 명함을 나눠주고 지지를 호소해 공직선거법상 호별방문 금지 조항을 위반한 혐의를 받고 있다.\\r\\n\\r\\nphoto31@newsis.com \\r\\n\\r\\n<저작권자ⓒ 공감언론 뉴시스통신사. 무단전재-재배포 금지.> ";

        NamedEntityRecognizer namedEntityRecognizer = new ReporterRecognizer();

        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(corpus);

        Assertions.assertEquals(2, namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            System.out.println(namedEntity.getValue());
            Assertions.assertTrue(namedEntity.getValue().equals("박종우") || namedEntity.getValue().equals("김경목"));
        }
    }
}
