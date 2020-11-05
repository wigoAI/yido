package org.moara.yido.file;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileManagerTest {

    @Test
    public void testReadFile() {

        FileManagerImpl fileManager = new FileManagerImpl();

        fileManager.readFile("/data/newRevData.txt");

        assertEquals(fileManager.getFile().get(1), "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
    }

    @Test
    public void testWriteFile() {
        FileManagerImpl fileManager = new FileManagerImpl();
        List<String> data = new ArrayList<>();

        data.add("test1");
        data.add("test2");
        data.add("test3");

        fileManager.writeFile("/data/test.role", data);



        fileManager.readFile("/data/test.role");
        System.out.println(fileManager.getFile());

        fileManager.addLine("/data/test.role", data);

    }


}
