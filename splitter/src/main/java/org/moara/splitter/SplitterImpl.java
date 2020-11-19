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

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;

import org.moara.splitter.processor.regularExpression.RegularExpressionProcessorImpl;
import org.moara.splitter.role.PublicRoleManager;
import org.moara.splitter.role.RoleManager;
import org.moara.splitter.utils.Config;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 문장 분리기
 *
 * @author wjrmffldrhrl
 */
public class SplitterImpl implements Splitter {

    protected TerminatorAreaProcessor terminatorAreaProcessor;
    protected ExceptionAreaProcessor exceptionAreaProcessor;
    protected RegularExpressionProcessorImpl regularExpressionProcessor;
    protected PublicRoleManager publicRoleManager = (PublicRoleManager) PublicRoleManager.getRoleManager();

    /**
     *
     * Default constructor
     * SplitterFactory 만 접근 가능하다.
     *
     * @param roleManager news or basic role manager
     * @param config 문장 구분기 설정값
     */
    SplitterImpl(RoleManager roleManager, Config config) { initAreaProcessor(roleManager, config); }

    private void initAreaProcessor(RoleManager roleManager, Config config) {


        if (config.USE_PUBLIC_ROLE) {
            terminatorAreaProcessor = new TerminatorAreaProcessor(publicRoleManager, roleManager, config);
            exceptionAreaProcessor = new ExceptionAreaProcessor(publicRoleManager, roleManager);
            regularExpressionProcessor = new RegularExpressionProcessorImpl(publicRoleManager, roleManager);
        } else {
            terminatorAreaProcessor = new TerminatorAreaProcessor(roleManager, config);
            exceptionAreaProcessor = new ExceptionAreaProcessor(roleManager);
            regularExpressionProcessor = new RegularExpressionProcessorImpl(roleManager);
        }

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
        TreeSet<Integer> splitPoints = terminatorAreaProcessor.find(inputData);
        List<Area> regxAreas = regularExpressionProcessor.find(inputData);
        List<Area> exceptionAreas = exceptionAreaProcessor.find(inputData);
        List<Integer> removeItems = new ArrayList<>();

        for (Area regxArea : regxAreas) {
            splitPoints.add(regxArea.getEnd());
        }

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

