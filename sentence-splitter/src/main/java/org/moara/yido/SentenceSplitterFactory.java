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
import org.moara.yido.role.RoleManager;
import org.moara.yido.utils.Config;

import java.util.HashMap;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 *
 * @author 조승현
 */
public class SentenceSplitterFactory {
    private static final int BASIC_SENTENCE_SPLITTER_ID = 1;
    private static final int NEWS_SENTENCE_SPLITTER_ID = 2;
    private static final int CUSTOM_SENTENCE_SPLITTER_ID = 9;


    /**
     *  id  1. 기본 문장 구분기
     *      2. 뉴스 문장 구분기
     */
    private static final HashMap<Integer, SentenceSplitter> sentenceSplitterHashMap = new HashMap<>();
    private static final SentenceSplitterFactory sentenceSplitterFactory = new SentenceSplitterFactory();

    /**
     * 싱글톤으로 구성된 SentenceSplitterFactory 인스턴스 획득
     *
     * @return SentenceSplitterFactory 인스턴스 반환
     */
    public static SentenceSplitterFactory getInstance() { return SentenceSplitterFactory.sentenceSplitterFactory; }
    private SentenceSplitterFactory() { }

    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSentenceSplitter를 반환한다.
     *
     * @return BasicSentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter() {
        if(isKeyEmpty(BASIC_SENTENCE_SPLITTER_ID)) { createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID); }

        return sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID);
    }



    /**
     * 임의 설정값이 적용된 문장 구분기 인스턴스 반환
     * 이후 CustomSentenceSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     * {@code getSentenceSplitter(9)}
     * @param config 설정값
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(Config config) {
        RoleManager basicRoleManager = BasicRoleManager.getRoleManager();
        sentenceSplitterHashMap.put(CUSTOM_SENTENCE_SPLITTER_ID,
                new SentenceSplitterImpl(basicRoleManager, config));
        return sentenceSplitterHashMap.get(CUSTOM_SENTENCE_SPLITTER_ID);
    }

    /**
     * 커스텀 룰 관리자가 적용된 문장 분리기 반환
     *
     * 이후 CustomSentenceSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     *  {@code getSentenceSplitter(9)}
     * @param roleManager CustomRoleManager
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(RoleManager roleManager) {
        sentenceSplitterHashMap.put(CUSTOM_SENTENCE_SPLITTER_ID,
                new SentenceSplitterImpl(roleManager, new Config()));
        return sentenceSplitterHashMap.get(CUSTOM_SENTENCE_SPLITTER_ID);
    }

    /**
     * 커스텀 룰 관리자와 사용자 지정 설정이 적용된 문장 분리기 반환
     *
     * 이후 CustomSentenceSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     * {@code getSentenceSplitter(9)}
     * @param roleManager CustomRoleManager
     * @param config CustomConfig
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(RoleManager roleManager, Config config) {
        sentenceSplitterHashMap.put(CUSTOM_SENTENCE_SPLITTER_ID,
                new SentenceSplitterImpl(roleManager, config));
        return sentenceSplitterHashMap.get(CUSTOM_SENTENCE_SPLITTER_ID);
    }


    /**
     * 특정 id로 분류한 문장 구분기 인스턴스 반환
     *
     * @param id SentenceSplitter ID
     * @return SentenceSplitter
     */
    public SentenceSplitter getSentenceSplitter(int id) {
        if(isKeyEmpty(id)) { createSentenceSplitter(id); }
        return sentenceSplitterHashMap.get(id);
    }


    private void createSentenceSplitter(int id) {
        if(id == BASIC_SENTENCE_SPLITTER_ID) {
            RoleManager basicRoleManager = BasicRoleManager.getRoleManager();
            sentenceSplitterHashMap.put(BASIC_SENTENCE_SPLITTER_ID,
                    new SentenceSplitterImpl(basicRoleManager, new Config()));
        } else if(id == NEWS_SENTENCE_SPLITTER_ID) {
            RoleManager newsRoleManager = NewsRoleManager.getRoleManager();
            sentenceSplitterHashMap.put(NEWS_SENTENCE_SPLITTER_ID,
                    new SentenceSplitterImpl(newsRoleManager,
                            new Config(5, 3, 2, true)));
        }
    }

    private boolean isKeyEmpty(int key) { return !sentenceSplitterHashMap.containsKey(key); }


}

