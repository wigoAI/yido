package org.moara.yido.file;

import java.util.List;

public interface FileManager extends FileWriter, FileReader{
    void writeFile(String fileName, List<String> data);

    List<String> getFile();
}
