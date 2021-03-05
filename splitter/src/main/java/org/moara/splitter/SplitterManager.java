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
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.seomse.commons.config.Config;
import org.moara.splitter.exception.SplitterNotFoundException;
import org.moara.splitter.processor.BracketAreaProcessor;
import org.moara.splitter.processor.ConditionTerminatorProcessor;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorProcessor;
import org.moara.splitter.utils.FileReader;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.manager.SplitConditionManager;

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
    private static final String DEFAULT_SPLITTER_ID = Config.getConfig("yido.splitter.default.id", "sns");
    private final Map<String, Splitter> splitterMap = new HashMap<>();

    /**
     * 분리기 관리자 인스턴스 획득
     *
     * @return SplitterManager instance
     */
    public static SplitterManager getInstance() {
        return Singleton.instance;
    }

    private static class Singleton {
        private static final SplitterManager instance = new SplitterManager();
    }

    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSplitter 를 반환한다.
     *
     * @return BasicSplitter
     */
    public Splitter getSplitter() {
        return getSplitter(DEFAULT_SPLITTER_ID);
    }

    // thread lock
    private final Object createLock = new Object();

    public Splitter getSplitter(String id) {

        Splitter splitter = splitterMap.get(id);
        if (splitter == null) {
            synchronized (createLock) { // thread lock 을 걸고 splitter instance 확인
                splitter = splitterMap.get(id);
                if (splitter == null) {
                    createSplitter(id);
                    splitter = splitterMap.get(id);
                }
            }
        }

        return splitter;
    }

    // splitter 생성
    private void createSplitter(String id) {
        JsonObject splitterJson = getSplitterJson(id);

        // 구분 조건 생성
        List<String> conditionRuleNames = getConditionRuleNames(splitterJson);
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(conditionRuleNames);

        // Processors 생성
        int minResultLength = splitterJson.get("minimum_split_length").getAsInt(); // 최소 문장 길이
        TerminatorProcessor terminatorProcessor = new ConditionTerminatorProcessor(splitConditions, minResultLength); // 구분 처리기
        List<ExceptionAreaProcessor> exceptionAreaProcessors = getExceptionAreaProcessors(splitterJson);

        // 구분 결과 예외 단어
        List<String> exceptionWords = new ArrayList<>(Arrays.asList(" ", "\n", "\t"));
        boolean containSplitCondition = splitterJson.get("contain_split_condition").getAsBoolean(); // 구분점 포함 여부
        if (!containSplitCondition) {
            // 구분 조건 미 포함시 exceptionWords에 해당 조건들을 추가시켜
            // 문장 구분 결과에 구분 조건을 포함시키지 않는다.
            for (SplitCondition splitCondition : splitConditions) {
                exceptionWords.add(splitCondition.getValue());
            }
        }

        String key = splitterJson.get("id").getAsString(); // splitter key value
        splitterMap.put(key, new RuleSplitter(terminatorProcessor, exceptionAreaProcessors, exceptionWords));

    }

    private List<ExceptionAreaProcessor> getExceptionAreaProcessors(JsonObject splitterJson) {
        List<ExceptionAreaProcessor> exceptionAreaProcessors = new ArrayList<>();
        JsonArray exceptions = splitterJson.get("exceptions").getAsJsonArray();
        for (JsonElement exception : exceptions) {
            if (exception.getAsString().equals("bracket_exception")) {
                exceptionAreaProcessors.add(new BracketAreaProcessor());
            }
        }

        return exceptionAreaProcessors;
    }

    private List<String> getConditionRuleNames(JsonObject splitterJson) {
        List<String> conditionRuleNames = new ArrayList<>();
        JsonArray conditionArray = splitterJson.get("conditions").getAsJsonArray();

        for (JsonElement jsonObject : conditionArray) {
            conditionRuleNames.add(jsonObject.getAsString());
        }

        return conditionRuleNames;
    }

    private JsonObject getSplitterJson(String id) {
        JsonObject splitterJson;
        try {
            splitterJson = FileReader.getJsonObjectByFile("splitter/" + id);
        } catch (RuntimeException e) {
            System.out.println("splitter not found exception");
            throw new SplitterNotFoundException(id);
        }

        checkSplitterJsonValidation(splitterJson);
        return splitterJson;
    }

    private void checkSplitterJsonValidation(JsonObject splitterJson) {
        if (!(splitterJson.isJsonObject() && splitterJson.get("id") != null && splitterJson.get("name") != null
                && splitterJson.get("minimum_split_length") != null && splitterJson.get("conditions") != null
                && splitterJson.get("exceptions") != null && splitterJson.get("conditions").isJsonArray()
                && splitterJson.get("exceptions").isJsonArray()) && splitterJson.get("contain_split_condition") != null) {
            throw new JsonIOException("Invalid splitter json");
        }
    }

    /**
     * 구분기 reload
     *
     * 해당 메서드를 호출한 뒤 {@code SplitterManager.getInstance().getSplitter(splitterId)}를 통해 구분기를 새로 할당받아야 한다.
     * 기존에 참조하고 있는 구분기 인스턴스에는 영향이 가지 않는다.
     *
     * @param id reload 할 구분기 id
     */
    public void reloadSplitter(String id) {
        synchronized (createLock) { // thread lock 을 걸고 splitter instance 확인
            Splitter splitter = splitterMap.get(id);
            if (splitter == null) { throw new SplitterNotFoundException(id); }

            createSplitter(id);
        }

    }

}

