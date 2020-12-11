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


import com.seomse.commons.data.BeginEnd;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.utils.Area;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * 구분기 구현체
 *
 * @author wjrmffldrhrl
 */
class RuleSplitter implements Splitter {

    protected final TerminatorAreaProcessor terminatorAreaProcessor;
    protected final ExceptionAreaProcessor[] exceptionAreaProcessors;

    /**
     * 구분기 생성자
     * package-private 하기 때문에  SplitterManager에서만 접근 가능하다.
     *
     * @param terminatorAreaProcessor 구분 동작을 수행하는 processor
     * @param exceptionAreaProcessors 예외 영역을 지정해주는 processor, 두 개 이상 적용시킬 수 있다.
     *                                
     *  TODO 1. ExceptionAreaProcessor를 HashMap으로 관리
     *          - 입출력만 hashMap 으로
     */
    RuleSplitter(TerminatorAreaProcessor terminatorAreaProcessor,
                 List<ExceptionAreaProcessor> exceptionAreaProcessors) {
        this.terminatorAreaProcessor = terminatorAreaProcessor;
        this.exceptionAreaProcessors = exceptionAreaProcessors.toArray(new ExceptionAreaProcessor[0]);
    }

    @Override
    public BeginEnd[] split(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text is null or empty");
        }

        List<Integer> splitPoint = getSplitPoint(text);

        return doSplit(splitPoint, text);

    }

    private List<Integer> getSplitPoint(String text) {
        List<Area> exceptionAreas = new ArrayList<>();
        for (ExceptionAreaProcessor exceptionAreaProcessor : exceptionAreaProcessors) {
            exceptionAreas.addAll(exceptionAreaProcessor.find(text));
        }

        return terminatorAreaProcessor.find(text, exceptionAreas);
    }


    public void addSplitConditions(SplitCondition additionalSplitCondition) {
        terminatorAreaProcessor.addSplitConditions(additionalSplitCondition);
    }

    public void deleteSplitConditions(SplitCondition unnecessarySplitCondition) {

        terminatorAreaProcessor.deleteSplitConditions(unnecessarySplitCondition);
    }

    public void addValidation(Validation additionalValidations) {
        terminatorAreaProcessor.addValidation(additionalValidations);
    }

    public void deleteValidation(Validation unnecessaryValidations) {
        terminatorAreaProcessor.deleteValidation(unnecessaryValidations);
    }

    private Area[] doSplit(List<Integer> splitPoint, String inputData) {
        Area[] result = new Area[splitPoint.size() + 1];
        int startIndex = 0;
        int resultIndex = 0;

        for(int point : splitPoint) {
            Area splitResult = new Area(startIndex, point);

            result[resultIndex++] = splitResult;
            startIndex = point;

        }

        result[resultIndex] =  new Area(startIndex, inputData.length());

        return result;
    }


    /**
     * Thread test
     */
    public static void main(String[] args) {

        String data = args[0];
        System.out.println(data);
        Splitter splitter = SplitterManager.getInstance().getSplitter();

        for (BeginEnd splitPoint : splitter.split(data)) {
            System.out.println(data.substring(splitPoint.getBegin(), splitPoint.getEnd()));

        }

    }
}
