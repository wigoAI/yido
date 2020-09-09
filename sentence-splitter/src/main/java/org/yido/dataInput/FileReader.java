package org.yido.dataInput;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    String file;

    public FileReader(String path) {
        try {
            this.file = IOUtils.toString(getClass().getResourceAsStream(path), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFile() {
        return this.file;
    }

    public List<String> getSplitFile(String splitter) {
        List<String> splitList = new ArrayList<String>();
        if(splitter == "|") splitter = "\\|";
        for(String str : this.getFile().split(splitter)) {
            splitList.add(str);
        }

        return splitList;
    }
}
