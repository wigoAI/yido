package org.moara.yido.file;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class FileManagerImpl implements FileManager {

    List<String> file = new ArrayList<>();
    private BufferedReader br = null;




    @Override
    public void readFile(String fileName) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(FileManagerImpl.class.getResourceAsStream(fileName), "UTF-8"));
        while(true) {
            String line = br.readLine();
            this.file.add(line);
            if(line == null)
                break;
        }

    }

    @Override
    public void writeFile(String fileName, List<String> data) {

        try (  BufferedWriter bw = new BufferedWriter(new FileWriter(ABSTRACT_PATH + fileName))){
            for(String str : data)
                bw.write(str + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getFile() {

        return this.file;
    }
}
