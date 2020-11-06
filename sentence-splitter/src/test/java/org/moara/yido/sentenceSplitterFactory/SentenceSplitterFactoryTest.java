package org.moara.yido.sentenceSplitterFactory;

import org.junit.Assert;
import org.junit.Test;
import org.moara.yido.SentenceSplitter;
import org.moara.yido.SentenceSplitterFactory;
import org.moara.yido.role.CustomRoleManager;
import org.moara.yido.role.RoleManager;
import org.moara.yido.utils.Config;
import org.moara.yido.utils.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 1. 문장 분리기 생성자 관련 테스트 계속 진행할 것
 */
public class SentenceSplitterFactoryTest {
    String text = "반갑습니다. 조승현입니다.";

    @Test
    public void testGetBasicSentenceSplitter() {
        SentenceSplitterFactory ssf = SentenceSplitterFactory.getInstance();
        SentenceSplitter sentenceSplitter = ssf.getSentenceSplitter();

        String[] answer = {"반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : sentenceSplitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }

    }

    @Test
    public void testCustomRoleSentenceSplitterTest() {
        SentenceSplitterFactory ssf = SentenceSplitterFactory.getInstance();

        RoleManager customRoleManager = CustomRoleManager.getRoleManager();
        Config config = new Config(5, 3, 2, false);


        SentenceSplitter sentenceSplitter = ssf.getSentenceSplitter(customRoleManager, config);

        String[] answer = {"반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : sentenceSplitter.split(text)) {
            Assert.assertNotEquals( answer[index++], sentence.getText());
        }

        List<String> roles = new ArrayList<>();
        roles.add("다.");
        customRoleManager.addRolesToMemory("terminator", roles);

        index = 0;
        for (Sentence sentence : sentenceSplitter.split(text)) {
            Assert.assertNotEquals( answer[index++], sentence.getText());
        }
    }

}
