package org.moara.splitter.utils.file;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileManagerTest {

    @Test
    public void testReadFile() {

        assertEquals(FileManager.readFile("/newRevData.txt").toArray()[1], "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
    }

    @Test
    public void testWriteFile() {
        List<String> data = new ArrayList<>();
        HashSet<String> hashData = new HashSet<>();

        data.add("test1");
        data.add("test2");
        data.add("test3");

        hashData.add("test1");
        hashData.add("test2");
        hashData.add("test3");

        assertTrue(FileManager.writeFile("/test.role", data));
        assertFalse(FileManager.readFile("/test.role").isEmpty());
        assertTrue(FileManager.addLine("/test.role", data));
        assertTrue(FileManager.addLine("/test.role", hashData));

        assertFalse(FileManager.readFile("/test.role").isEmpty());

    }

    @Test
    public void testReadJsonFile() {
        JsonObject jsonObject = FileManager.getJsonObjectByFile("/string_group/meta/M_bracket.json");
    }



}
