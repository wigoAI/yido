/*
 * Copyright (C) 2021 Wigo Inc.
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wigoai.rest.RestCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author macle
 */
public class ApiTest {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject request = new JsonObject();

        request.addProperty("tokenizer_id", "mecab");
        request.addProperty("text", "김용수는 모아라에 다닌다");


        String requestValue = gson.toJson(request);

        String response = RestCall.postJson("http://localhost:" + new String(Files.readAllBytes(Paths.get("config/port_number"))) + "/v1/tokenize"
                , requestValue);


        System.out.println(requestValue);
        System.out.println(response);





    }
}
