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

package org.moara.yido.textmining.document;

import com.seomse.commons.config.Config;
import org.moara.splitter.Splitter;
import org.moara.yido.tokenizer.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 문서 분석에서 사용하는 모듈관리
 * @author macle
 */
public class DocumentModuleManager {

    private static final Logger logger = LoggerFactory.getLogger(DocumentModuleManager.class);

    private static class Singleton {
        private static final DocumentModuleManager instance = new DocumentModuleManager();
    }

    /**
     * 인스턴스 얻기
     * @return DocumentModuleManager singleton instance
     */
    public static DocumentModuleManager getInstance(){
        return Singleton.instance;
    }

    private final Map<String, Tokenizer> tokenizerMap = new HashMap<>();

    private final Map<String, Splitter> sentenceSplitterMap = new HashMap<>();

    private final Map<String, Splitter> paragraphSplitterMap = new HashMap<>();


    /**
     * 생성자 막기
     */
    private DocumentModuleManager(){


        //공백 단위로 매핑구조, 단위로 배열
        //문서 유형별 tokenizer
        Config.getConfig("document.tokenizer.map", "ddD?");

        Config.getConfig("document.sentence.splitter.map", "ddD?");



    }





}
