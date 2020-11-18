package org.moara.yido.splitterFactory;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterFactory;
import org.moara.splitter.role.CustomRoleManager;
import org.moara.splitter.role.RoleManager;
import org.moara.splitter.utils.Config;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 1. 문장 분리기 생성자 관련 테스트 계속 진행할 것
 */
public class SplitterFactoryTest {
    String text = "반갑습니다. 조승현입니다.";

    @Test
    public void testGetBasicSplitter() {
        SplitterFactory ssf = SplitterFactory.getInstance();
        Splitter splitter = ssf.getSplitter();

        String[] answer = {"반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }

    }

    @Test
    public void testCustomRoleSplitterTest() {
        SplitterFactory ssf = SplitterFactory.getInstance();

        RoleManager customRoleManager = CustomRoleManager.getRoleManager();
        Config config = new Config(5, 3, 2, false);


        Splitter splitter = ssf.getSplitter(customRoleManager, config);

        String[] answer = {"반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertNotEquals( answer[index++], sentence.getText());
        }

        List<String> roles = new ArrayList<>();
        roles.add("다.");
        customRoleManager.addRolesToMemory("terminator", roles);

        index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertNotEquals( answer[index++], sentence.getText());
        }
    }

}
