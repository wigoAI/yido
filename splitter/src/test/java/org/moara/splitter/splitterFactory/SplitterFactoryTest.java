package org.moara.splitter.splitterFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterFactory;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.utils.Sentence;
import org.moara.splitter.utils.file.FileManager;


public class SplitterFactoryTest {
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
        Splitter splitter = SplitterFactory.getSplitter();

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSplitterWithIllegalKey() {
        SplitterFactory.getSplitter(525);
    }


    @Test
    public void testCreateSplitterWithSplitConditions() {
        int key = 8;

        SplitterFactory.createSplitter("test", key);

        Splitter splitter = SplitterFactory.getSplitter(key);

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }
    }

    @Test
    public void testCreateSplitterByJsonObject() {
        int key = 20;
        String id = "json";
        String name = "json_splitter";
        int minimumSplitLength = 5;
        JsonArray conditions = new JsonArray();
        conditions.add("terminator");

        JsonArray exceptions = new JsonArray();
        exceptions.add("bracket_exception");


        JsonObject splitterJsonObject = new JsonObject();
        splitterJsonObject.addProperty("id", id);
        splitterJsonObject.addProperty("name", name);
        splitterJsonObject.addProperty("minimum_split_length", minimumSplitLength);

        splitterJsonObject.add("conditions", conditions);
        splitterJsonObject.add("exceptions", exceptions);

        SplitterFactory.createSplitter(splitterJsonObject, key);
        Splitter splitter = SplitterFactory.getSplitter(key);

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (Sentence sentence : splitter.split(text)) {
            Assert.assertEquals(answer[index++], sentence.getText());
        }
    }

    @Test
    public void testJsonObjectValidationCheck() {
        JsonObject normalJson = FileManager.getJsonObjectByFile("splitter/test.json");

        Assert.assertTrue(SplitterFactory.isValid(normalJson));

        JsonObject invalidJson1 = FileManager.getJsonObjectByFile("splitter/test_invalid1.json");
        Assert.assertFalse(SplitterFactory.isValid(invalidJson1));

        JsonObject invalidJson2 = FileManager.getJsonObjectByFile("splitter/test_invalid2.json");
        Assert.assertFalse(SplitterFactory.isValid(invalidJson2));

    }

}
