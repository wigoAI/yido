/*
 * Copyright (C) 2020 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moara.splitter.utils.file;

import java.io.*;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 파일 관리자 구현체
 * @author wjrmffldrhrl
 *
 */
public class FileManagerImpl implements FileManager{


    @Override
    public Collection<String> readFile(String fileName){
        Collection<String> file = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ABSTRACT_PATH + fileName), StandardCharsets.UTF_8))) {

            while(true) {
                String line = br.readLine();
                if(line == null) { break; }
                file.add(line);
            }


        } catch (IOException e) {
            e.printStackTrace();

        }


        return file;
    }

    @Override
    public boolean writeFile(String fileName, Collection<String> data) {
        return writeFile(fileName, data, false);
    }

    @Override
    public boolean addLine(String fileName, Collection<String> data) {
        return writeFile(fileName, data, true);
    }

    private boolean writeFile(String fileName, Collection<String> data, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(changePathSeparator(ABSTRACT_PATH + fileName), append))){
            for(String str : data)
                bw.write(str + "\n");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 특정 확장자 파일 획득
     * @param path String
     * @param fileExtension String
     * @return {@code List<File>}
     */
    public List<File> getFileList(String path, String fileExtension){
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


    private String changePathSeparator(String path) {
        return path.replace("/", File.separator).replace("\\", File.separator);
    }


}
