package org.moara.ner.test.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.moara.ner.entity.NamedEntity;
import org.moara.ner.exception.RecognizerNotFoundException;
import org.moara.ner.NamedEntityRecognizerManager;

public class ManagerTest {

    @Test
    public void testNotFoundException() {
        Assertions.assertThrows(RecognizerNotFoundException.class, () ->
            NamedEntityRecognizerManager.getInstance().getNamedEntityRecognizer("NOT_EXIST_RECOGNIZER"));
    }


    @Test
    public void testUseAllRecognizer() {
        String text = "[앵커]\\r\\n우리나라에서 가장 긴 인제~양양 터널 안에서 역주행한 운전자가 경찰에 잡혔습니다.\\r\\n\\r\\n다행히 사고가 나지는 않았는데 이 운전자, 심지어 운전하면서 술을 마셨다고 합니다.\\r\\n\\r\\n지환 기자입니다.\\r\\n\\r\\n[기자]\\r\\n한밤중 고속도로 터널 안, 도로 옆에 멈춰 선 3.5톤 화물차가 요리조리 방향을 틀며 차선을 막습니다.\\r\\n\\r\\n뒤따라오던 차량은 줄줄이 브레이크를 밟습니다.\\r\\n\\r\\n불법 유턴한 화물차, 역주행하며 터널을 빠져나갑니다.\\r\\n\\r\\n고속도로 터널에서 역주행한다는 신고가 들어온 건 자정이 조금 넘은 시각.\\r\\n\\r\\n화물차 운전기사가 불법 유턴과 역주행을 한 고속도로 터널입니다.\\r\\n\\r\\n총 길이 10.9㎞, 국내 최장 도로 터널인데요.\\r\\n\\r\\n차선은 2개지만 모두 같은 방향입니다.\\r\\n\\r\\n터널 내부가 어두운 상태에서 자칫 대형사고 가능성이 있었습니다.\\r\\n\\r\\n이번에도 역시나 음주운전이었습니다.\\r\\n\\r\\n운전기사 45살 A 씨의 혈중알코올농도는 면허 취소 기준을 크게 넘긴 0.186%.\\r\\n\\r\\n공작기계 장비를 싣고 강원도 강릉으로 가던 중 편의점에서 소주 2병을 샀는데, 고속도로 휴게소 주차장에서 마시고, 심지어 차를 운전하면서도 술병을 놓지 않았습니다.\\r\\n\\r\\n[강원지방경찰청 고속도로순찰대 관계자 : 휴게소에 (차를) 세워 놓고 주차장에서 먹기 시작해서 그다음 얘기로는 움직이면서도 먹고 한 병 반을 먹은 거예요. 가다가 술에 취하니까 지리를 잊어먹은 거죠. 어딘지. 옆에다 세워 놓고 똑바로 온 건지 생각하다가 잘못 온 줄 알고 잡아 돌린 거죠. 터널 안에서….]\\r\\n\\r\\n3㎞ 정도를 역주행한 A 씨는 터널 밖 갓길에서 순찰대에 잡혔습니다.\\r\\n\\r\\n경찰은 A 씨를 도로교통법 위반 혐의로 불구속 입건하고, 고의로 역주행했는지도 조사할 계획입니다.\\r\\n\\r\\nYTN 지환[haji@ytn.co.kr]입니다.";

        NamedEntity[][] results = NamedEntityRecognizerManager.getInstance().recognize(text);

        String[] answers = {"PersonEntity{text='지환', type='ps_reporter', begin=115, end=117}",
                "PersonEntity{text='haji@ytn.co.kr', type='tmi_email', begin=947, end=961}",
                "PersonEntity{text='지환 기자입니다', type='token', begin=115, end=123}"};


        int answerIndex = 0;
        for (NamedEntity[] result : results) {
            for (NamedEntity namedEntity : result) {
                Assertions.assertEquals(answers[answerIndex++], namedEntity.toString());
            }
        }



    }

}
