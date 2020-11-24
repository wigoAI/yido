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


import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.role.SplitCondition;
import org.moara.splitter.role.SplitConditionManager;
import org.moara.splitter.utils.Config;

import java.util.HashMap;
import java.util.List;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 * TODO 1. splitter 생성 관련 메서드 추가하기
 *          - 기본 문장 구분기
 *          - 뉴스 문장 구분기
 *          - 커스텀 문장 구분기 생성 지원 메서드
 * @author wjrmffldrhrl
 */
public class SplitterFactory {
    private static final int BASIC_SPLITTER_ID = 1;
    private static final int NEWS_SPLITTER_ID = 2;
    private static final int CUSTOM_SPLITTER_ID = 9;
    private static final HashMap<Integer, Splitter> splitterHashMap = new HashMap<>();
    private static final SplitterFactory SPLITTER_FACTORY = new SplitterFactory();

    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSplitter를 반환한다.
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


    private static void createSplitter(int id) {
        if (id == BASIC_SPLITTER_ID) {
            String[] validationList = {"V_N_B_001"};
            List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_001", validationList);
            TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, new Config());
            ExceptionAreaProcessor exceptionAreaProcessor = new ExceptionAreaProcessor();

            splitterHashMap.put(BASIC_SPLITTER_ID,
                    new SplitterImpl(terminatorAreaProcessor, exceptionAreaProcessor));
        } else if(id == NEWS_SPLITTER_ID) {
            String[] validationList = {"V_N_B_002"};
            List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_002", validationList);
            TerminatorAreaProcessor terminatorAreaProcessor = new TerminatorAreaProcessor(splitConditions, new Config());

            ExceptionAreaProcessor exceptionAreaProcessor = new ExceptionAreaProcessor();
        } else {

            // throw new InvalidArgumentException();

        }
    }

    private static boolean isKeyEmpty(int key) { return !splitterHashMap.containsKey(key); }


}

