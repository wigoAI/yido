package org.moara.yido.file;

import org.junit.Test;
import static org.junit.Assert.*;


import org.moara.yido.fileIO.FileReader;
import org.moara.yido.fileIO.FileWriter;
import org.moara.yido.db.DataBase;

import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void getFileTest() {
        FileReader fileReader = new FileReader("/data/newRevData.txt");
        List<String> revData = new ArrayList<>();

        for(String str : fileReader.getSplitFileByLine()) {
            System.out.println(str);
            revData.add(str);
        }

        assertEquals(revData.get(0), "아 더빙.. 진짜 짜증나네요 목소리");

    }

    @Test
    public void writeFileTest() throws InterruptedException {

        FileWriter fileWriter = new FileWriter("/data/test.txt");
        List<String> data = new ArrayList<>();

        data.add("hello!");
        data.add("hello!");
        data.add("hello!2");

        fileWriter.writeFileByList(data, true);
    }

    @Test
    public void createTestData() {
        FileReader fileReader = new FileReader("/data/data5Answer.txt");
        FileWriter fileWriter = new FileWriter("/data/data5.txt");

        List<String> data = fileReader.getSplitFileByLine();
        String dataStr = "";

        for(String str : data) {
            dataStr += (str + " ");

        }
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add(dataStr);
        fileWriter.writeFileByList(dataList, false);
    }


}
