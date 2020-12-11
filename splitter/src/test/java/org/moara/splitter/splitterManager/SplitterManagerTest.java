package org.moara.splitter.splitterManager;

import com.seomse.commons.data.BeginEnd;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.splitter.TestFileInitializer;


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

        int[] answer = {12, 20};

        int index = 0;
        for (BeginEnd splitResult : splitter.split(text)) {
            Assert.assertEquals(answer[index++], splitResult.getEnd());
        }

    }

    @Test
    public void testCreateSplitterWithSplitConditions() {

        Splitter splitter = SplitterManager.getInstance().getSplitter("test");


        int[] answer = {12, 20};
        int index = 0;
        for (BeginEnd splitResult : splitter.split(text)) {
            Assert.assertEquals(answer[index++], splitResult.getEnd());
        }
    }


    @Test
    public void testJsonObjectValidationCheck() {
        boolean normalJsonFlag = true;
        boolean invalidJsonFlag1 = false;
        boolean invalidJsonFlag2 = false;


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

        Assert.assertTrue(normalJsonFlag);
        Assert.assertTrue(invalidJsonFlag1);
        Assert.assertTrue(invalidJsonFlag2);
    }

}
