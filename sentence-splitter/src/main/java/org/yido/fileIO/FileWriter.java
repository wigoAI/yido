package org.yido.fileIO;


import org.yido.SentenceSplitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {

    // class 파일의 절대경로로 상대경로 습득
    final String ABSTRACT_PATH = SentenceSplitter.class.getResource("").getPath().split("/target")[0] + "/src/main/resources";
    String pathName;
    File file;
    public FileWriter(String pathName) {
        this.pathName = pathName;
        file = new File(ABSTRACT_PATH + pathName);
    }

    public void writeFileByList(List<String> inputList, boolean append) {


        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new java.io.FileWriter(this.file, append)));

            for(String str : inputList) {
                pw.println(str);
            }

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
