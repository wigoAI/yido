package org.moara.splitter.splitterManager;

import com.seomse.commons.data.BeginEnd;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.exception.SplitterNotFoundException;


public class SplitterManagerTest {
    String text = "안녕하세요 반갑습니다. 조승현입니다.";

    @Before
    public void initializeTest() {
        TestFileInitializer.initialize();
    }

    @After
    public void tearDownTest() {
        TestFileInitializer.tearDown();
    }

    @Test
    public void testGetBasicSplitter() {
        Splitter splitter = SplitterManager.getInstance().getSplitter();

        int[] beginAnswer = {0, 13};
        int[] endAnswer = {12, 20};
        int index = 0;

        BeginEnd[] splitResults = splitter.split(text);

        for (BeginEnd splitResult : splitResults) {
            Assert.assertEquals(beginAnswer[index], splitResult.getBegin());
            Assert.assertEquals(endAnswer[index++], splitResult.getEnd());
        }

    }

    @Test
    public void testCreateSplitterWithSplitConditions() {

        Splitter splitter = SplitterManager.getInstance().getSplitter("test");

        BeginEnd[] splitResults = splitter.split(text);

        int[] beginAnswer = {0, 13};
        int[] endAnswer = {12, 20};
        int index = 0;
        for (BeginEnd splitResult : splitResults) {
            Assert.assertEquals(beginAnswer[index], splitResult.getBegin());
            Assert.assertEquals(endAnswer[index++], splitResult.getEnd());
        }
    }


    @Test
    public void testJsonObjectValidationCheck() {
        boolean normalJsonFlag = true;
        boolean invalidJsonFlag1 = false;
        boolean invalidJsonFlag2 = false;
        boolean notFoundFlag = false;

        try {
            SplitterManager.getInstance().getSplitter("test");
        } catch (RuntimeException e) {
            normalJsonFlag = false;
        }

        try {
            SplitterManager.getInstance().getSplitter("test_invalid1");

        } catch (RuntimeException e) {
            invalidJsonFlag1 = true;
        }

        try {
            SplitterManager.getInstance().getSplitter("test_invalid2");
        } catch (RuntimeException e) {
            invalidJsonFlag2 = true;
        }

        try {
            SplitterManager.getInstance().getSplitter("this_is_not_exist");
        } catch (SplitterNotFoundException e) {
            notFoundFlag = true;
        }

        Assert.assertTrue(normalJsonFlag);
        Assert.assertTrue(invalidJsonFlag1);
        Assert.assertTrue(invalidJsonFlag2);
        Assert.assertTrue(notFoundFlag);
    }

}
