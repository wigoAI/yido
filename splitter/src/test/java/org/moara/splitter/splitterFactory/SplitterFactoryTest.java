package org.moara.splitter.splitterFactory;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterFactory;
import org.moara.splitter.processor.BracketAreaProcessor;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.role.SplitCondition;
import org.moara.splitter.role.SplitConditionManager;
import org.moara.splitter.utils.Config;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 */
public class SplitterFactoryTest {
    String text = "안녕하세요 반갑습니다. 조승현입니다.";

    @Test
    public void testGetBasicSplitter() {
        Splitter splitter = SplitterFactory.getSplitter();

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }

    }

    @Test
    public void testGetNewsSplitter() {
        Splitter splitter = SplitterFactory.getSplitter(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSplitterWithIllegalKey() {
        SplitterFactory.getSplitter(525);
    }

    @Test
    public void testCreateSplitterWithAreaProcessor() {
        int key = 7;
        String[] validationList = {"V_N_B_001"};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_001", validationList);
        TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, new Config());
        ExceptionAreaProcessor exceptionAreaProcessor = new BracketAreaProcessor();
        SplitterFactory.createSplitter(terminatorAreaProcessor, exceptionAreaProcessor, key);
        Splitter splitter = SplitterFactory.getSplitter(key);

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }
    }

    @Test
    public void testCreateSplitterWithSplitConditions() {
        int key = 8;

        String[] validationList = {"V_N_B_001"};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_001", validationList);
        SplitterFactory.createSplitter(splitConditions, key);
        Splitter splitter = SplitterFactory.getSplitter(key);

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }
    }


}
