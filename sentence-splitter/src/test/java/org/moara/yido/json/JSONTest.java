package org.moara.yido.json;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class JSONTest {


    /**
     *
     * @auter https://kingpodo.tistory.com/11
     * */
    @Test
    public void JSONInputTest() throws ParseException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("data", "test");

        System.out.println("data : " + jsonObject.toJSONString());


        String jsonData = "{\"title\": \"how to get stroage size\","
                + "\"url\": \"https://codechacha.com/ko/get-free-and-total-size-of-volumes-in-android/\","
                + "\"draft\": false,"
                + "\"star\": 10"
                + "}";
        JSONParser parser = new JSONParser();


        JSONObject jsonObjectStr = (JSONObject) parser.parse(jsonData);

        System.out.println(jsonObjectStr.get("title"));
    }
}
