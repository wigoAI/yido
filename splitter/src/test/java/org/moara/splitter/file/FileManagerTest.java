package org.moara.splitter.file;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FileManagerTest {

    @Test
    public void testReadFile() {

        FileManagerImpl fileManager = new FileManagerImpl();



        assertEquals(fileManager.readFile("/data/newRevData.txt").toArray()[1], "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
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

        assertTrue(fileManager.writeFile("/data/test.role", data));
        assertFalse(fileManager.readFile("/data/test.role").isEmpty());
        assertTrue(fileManager.addLine("/data/test.role", data));
        assertTrue(fileManager.addLine("/data/test.role", hashData));

        assertFalse(fileManager.readFile("/data/test.role").isEmpty());

    }


}
