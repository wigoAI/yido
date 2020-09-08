package org.yido.dataInput;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    Path path;
    Charset cs;

    public FileReader(String path) {
        this.path = Paths.get(path);
        this.cs = StandardCharsets.UTF_8;
    }

    public String readFile() {
        String str = "";
        List<String> list = new ArrayList<String>();
        try{
            System.out.println(this.path);
            list = Files.readAllLines(this.path,this.cs);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(String readLine : list) {
            str += readLine;
        }

        return str;

    }
}
