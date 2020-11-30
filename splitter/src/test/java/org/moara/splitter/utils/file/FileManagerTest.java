package org.moara.splitter.utils.file;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FileManagerTest {

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
        JsonObject jsonObject = FileManager.getJsonObjectByFile("/string_group/meta/M_different_side_bracket.json");
    }


    @Test
    public void testInvalidJsonFileRead() {
        FileManager.getJsonObjectByFile("/string_group/meta/THIS_FILE_DOESNT_EXIST.json");
    }

    @Test
    public void testInvalidFileRead() {
        FileManager.readFile("THIS_FILE_DOESNT_EXIST.txt");

    }

    @Test
    public void testInvalidFileWrite() {
        assertFalse(FileManager.writeFile("", new ArrayList<>()));

    }



}
