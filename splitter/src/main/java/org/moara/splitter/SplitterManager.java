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
package org.moara.splitter;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.moara.splitter.processor.BracketAreaProcessor;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.manager.SplitConditionManager;
import org.moara.splitter.utils.file.FileManager;

import java.util.*;

/**
 * 문장 구분기를 관리해주는 클래스
 *
 * 기본 구분기를 사용하기 위해서는 {@code getSplitter()} 매서드를 사용해 기본 구분기의 인스턴스를 받을 수 있다.
 * 해당 메서드를 처음 호출하기 전 까지는 인스턴스는 존재하지 않다가 첫 호출 시 구분기를 생성한다.
 *
 * @author wjrmffldrhrl
 */
public class SplitterManager {
    private static SplitterManager splitterManager = null;
    private static final String BASIC_SPLITTER_ID = "basic";
    private final Map<String, Splitter> splitterMap = new HashMap<>();

    /**
     * SplitterManager 인스턴스 생성 및 반환
     * @return SplitterManager instance
     *
     * TODO 1. 다른 방식 적용
     */
    public static SplitterManager getSplitterManager() {
        if (splitterManager == null) {
            splitterManager = new SplitterManager();
        }
        return splitterManager;
    }


    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSplitter 를 반환한다.
     * @return BasicSplitter
     */
    public Splitter getSplitter() {
        return getSplitter(BASIC_SPLITTER_ID);
    }

    /**
     * 특정 id로 분류한 문장 구분기 인스턴스 반환
     *
     * @param id Splitter ID
     *
     * @return Splitter
     */
    public Splitter getSplitter(String id) {
        JsonObject splitterJson = FileManager.getJsonObjectByFile("splitter/" + id + ".json");

        return getSplitter(splitterJson);
    }




    private final Object createLock = new Object();

    /**
     * JsonObject를 통해 Splitter 획득
     * 해당 JsonObject의 key로 생성된 Splitter가 존재하지 않으면 새로 생성한다.
     *
     * 멀티 쓰레드에 안전하게 생성할 수 있도록 동기화처리가 되어있다.
     *
     * TODO 1. lock 구간을 id 단위로 나눌 수 있다.
     *      2. json 입력과 분리
     *      3. core에서 json 고려
     * @param splitterJson 형식에 맞춘 jsonObject
     * @return splitter 구현체
     */
    public Splitter getSplitter(JsonObject splitterJson) {
        checkSplitterJsonValidation(splitterJson);

        String key = splitterJson.get("id").getAsString();
        Splitter splitter = splitterMap.get(key);

        if(splitter == null) {
            synchronized (createLock) {
                splitter = splitterMap.get(key);
                if(splitter == null) {
                    createSplitterByJson(splitterJson);
                    splitter = splitterMap.get(key);
                }
            }
        }

        return splitter;
    }

    private void createSplitterByJson(JsonObject splitterJson) {
        String key = splitterJson.get("id").getAsString();
        int minResultLength = splitterJson.get("minimum_split_length").getAsInt();
        JsonArray conditionArray = splitterJson.get("conditions").getAsJsonArray();

        List<String> conditionRuleNames = new ArrayList<>();
        for (JsonElement jsonObject : conditionArray) {
            conditionRuleNames.add(jsonObject.getAsString());
        }

        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(conditionRuleNames);
        TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, minResultLength);
        List<ExceptionAreaProcessor> exceptionAreaProcessors = Arrays.asList(new BracketAreaProcessor());

        splitterMap.put(key, new RuleSplitter(terminatorAreaProcessor, exceptionAreaProcessors));

    }

    private void checkSplitterJsonValidation(JsonObject splitterJson) {
        if (!(splitterJson.isJsonObject() && splitterJson.get("id") != null && splitterJson.get("name") != null
                && splitterJson.get("minimum_split_length") != null && splitterJson.get("conditions") != null
                && splitterJson.get("exceptions") != null && splitterJson.get("conditions").isJsonArray()
                && splitterJson.get("exceptions").isJsonArray())) {
            throw new RuntimeException("Invalid splitter json");
        }
    }



}

