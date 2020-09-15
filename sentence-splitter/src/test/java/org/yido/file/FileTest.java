package org.yido.file;

import org.junit.Test;
import org.yido.fileIO.FileReader;
import org.yido.fileIO.FileWriter;
import org.yido.db.DataBase;

import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void getFileTest() {
        FileReader fileReader = new FileReader("/data/data1Answer.txt");


        for(String str : fileReader.getSplitFileByLine()) {
            System.out.println(" : " + str);
        }
    }

    @Test
    public void writeFileTest() {

        FileWriter fileWriter = new FileWriter("/data/test.txt");
        List<String> data = new ArrayList<>();

        data.add("hello!");
        data.add("hello!");
        data.add("hello!2");

        fileWriter.writeFileByList(data, false);
    }

    @Test
    public void createRoleFile() throws Exception {
        FileWriter fileWriter = new FileWriter("/data/roleTest.txt");
        List<String> data = new ArrayList<>();
        DataBase db = new DataBase();

        for(String str : db.getRoles()){
            data.add(str);
        }

        fileWriter.writeFileByList(data, false);
    }
}
