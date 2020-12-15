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
    protected final String[] emptyStrings = {"\n", "\t", " "};

    /**
     * 구분기 생성자
     * package-private 하기 때문에  SplitterManager에서만 접근 가능하다.
     *
     * @param terminatorAreaProcessor 구분 동작을 수행하는 processor
     * @param exceptionAreaProcessors 예외 영역을 지정해주는 processor, 두 개 이상 적용시킬 수 있다.
     *                                <p>
     *                                TODO 1. ExceptionAreaProcessor를 HashMap으로 관리
     *                                - 입출력만 hashMap 으로
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

    private Area[] doSplit(List<Integer> splitPoints, String inputData) {

        List<Area> areaList = new ArrayList<>();

        int beginPoint = 0;
        int endPoint;
        for (int splitPoint : splitPoints) {
            Area targetArea = getAreaWithOutEmpty(beginPoint, splitPoint, inputData);

            beginPoint = targetArea.getBegin();
            endPoint = targetArea.getEnd();

            // 해당 영역이 비어있으면 구분 영역으로 취급하지 않고 스킵한다.
            if (beginPoint == endPoint) {
                continue;
            }

            areaList.add(targetArea);
            beginPoint = endPoint;

        }

        if (beginPoint < inputData.length()) {
            Area targetArea = getAreaWithOutEmpty(beginPoint, inputData.length(), inputData);

            if (targetArea.getBegin() != targetArea.getEnd()) {
                areaList.add(targetArea);
            }
        }

        return areaList.toArray(new Area[0]);
    }

    private Area getAreaWithOutEmpty(int begin, int end, String inputData) {
        String targetString = inputData.substring(begin, end);

        for (int i = 0; i < targetString.length(); i++) {
            if (targetString.charAt(i) == ' ' || targetString.charAt(i) == '\n' || targetString.charAt(i) == '\t') {
                begin++;
            } else {
                break;
            }
        }

        for (int i = targetString.length() - 1; i > -1; i--) {
            if (targetString.charAt(i) == ' ' || targetString.charAt(i) == '\n' || targetString.charAt(i) == '\t') {
                end--;
            } else {
                break;
            }
        }

        if (begin >= end) {
            return new Area(begin);
        }

        return new Area(begin, end);
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
