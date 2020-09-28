package org.moara.yido.file;

import org.junit.Test;
import static org.junit.Assert.*;


import org.moara.yido.fileIO.FileReader;
import org.moara.yido.fileIO.FileWriter;
import org.moara.yido.db.DataBase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void getFileTest() throws UnsupportedEncodingException {

        FileManagerImpl fileManager = new FileManagerImpl();

        try {
            fileManager.readFile("/data/newRevData.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(fileManager.getFile().get(1), "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
    }

    @Test
    public void writeFileTest() throws InterruptedException {
        FileManagerImpl fileManager = new FileManagerImpl();
        List<String> data  = new ArrayList<>();

        data.add("test1");
        data.add("test2");
        data.add("test3");

        fileManager.writeFile("/data/test.role", data);

    }




}
