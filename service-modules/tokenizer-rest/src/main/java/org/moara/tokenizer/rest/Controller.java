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

package org.moara.tokenizer.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.moara.common.util.ExceptionUtil;
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.TokenizerManager;
import org.moara.yido.tokenizer.word.WordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * api 정의
 * @author macle
 */
@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);


    @RequestMapping(value = "/v1/tokenize" , method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public String tokenize(@RequestBody final String jsonValue) {

        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject request = gson.fromJson(jsonValue, JsonObject.class);

            String tokenizerId = request.get("tokenizer_id").getAsString();
            String text = request.get("text").getAsString();

            JsonArray response = new JsonArray();

            Token[] tokens = TokenizerManager.getInstance().getTokenizer(tokenizerId).getTokens(text);
            for(Token token : tokens){
                WordToken wordToken = (WordToken) token;

                JsonObject jsonToken = new JsonObject();
                jsonToken.addProperty("text", wordToken.getText());
                jsonToken.addProperty("pos", wordToken.getPartOfSpeech());
                jsonToken.addProperty("begin", wordToken.getBegin());
                jsonToken.addProperty("end", wordToken.getEnd());

                response.add(jsonToken);

            }


            return gson.toJson(response);

        }catch(Exception e){
            logger.error(ExceptionUtil.getStackTrace(e));
        }



        return "[]";
    }



}
