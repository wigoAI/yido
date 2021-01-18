package org.moara.splitter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seomse.commons.config.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class FileReader {

    public static final String ABSTRACT_PATH = Config.getConfig("yido.splitter.data.path", "dic/splitter/");

    /**
     * json 파일로부터 json object 생성
     * @param fileName json file name
     * @return JsonObject
     */
    public static JsonObject getJsonObjectByFile(String fileName) {
        JsonElement element;

        try {
            element = JsonParser.parseReader(new java.io.FileReader(ABSTRACT_PATH + fileName + ".json"));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return element.getAsJsonObject();
    }



    /**
     * 경로를 포함한 파일 명으로 파일을 읽어온다.
     *
     * @param fileName String
     *
     * @return Result read file Success
     */
    public static Collection<String> readDictionary(String fileName){
        Collection<String> file = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ABSTRACT_PATH + fileName + ".dic"), StandardCharsets.UTF_8))) {

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
}
