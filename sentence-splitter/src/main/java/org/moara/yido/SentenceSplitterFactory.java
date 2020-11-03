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
package org.moara.yido;

import org.moara.yido.role.BasicRoleManager;
import org.moara.yido.role.NewsRoleManager;

import java.util.HashMap;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 *
 * @author 조승현
 */
public class SentenceSplitterFactory {
    private static final int BASIC_SENTENCE_SPLITTER_ID = 1;
    private static final int NEWS_SENTENCE_SPLITTER_ID = 2;
    private static final String JSON_DATA_TYPE = "json";
    private static final String TEXT_DATA_TYPE = "text";
    private static final String BASIC_DOC_TYPE = "basic";
    private static final String NEWS_DOC_TYPE = "news";
    private static final String SNS_DOC_TYPE = "SNS";
    private static final String STT_DOC_TYPE = "stt";
    private static final String QA_DOC_TYPE = "qa";

    /**
     *  id  1. 기본 문장 구분기
     *      2. 뉴스 문장 구분기
     */
    private static final HashMap<Integer, SentenceSplitter> sentenceSplitterHashMap = new HashMap<>();
    private static final SentenceSplitterFactory sentenceSplitterFactory = new SentenceSplitterFactory();

    /**
     *
     * @return
     */
    public static SentenceSplitterFactory getInstance() { return SentenceSplitterFactory.sentenceSplitterFactory; }
    private SentenceSplitterFactory() { }

    /**
     * <p>문장 구분기 인스턴스 획득</p>
     * 설정값이 없으면 기본값을로 설정된 BasicSentenceSplitter를 반환한다.
     *
     * @return BasicSentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter() {
        if(isKeyEmpty(BASIC_SENTENCE_SPLITTER_ID)) { createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID); }
        System.out.println(sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID));
        return sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID);
        
    }

    /**
     * 임의 설정값이 적용된 문장 구분기 인스턴스 반환
     *
     * @param config 설정값
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(Config config) { return null; }

    /**
     * 특정 id로 분류한 문장 구분기 인스턴스 반환
     *
     * @param id SentenceSplitter ID
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(int id) {
        if(isKeyEmpty(id)) {
            createSentenceSplitter(id);
        }
        return sentenceSplitterHashMap.get(id);
    }


    private void createSentenceSplitter(int id) {
        if(id == BASIC_SENTENCE_SPLITTER_ID) {
            BasicRoleManager basicRoleManager = BasicRoleManager.getRoleManager();
            basicRoleManager.getException();
            sentenceSplitterHashMap.put(BASIC_SENTENCE_SPLITTER_ID,
                    new SentenceSplitterImpl(basicRoleManager, new Config()));
        } else if(id == NEWS_SENTENCE_SPLITTER_ID) {
            NewsRoleManager newsRoleManager = NewsRoleManager.getRoleManager();
            newsRoleManager.getException();
            sentenceSplitterHashMap.put(NEWS_SENTENCE_SPLITTER_ID,
                    new SentenceSplitterImpl(newsRoleManager,
                            new Config(5, 3, 2, TEXT_DATA_TYPE, NEWS_DOC_TYPE)));
        }
    }

    private boolean isKeyEmpty(int key) { return !sentenceSplitterHashMap.containsKey(key); }


}

