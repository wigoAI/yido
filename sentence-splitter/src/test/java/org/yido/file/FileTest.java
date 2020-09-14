package org.yido.file;

import org.junit.Test;
import org.yido.dataInput.FileReader;

public class FileTest {

    @Test
    public void getFileTest() {
        FileReader fileReader = new FileReader("/data/data1Answer.txt");


        for(String str : fileReader.getSplitFileByLine()) {
            System.out.println(" : " + str);
        }
    }
}
