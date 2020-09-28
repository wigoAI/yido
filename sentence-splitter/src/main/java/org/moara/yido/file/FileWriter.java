package org.moara.yido.file;

import org.moara.yido.BasicSentenceSplitter;

import java.util.List;

public interface FileWriter {
    String ABSTRACT_PATH = BasicSentenceSplitter.class.getResource("").getPath().split("/target")[0] + "/src/main/resources";
    void writeFile(String fileName, List<String> data);
}
