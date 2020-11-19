package org.moara.splitter.utils.file;

import org.junit.Test;
import org.moara.splitter.SplitterImpl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileManagerTest {

    @Test
    public void testReadFile() {

        FileManagerImpl fileManager = new FileManagerImpl();

        assertEquals(fileManager.readFile("/newRevData.txt").toArray()[1], "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
    }

    @Test
    public void testWriteFile() {
        FileManagerImpl fileManager = new FileManagerImpl();
        List<String> data = new ArrayList<>();
        HashSet<String> hashData = new HashSet<>();

        data.add("test1");
        data.add("test2");
        data.add("test3");

        hashData.add("test1");
        hashData.add("test2");
        hashData.add("test3");

        assertTrue(fileManager.writeFile("/test.role", data));
        assertFalse(fileManager.readFile("/test.role").isEmpty());
        assertTrue(fileManager.addLine("/test.role", data));
        assertTrue(fileManager.addLine("/test.role", hashData));

        assertFalse(fileManager.readFile("/test.role").isEmpty());

    }




}
