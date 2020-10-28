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

import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.BasicRoleManager;

import java.util.TreeSet;

/**
 * 문장 분리기
 * @author 조승현
 */
public class BasicSentenceSplitter implements SentenceSplitter {

    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    /**
     * Default constructor
     * SentenceSplitterFactory 만 접근 가능하다.
     * @param config Config
     */
    BasicSentenceSplitter(Config config) { initAreaProcessor(config); }

    private void initAreaProcessor(Config config) {

        this.terminatorAreaProcessor = new TerminatorAreaProcessor(BasicRoleManager.getRoleManager(), config);
        this.exceptionAreaProcessor = new ExceptionAreaProcessor(BasicRoleManager.getRoleManager());
    }

    @Override
    public Sentence[] split(String inputData) {
        if(inputData == null || inputData.isEmpty()) {
            System.out.println("No Data");
            return new Sentence[0];
        }

        this.exceptionAreaProcessor.find(inputData);
        TreeSet<Integer> splitPoint = findSplitPoint(inputData);

        return doSplit(splitPoint, inputData);
    }

    private TreeSet<Integer> findSplitPoint(String inputData) {
        return this.terminatorAreaProcessor.find(inputData, this.exceptionAreaProcessor);
    }

    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String inputData) {
        int startIndex = 0;
        int endIndex = 0;
        int resultIndex = 0;
        Sentence[] result = new Sentence[splitPoint.size() + 1];

        for(int point : splitPoint) {
            endIndex = point;
            Sentence sentence = new Sentence(startIndex, endIndex, inputData.substring(startIndex, endIndex).trim());

            result[resultIndex++] = sentence;
            startIndex = endIndex;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }
}

