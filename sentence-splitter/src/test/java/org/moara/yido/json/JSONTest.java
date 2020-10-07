package org.moara.yido.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;


public class JSONTest {


    @Test
    public void createRoleByJson() {
        String path = "D:\\moara\\data\\testworks\\test2.json";
        HashMap<String, Integer> newRole = new HashMap<>();

        JsonElement element = null;
        try {
            element = JsonParser.parseReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        JsonObject jsonobject = element.getAsJsonObject();
//        System.out.println(jsonobject.get("name"));
        JsonArray documents = (JsonArray) jsonobject.get("documents");

        for(int i = 0 ; i < documents.size() ; i++) {
            JsonObject document = (JsonObject) documents.get(i);
//            System.out.println(document.get("id"));

            JsonArray texts = (JsonArray) document.get("text");
//            System.out.println(texts.size());
            for(int j = 1 ; j < texts.size() -1 ; j++) {
                JsonArray sentenceList = (JsonArray) texts.get(j);

                for (int k = 0 ; k < sentenceList.size() ; k++) {
                    JsonObject sentence = (JsonObject) sentenceList.get(k);
                    String targetString = sentence.get("sentence").toString();

                    try {
                        String data = targetString.substring(targetString.lastIndexOf(" "), targetString.length() - 1);
//                        System.out.println(targetString.substring(targetString.lastIndexOf(" "), targetString.length() - 1));
                        if(k != (sentenceList.size() - 1)) {
                            if(newRole.containsKey(data)) {
                                int oldData = newRole.get(data);
                                newRole.replace(data, oldData + 1);
                            } else {
                                newRole.put(data, 1);
                            }
                        }


                    }catch (Exception e) {

                        continue;
                    }



                }

            }

        }

        for(String str :  newRole.keySet()) {
            if(!str.endsWith("."))
                System.out.println(str + " : " + newRole.get(str));
        }


    }

}
