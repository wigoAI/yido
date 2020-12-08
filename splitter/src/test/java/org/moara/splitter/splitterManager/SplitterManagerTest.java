package org.moara.splitter.splitterManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.utils.SplitResult;
import org.moara.splitter.utils.file.FileManager;




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
        Splitter splitter = SplitterManager.getSplitterManager().getSplitter();

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (SplitResult splitResult : splitter.split(text)) {
            Assert.assertEquals(answer[index++], splitResult.getText());
        }

    }

    @Test
    public void testCreateSplitterWithSplitConditions() {

        Splitter splitter = SplitterManager.getSplitterManager().getSplitter("test");


        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (SplitResult splitResult : splitter.split(text)) {
            Assert.assertEquals(answer[index++], splitResult.getText());
        }
    }

    @Test
    public void testCreateSplitterByJsonObject() {

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


        Splitter splitter = SplitterManager.getSplitterManager().getSplitter(splitterJsonObject);

        String[] answer = {"안녕하세요 반갑습니다.", "조승현입니다."};
        int index = 0;
        for (SplitResult splitResult : splitter.split(text)) {
            Assert.assertEquals(answer[index++], splitResult.getText());
        }
    }

    @Test
    public void testJsonObjectValidationCheck() {
        boolean normalJsonFlag = true;
        boolean invalidJsonFlag1 = false;
        boolean invalidJsonFlag2 = false;

        JsonObject normalJson = FileManager.getJsonObjectByFile("splitter/test.json");

        try {
            SplitterManager.getSplitterManager().getSplitter(normalJson);
        } catch (RuntimeException e) {
            normalJsonFlag = false;
        }



        JsonObject invalidJson1 = FileManager.getJsonObjectByFile("splitter/test_invalid1.json");
        try {
            SplitterManager.getSplitterManager().getSplitter(invalidJson1);

        } catch (RuntimeException e) {
            invalidJsonFlag1 = true;
        }

        JsonObject invalidJson2 = FileManager.getJsonObjectByFile("splitter/test_invalid2.json");
        try {
            SplitterManager.getSplitterManager().getSplitter(invalidJson2);
        } catch (RuntimeException e) {
            invalidJsonFlag2 = true;
        }

        Assert.assertTrue(normalJsonFlag);
        Assert.assertTrue(invalidJsonFlag1);
        Assert.assertTrue(invalidJsonFlag2);
    }

}
