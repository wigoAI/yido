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

import com.github.wjrmffldrhrl.Area;
import org.moara.yido.processor.ExceptionAreaProcessor;
import org.moara.yido.processor.TerminatorAreaProcessor;

import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 문장 분리기
 * @author 조승현
 */
public class SentenceSplitterImpl implements SentenceSplitter {

    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    /**
     *
     * Default constructor
     * SentenceSplitterFactory 만 접근 가능하다.
     *
     * @param roleManager news or basic role manager
     * @param config 문장 구분기 설정값
     */
    SentenceSplitterImpl(RoleManager roleManager, Config config) { initAreaProcessor(roleManager, config); }

    private void initAreaProcessor(RoleManager roleManager, Config config) {
        terminatorAreaProcessor = new TerminatorAreaProcessor(roleManager, config);
        exceptionAreaProcessor = new ExceptionAreaProcessor(roleManager);
    }

    @Override
    public Sentence[] split(String inputData) {
        if(inputData == null || inputData.isEmpty()) {
            System.out.println("No Data");
            return new Sentence[0];
        }

        TreeSet<Integer> splitPoint = getSplitPoint(inputData);
        return doSplit(splitPoint, inputData);
    }

    private TreeSet<Integer> getSplitPoint(String inputData) {
        List<Area> exceptionAreas = exceptionAreaProcessor.find(inputData);
        TreeSet<Integer> splitPoints = terminatorAreaProcessor.find(inputData);
        List<Integer> removeItems = new ArrayList<>();

        for (Area exceptionArea : exceptionAreas) {
            for(int splitPoint : splitPoints) {
                if(exceptionArea.contains(splitPoint)) { removeItems.add(splitPoint); }
            }
        }

        for (int removeItem : removeItems) {
            splitPoints.remove(removeItem);
        }

        return splitPoints;
    }


    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String inputData) {
        Sentence[] result = new Sentence[splitPoint.size() + 1];
        int startIndex = 0;
        int resultIndex = 0;

        for(int point : splitPoint) {
            Sentence sentence = new Sentence(startIndex, point, inputData.substring(startIndex, point).trim());

            result[resultIndex++] = sentence;
            startIndex = point;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }
}

