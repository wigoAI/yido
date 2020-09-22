package org.moara.yido.file;

import org.junit.Test;
import org.moara.yido.fileIO.FileReader;
import org.moara.yido.fileIO.FileWriter;
import org.moara.yido.db.DataBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileTest {

    @Test
    public void getFileTest() {
        FileReader fileReader = new FileReader("/data/ratings_train.txt");
        List<String> revData = new ArrayList<>();

        for(String str : fileReader.getSplitFileByLine()) {
            System.out.println(str.split("\t")[1]);
            revData.add(str.split("\t")[1]);
        }

        FileWriter fileWriter = new FileWriter("/data/newRevData.txt");
        fileWriter.writeFileByList(revData, false);
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
        FileWriter fileWriter = new FileWriter("/data/connective.txt");
        List<String> data = new ArrayList<>();
        DataBase db = new DataBase();
        String select = "VAL_STRING";
        String from = "TB_ARA_SEN_GROUP_STRING";
        String where = "CD_GROUP='S9'";

        for(String str : db.doQueryAndGetList(select, from, where)){
            data.add(str);
        }

        fileWriter.writeFileByList(data, false);
    }


}
