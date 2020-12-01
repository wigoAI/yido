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
import org.moara.splitter.utils.Config;
import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 *
 * @author wjrmffldrhrl
 */
public class SplitterFactory {
    private static final int BASIC_SPLITTER_ID = 1;
    private static final int NEWS_SPLITTER_ID = 2;
    private static final HashMap<Integer, Splitter> splitterHashMap = new HashMap<>();
    private static final HashMap<Integer, TerminatorAreaProcessor> terminatorAreaProcessorHashMap = new HashMap<>();

    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSplitter 를 반환한다.
     *
     * @return BasicSplitter
     */
    public static Splitter getSplitter() {
        if(isKeyEmpty(BASIC_SPLITTER_ID)) { createSplitter(BASIC_SPLITTER_ID); }

        return splitterHashMap.get(BASIC_SPLITTER_ID);
    }

    /**
     * 특정 id로 분류한 문장 구분기 인스턴스 반환
     *
     * @param id Splitter ID
     * @return Splitter
     */
    public static Splitter getSplitter(int id) {
        if(isKeyEmpty(id)) { createSplitter(id); }
        return splitterHashMap.get(id);
    }


    public static void createSplitter(String splitterJsonName, int key) {
        Splitter splitter = getSplitterFromJson(splitterJsonName, key);

        splitterHashMap.put(key, splitter);
    }

    public static void createSplitter(JsonObject splitterJson, int key) {
        Splitter splitter = getSplitterFromJson(splitterJson, key);
        splitterHashMap.put(key, splitter);
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    private static void createSplitter(int id) {
        if (id == BASIC_SPLITTER_ID) {

            Splitter splitter =  getSplitterFromJson("basic", id);
            splitterHashMap.put(BASIC_SPLITTER_ID, splitter);
        } else if (id == NEWS_SPLITTER_ID) {
            Splitter splitter = getSplitterFromJson("news", id);
            splitterHashMap.put(NEWS_SPLITTER_ID, splitter);

        } else {
            throw new IllegalArgumentException("Key [" + id + "] doesn't exist");
        }
    }

    private static Splitter getSplitterFromJson(String splitterJsonName, int key) {
        JsonObject splitterJson = FileManager.getJsonObjectByFile("splitter/" + splitterJsonName + ".json");

        return getSplitterFromJson(splitterJson,  key);
    }

    private static Splitter getSplitterFromJson(JsonObject splitterJson, int key) {
        Config config = new Config(splitterJson.get("minimum_split_length").getAsInt());
        JsonArray conditionArray = splitterJson.get("conditions").getAsJsonArray();

        List<String> conditionList = new ArrayList<>();
        for (JsonElement jsonObject : conditionArray) {
            conditionList.add(jsonObject.getAsString());
        }

        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(conditionList);
        TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, config);
        terminatorAreaProcessorHashMap.put(key, terminatorAreaProcessor);

        List<ExceptionAreaProcessor> exceptionAreaProcessors = new ArrayList<>();
        exceptionAreaProcessors.add(new BracketAreaProcessor());
        return new SplitterImpl(terminatorAreaProcessor, exceptionAreaProcessors);
    }

    private static boolean isKeyEmpty(int key) { return !splitterHashMap.containsKey(key); }

    public static void addSplitCondition(List<SplitCondition> additionalSplitCondition, int id) {
        TerminatorAreaProcessor terminatorAreaProcessor = getTerminatorAreaProcessorByKey(id);
        terminatorAreaProcessor.getSplitConditions().addAll(additionalSplitCondition);

    }

    public static void deleteSplitCondition(List<SplitCondition> unnecessarySplitCondition, int id) {
        TerminatorAreaProcessor terminatorAreaProcessor = getTerminatorAreaProcessorByKey(id);
        for (SplitCondition splitCondition : unnecessarySplitCondition) {

            terminatorAreaProcessor.getSplitConditions()
                    .removeIf(item -> item.getValue().equals(splitCondition.getValue()));
        }
    }

    private static TerminatorAreaProcessor getTerminatorAreaProcessorByKey(int id) {
        if(isKeyEmpty(id)) { throw new IllegalArgumentException("Key [" + id + "] doesn't exist"); }
        return terminatorAreaProcessorHashMap.get(id);
    }
}

