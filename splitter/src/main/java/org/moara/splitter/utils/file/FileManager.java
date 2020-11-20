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

import org.moara.splitter.SplitterImpl;

import java.io.*;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 파일 관리자 구현체
 *
 * TODO 1. Change to singleTone
 *
 * @author wjrmffldrhrl
 *
 */
public class FileManager {
    public static final String pathSeparator = File.separator;
    public static final String ABSTRACT_PATH = SplitterImpl.class.getResource("")
            .getPath().split( "build")[0]
            + "data" + pathSeparator;

    private FileManager() { }
    /**
     * 경로를 포함한 파일 명으로 파일을 읽어온다.
     *
     * @param fileName String
     *
     * @return Result read file Success
     */
    public static Collection<String> readFile(String fileName){
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

    /**
     * 파일 생성
     * @param fileName String
     * @param data {@code List<String>}
     *
     * @return Write file result
     */
    public static boolean writeFile(String fileName, Collection<String> data) {
        return writeFile(fileName, data, false);
    }

    /**
     *
     * @param fileName
     * @param data
     *
     * @return Result add line success
     */
    public static boolean addLine(String fileName, Collection<String> data) {
        return writeFile(fileName, data, true);
    }


    private static boolean writeFile(String fileName, Collection<String> data, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(changePathSeparator(ABSTRACT_PATH + fileName), append))){
            for(String str : data)
                bw.write(str + "\n");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static List<File> getFileList(String path, String fileExtension){
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

    private static void addFiles(List<File> fileList, File file){
        fileList.add(file);
        if(file.isDirectory()){
            File [] files = file.listFiles();

            //noinspection ConstantConditions
            for(File f : files){
                addFiles(fileList, f);
            }
        }
    }


    private static String changePathSeparator(String path) {
        return path.replace("/", File.separator).replace("\\", File.separator);
    }


}
