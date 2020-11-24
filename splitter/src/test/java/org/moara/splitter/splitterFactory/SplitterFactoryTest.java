package org.moara.splitter.splitterFactory;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterFactory;
import org.moara.splitter.utils.Config;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
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
}
