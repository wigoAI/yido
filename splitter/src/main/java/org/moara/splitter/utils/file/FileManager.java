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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seomse.commons.config.Config;
import org.moara.splitter.Splitter;

import java.io.*;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 파일 관리자 구현체
 *
 * @author wjrmffldrhrl
 *
 */
public class FileManager {
    protected static final String ABSTRACT_PATH = Config.getConfig("yido.splitter.data.path", "data") + "/";

    /**
     * 경로를 포함한 파일 명으로 파일을 읽어온다.
     *
     * @param fileName String
     *
     * @return Result read file Success
     */
    public static Collection<String> readFile(String fileName){
        Collection<String> file = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ABSTRACT_PATH + fileName), StandardCharsets.UTF_8))) {

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                file.add(line);
            }

        }catch (IOException e) {
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
     * 파일 내용 추가
     * @param fileName 내용을 추가할 파일
     * @param data 파일에 추가할 내용
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

    private static String changePathSeparator(String path) {
        return path.replace("/", File.separator).replace("\\", File.separator);
    }


    /**
     * json파일로부터 json object 생성
     * @param fileName json file name
     * @return JsonObject
     */
    public static JsonObject getJsonObjectByFile(String fileName) {
        JsonElement element;

        try {
            element = JsonParser.parseReader(new FileReader(ABSTRACT_PATH + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return element.getAsJsonObject();
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(ABSTRACT_PATH + fileName);

        return file.delete();

    }

}
