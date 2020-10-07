package org.moara.yido.file;

import org.moara.yido.BasicSentenceSplitter;

import java.io.File;
import java.util.List;

public interface FileWriter {
    String pathSeparator = File.separator;

    String ABSTRACT_PATH = BasicSentenceSplitter.class.getResource("")
            .getPath().split( "target")[0]
            + "src" + pathSeparator + "main" + pathSeparator + "resources";
    void writeFile(String fileName, List<String> data);
}
