package org.moara.yido.file;

import java.io.*;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FileManagerImpl implements FileManager {

    List<String> file = new ArrayList<>();


    @Override
    public void readFile(String fileName){
        this.file.clear();
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(FileManagerImpl.class.getResourceAsStream(fileName), StandardCharsets.UTF_8));
            while(true) {
                String line = br.readLine();
                this.file.add(line);
                if(line == null)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeFile(String fileName, List<String> data) {
        System.out.println(ABSTRACT_PATH + fileName);
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

    public  List<File> getFileList(String path, String fileExtension){
        List<File> fileList = new ArrayList<>();
        File file = new File(path);

        addFiles(fileList, file);

        List<File> resultFileList = new ArrayList<>();
        for(File f : fileList){
            if(f.isDirectory()){
                continue;
            }

            if(f.getName().endsWith(fileExtension)){
                resultFileList.add(f);
            }
        }
        fileList.clear();
        fileList = null;

        return resultFileList;
    }

    private void addFiles(List<File> fileList, File file){
        fileList.add(file);
        if(file.isDirectory()){
            File [] files = file.listFiles();
            //noinspection ConstantConditions
            for(File f : files){
                addFiles(fileList, f);
            }
        }
    }


}
