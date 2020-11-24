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

package org.moara.yido.tokenizer;

import java.util.HashMap;
import java.util.Map;

/**
 * Tokenizer 관리
 * singleton
 * @author macle
 */
public class TokenizerManager {

    private static class Singleton {
        private static final TokenizerManager instance = new TokenizerManager();
    }

    /**
     * 인스턴스 얻기
     * @return TokenizerManager singleton instance
     */
    public static TokenizerManager getInstance(){
        return Singleton.instance;
    }

    private final Map<String, Tokenizer> tokenizerMap = new HashMap<>();

    private TokenizerManager(){

        //tokenizer load

    }


    /**
     * 기본형 tokenizer
     * 지금 시점에서 가장 인기있는 tokenizer을 얻음
     * @return default(Popular) tokenizer
     */
    public Tokenizer getTokenizer(){


        return null;
    }

    /**
     * tokenizer 얻기
     * @param tokenizerId tokenizer id
     * @return Tokenizer
     */
    public Tokenizer getTokenizer(String tokenizerId){

        Tokenizer tokenizer = tokenizerMap.get(tokenizerId);

        if(tokenizer == null){
            throw new TokenizerNotFoundException(tokenizerId);
        }

        return tokenizer;

    }







}
