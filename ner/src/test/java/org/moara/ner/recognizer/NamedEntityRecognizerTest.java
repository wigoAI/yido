package org.moara.ner.recognizer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;
import org.moara.ner.person.PersonNamedEntityRecognizerManager;
import org.moara.ner.person.ReporterEntity;

import java.util.Arrays;
import java.util.List;

public class NamedEntityRecognizerTest {
    @Test
    public void testSimpleTargetWord() {
        String text = "【춘천=뉴시스】박종우 기자 = 선거법 위반 혐의를 받고 있는 이재수 춘천시장이 4일 오전 강원 춘천시 춘천지법 101호 법정에서 열리는 국민참여재판을 받기위해 출석하고 있다. 2019.04.04. jongwoo425@newsis.com【춘천=뉴시스】김경목 기자 = 춘천지검은 4일 공직선거법 위반 혐의로 불구속 기소돼 1심 재판에 회부된 이재수 춘천시장에게 벌금 250만원을 구형했다.\\r\\n\\r\\n이 시장은 춘천지법 재판부에 국민참여재판을 신청해 이날 결심 공판에 출석해 재판을 받았다.\\r\\n\\r\\n이 시장은 지난해 6·13 지방선거 선거운동 과정에서 예비후보 신분으로 춘천시청 부서들을 방문하면서 명함을 나눠주고 지지를 호소해 공직선거법상 호별방문 금지 조항을 위반한 혐의를 받고 있다.\\r\\n\\r\\nphoto31@newsis.com \\r\\n\\r\\n<저작권자ⓒ 공감언론 뉴시스통신사. 무단전재-재배포 금지.> ";

        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(text);
        List<ReporterEntity> answerList = Arrays.asList(new ReporterEntity("박종우", 8, 11), new ReporterEntity("김경목", 139, 142));
        Assertions.assertEquals(answerList.size(), namedEntities.length);

        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));

    }

    @Test
    public void testMultiEntity() {
        String text = "담양군까지 케이블카 운영을 추진하면서 이용객 감소가 불가피해졌다.\r\n\r\n울산·창원·포항·전주=이보람·안원준·장영태·김동욱 기자 boram@segye.com\r\n\r\n";
        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");


        List<ReporterEntity> answerList = Arrays.asList(new ReporterEntity("이보람", 52, 55),
                new ReporterEntity("안원준", 56, 59),
                new ReporterEntity("장영태", 60, 63),
                new ReporterEntity("김동욱", 64, 67));
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(answerList.size(), namedEntities.length);


        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));




        text = "직접 챙기는 상황이면 아무래도 밑에선 휴가를 다녀오기 부담스럽다\\\"며 \\\"단체장이 휴가를 미루는 바람에 덩달아 일정을 미루는 직원들도 있다\\\"고 말했다.   \\r\\n \\r\\n최모란·최은경·심석용 기자,[전국종합] moran@joongang.co.kr \\r\\n\\r\\n";
        namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

        answerList = Arrays.asList(new ReporterEntity("최모란", 97, 100),
                new ReporterEntity("최은경", 101, 104),
                new ReporterEntity("심석용", 105, 108));

        namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));



        text = " 다시 효율적으로 (수사) 할 수 있을 방안을 찾겠다”고 밝혔다.  \\r\\n \\r\\n김민상?박사라?박태인 기자 kim.minsang@joongang.co.kr\\r\\n\\r\\n ■ 영장 기각 사유(박정길 영장전담";
        namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");;

        answerList = Arrays.asList(new ReporterEntity("김민상", 47,50),
                new ReporterEntity("박사라", 51, 54),
                new ReporterEntity("박태인", 55, 58));

        namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));


    }

    @Test
    public void testNoSpaceReporterRecognize() {
        String text = "이번 행사는 코로나19 관련 방역수칙 준수하에 진행됐다.\\n\\n/이태규기자 classic@sedaily.com\\n\\n▶ 엄마 기자 취재수첩 [서지혜의 건강한 육아]\\n\\n▶ 中투자 알짜정보만 쏙쏙  [니하오 중국증시]\\n\\n▶ 네이버 채널에서 '서울경제' 구독해주세요!\\n\\n저작권자 ⓒ 서울경제, 무단 전재 및 재배포 금지";
        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");;


        ReporterEntity answer = new ReporterEntity("이태규", 36, 39);
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(1, namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertEquals(answer, namedEntity);
        }

    }


    @Test
    public void testAdditionalWordsReporterRecognize() {
        String text = "[앵커]\\r\\n우리나라에서 가장 긴 인제~양양 터널 안에서 역주행한 운전자가 경찰에 잡혔습니다.\\r\\n\\r\\n다행히 사고가 나지는 않았는데 이 운전자, 심지어 운전하면서 술을 마셨다고 합니다.\\r\\n\\r\\n지환 기자입니다.\\r\\n\\r\\n[기자]\\r\\n한밤중 고속도로 터널 안, 도로 옆에 멈춰 선 3.5톤 화물차가 요리조리 방향을 틀며 차선을 막습니다.\\r\\n\\r\\n뒤따라오던 차량은 줄줄이 브레이크를 밟습니다.\\r\\n\\r\\n불법 유턴한 화물차, 역주행하며 터널을 빠져나갑니다.\\r\\n\\r\\n고속도로 터널에서 역주행한다는 신고가 들어온 건 자정이 조금 넘은 시각.\\r\\n\\r\\n화물차 운전기사가 불법 유턴과 역주행을 한 고속도로 터널입니다.\\r\\n\\r\\n총 길이 10.9㎞, 국내 최장 도로 터널인데요.\\r\\n\\r\\n차선은 2개지만 모두 같은 방향입니다.\\r\\n\\r\\n터널 내부가 어두운 상태에서 자칫 대형사고 가능성이 있었습니다.\\r\\n\\r\\n이번에도 역시나 음주운전이었습니다.\\r\\n\\r\\n운전기사 45살 A 씨의 혈중알코올농도는 면허 취소 기준을 크게 넘긴 0.186%.\\r\\n\\r\\n공작기계 장비를 싣고 강원도 강릉으로 가던 중 편의점에서 소주 2병을 샀는데, 고속도로 휴게소 주차장에서 마시고, 심지어 차를 운전하면서도 술병을 놓지 않았습니다.\\r\\n\\r\\n[강원지방경찰청 고속도로순찰대 관계자 : 휴게소에 (차를) 세워 놓고 주차장에서 먹기 시작해서 그다음 얘기로는 움직이면서도 먹고 한 병 반을 먹은 거예요. 가다가 술에 취하니까 지리를 잊어먹은 거죠. 어딘지. 옆에다 세워 놓고 똑바로 온 건지 생각하다가 잘못 온 줄 알고 잡아 돌린 거죠. 터널 안에서….]\\r\\n\\r\\n3㎞ 정도를 역주행한 A 씨는 터널 밖 갓길에서 순찰대에 잡혔습니다.\\r\\n\\r\\n경찰은 A 씨를 도로교통법 위반 혐의로 불구속 입건하고, 고의로 역주행했는지도 조사할 계획입니다.\\r\\n\\r\\nYTN 지환[haji@ytn.co.kr]입니다.";
        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

        ReporterEntity answer = new ReporterEntity("지환", 115, 117);
        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(1, namedEntities.length);

        for (NamedEntity namedEntity : namedEntities) {
            Assertions.assertEquals(answer, namedEntity);
        }

    }


    @Test
    public void testNoSpaceAtFront() {
        String text = "강지원 기자\\r\\n‘\\r\\n\\r\\n강지원 기자 stylo@hankookilbo.com";

        NamedEntityRecognizer namedEntityRecognizer = PersonNamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("reporter");

        List<ReporterEntity> answerList = Arrays.asList(new ReporterEntity("강지원", 0,3),
                new ReporterEntity("강지원", 19, 22));

        NamedEntity[] namedEntities = namedEntityRecognizer.recognize(text);

        Assertions.assertEquals(answerList.size(), namedEntities.length);

        Assertions.assertTrue(answerList.containsAll(Arrays.asList(namedEntities.clone())));
    }



}
