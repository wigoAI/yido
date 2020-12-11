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
package org.moara.splitter.processor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.splitter.manager.SplitConditionManager;
import org.moara.splitter.manager.ValidationManager;
import org.moara.splitter.utils.Area;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;
import org.moara.splitter.utils.file.FileManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 구분 영역 처리기
 * <p>
 * TODO 1. 조건 변경시 lock
 * 2. refactoring
 *
 * @author wjrmffldrhrl
 */
public class TerminatorAreaProcessor {

    private SplitCondition[] splitConditions;
    private SplitCondition[] patternSplitConditions;

    private int[] conditionLengths; // 문장에서 조건을 찾을 때 사용됨

    private final Set<String> splitConditionValues = new HashSet<>(); // 빠른 탐색을 위해 HashSet
    private final int minResultLength;

    /**
     * Constructor
     * 구분기 생성시 함께 초기화 된다.
     * 구분 조건과 구분점 판별 시 사용될 정보들이 초기화 됨
     *
     * @param splitConditions 구분 조건
     * @param minResultLength 설정값
     */
    public TerminatorAreaProcessor(List<SplitCondition> splitConditions, int minResultLength) {
        this.splitConditions = splitConditions.toArray(new SplitCondition[0]);
        this.minResultLength = minResultLength;
        List<Integer> conditionLengths = new ArrayList<>();
        List<SplitCondition> patternSplitConditionList = new ArrayList<>();

        for (SplitCondition splitCondition : this.splitConditions) {
            if (splitCondition.isPattern()) {
                patternSplitConditionList.add(splitCondition);

            } else {
                splitConditionValues.add(splitCondition.getValue());

                if (!conditionLengths.contains(splitCondition.getValue().length())) {
                    conditionLengths.add(splitCondition.getValue().length());
                }
            }
        }

        this.patternSplitConditions = patternSplitConditionList.toArray(new SplitCondition[0]);

        updateConditionLengths(conditionLengths);

    }

    /**
     * 최소 문장 길이가 기본값 5로 설정된다.
     *
     * @param splitConditions 구분 조건 리스트
     */
    public TerminatorAreaProcessor(List<SplitCondition> splitConditions) {
        this(splitConditions, 5);
    }

    /**
     * rule이 10000개를 넘어가는 순간 text를 돌면서 조건을 찾는 방법으로 변경된다.
     *
     * @param text           구분점을 찾을 데이터
     * @param exceptionAreas 예외 영역
     * @return 구분점 리스트
     */
    public List<Integer> find(String text, List<Area> exceptionAreas) {
        text = text.trim();
//        if (splitConditions.length >= 10000) {

//        } else {
//            return findByRuleLoop(text, exceptionAreas);
//        }

        return  findByTextLoop(text, exceptionAreas);

    }


    private List<Integer> findByTextLoop(String text, List<Area> exceptionAreas) {
        List<Integer> splitPoints = new ArrayList<>();

        for (int processingLength : conditionLengths) {
            for (int i = text.length() - minResultLength; i >= 0; i--) {
                if (text.length() < i + processingLength) {
                    continue;
                }

                Area targetArea = new Area(i, i + processingLength);
                String targetString = text.substring(targetArea.getBegin(), targetArea.getEnd());

                if (splitConditionValues.contains(targetString)) {

                    SplitCondition targetSplitCondition = getSplitConditionByValue(targetString);

                    if (isValidCondition(text, targetSplitCondition, targetArea.getBegin())) {
                        int splitPoint;

                        if (targetSplitCondition.getSplitPosition() == 'F') {
                            splitPoint = targetArea.getBegin();
                        } else { // splitPosition == 'B'
                            int additionalSignLength = getAdditionalSignLength(targetArea.getEnd(), text);
                            splitPoint = targetArea.getEnd() + additionalSignLength;
                        }

                        if (isValidSplitPoint(exceptionAreas, splitPoints, text, splitPoint)) {
                            splitPoints.add(splitPoint);
                        }
                    }
                }
            }
        }

        for (SplitCondition patternSplitCondition : patternSplitConditions) {
            splitPoints.addAll(findSplitPointWithPattern(text, patternSplitCondition, exceptionAreas));
        }

        return splitPoints.stream().sorted().collect(Collectors.toList());
    }

    private SplitCondition getSplitConditionByValue(String targetString) {
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.getValue().equals(targetString)) {
                return splitCondition;
            }
        }

        throw new RuntimeException("No condition with this value : [" + targetString + "]");
    }


    private List<Integer> findByRuleLoop(String text, List<Area> exceptionAreas) {
        List<Integer> splitPoints = new ArrayList<>();
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.isPattern()) {
                splitPoints.addAll(findSplitPointWithPattern(text, splitCondition, exceptionAreas));
            } else {
                splitPoints.addAll(findSplitPointWithValue(text, splitCondition, exceptionAreas));
            }
        }

        return splitPoints.stream().sorted().collect(Collectors.toList());
    }

    private List<Integer> findSplitPointWithValue(String text, SplitCondition splitCondition, List<Area> exceptionAreas) {
        List<Integer> splitPoints = new ArrayList<>();

        int splitPoint = -1;
        while (true) {
            splitPoint = text.indexOf(splitCondition.getValue(), splitPoint);
            if (splitPoint == -1) {
                break;
            } // 구분 조건 x

            if (!isValidCondition(text, splitCondition, splitPoint)) {
                splitPoint += splitCondition.getValue().length();
                continue;
            }

            if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                splitPoint += splitCondition.getValue().length();
                splitPoint += getAdditionalSignLength(splitPoint, text);

                if (isValidSplitPoint(exceptionAreas, splitPoints, text, splitPoint)) {
                    splitPoints.add(splitPoint);
                }

            } else { // 앞
                if (isValidSplitPoint(exceptionAreas, splitPoints, text, splitPoint)) {
                    splitPoints.add(splitPoint);
                }

                splitPoint += splitCondition.getValue().length();
            }
        }

        return splitPoints;
    }


    private List<Integer> findSplitPointWithPattern(String text, SplitCondition splitCondition, List<Area> exceptionAreas) {

        Pattern pattern = Pattern.compile(splitCondition.getValue());
        Matcher matcher = pattern.matcher(text);
        List<Integer> splitPoints = new ArrayList<>();

        while (matcher.find()) {
            int splitPoint;
            if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                splitPoint = matcher.end();
                splitPoint += getAdditionalSignLength(splitPoint, text);

            } else { // 앞
                splitPoint = matcher.start();
            }

            if (isValidSplitPoint(exceptionAreas, splitPoints, text, splitPoint)) {
                splitPoints.add(splitPoint);
            }
        }

        return splitPoints;
    }

    private boolean isValidCondition(String text, SplitCondition splitCondition, int conditionBeginPoint) {
        boolean isValid = true;

        if (conditionBeginPoint < minResultLength || conditionBeginPoint > text.length() - minResultLength) {
            return false;
        }

        for (Validation validation : splitCondition.getValidations()) {
            int compareIndexStart = conditionBeginPoint;

            if (validation.getComparePosition() == 'B') {
                compareIndexStart += splitCondition.getValue().length();
            } else {
                compareIndexStart -= validation.getValue().length();
            }

            if (compareIndexStart + validation.getValue().length() > text.length()) {
                continue;
            }

            String compareText = text.substring(compareIndexStart, compareIndexStart + validation.getValue().length());

            boolean isEquals = compareText.equals(validation.getValue());

            if ((validation.getMatchFlag() == 'N' && isEquals) ||
                    (validation.getMatchFlag() == 'Y' && !isEquals)) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    private int getAdditionalSignLength(int startIndex, String text) {
        int additionalSignLength = 0;
        String regular = "[ㄱ-ㅎㅏ-ㅣ\\.\\?\\!\\~\\;\\^]";
        Pattern pattern = Pattern.compile(regular);

        for (int i = 0; i + startIndex < text.length(); i++) {
            String targetStr = text.substring(startIndex + i, startIndex + i + 1);

            if (!pattern.matcher(targetStr).matches()) {
                break;
            } else {
                additionalSignLength++;
            }
        }

        return additionalSignLength;
    }


    private boolean isValidSplitPoint(List<Area> exceptionAreas, List<Integer> splitPoints, String tmpText, int splitPoint) {

        for (Area exceptionArea : exceptionAreas) {
            if (exceptionArea.contains(splitPoint)) {
                return false;
            }
        }

        // check near points
        for (int p = 0; p <= minResultLength; p++) {
            if (splitPoints.contains(splitPoint + p)) {
                return false;
            }
        }

        // check out of range
        return splitPoint >= minResultLength && splitPoint <= tmpText.length() - minResultLength;
    }


    /**
     * 문장 구분 조건 추가
     *
     * @param additionalSplitCondition 추가할 문장 구분 조건
     */
    public synchronized void addSplitConditions(SplitCondition additionalSplitCondition) {



        List<Integer> conditionLengths = new ArrayList<>();
        for (int i : this.conditionLengths) {
            conditionLengths.add(i);
        }


        if (additionalSplitCondition.isPattern()) {
            patternSplitConditions[patternSplitConditions.length - 1] = additionalSplitCondition;
            SplitCondition[] patternSplitConditions = new SplitCondition[this.patternSplitConditions.length + 1];

            for (int i = 0; i < this.patternSplitConditions.length; i++) {
                patternSplitConditions[i] = this.patternSplitConditions[i];
            }

            this.patternSplitConditions = patternSplitConditions;
        } else {
            SplitCondition[] splitConditions = new SplitCondition[this.splitConditions.length + 1];


            for (int i = 0; i < this.splitConditions.length; i++) {
                splitConditions[i] = this.splitConditions[i];
            }

            splitConditions[splitConditions.length - 1] = additionalSplitCondition;
            splitConditionValues.add(additionalSplitCondition.getValue());

            if (!conditionLengths.contains(additionalSplitCondition.getValue().length())) {
                conditionLengths.add(additionalSplitCondition.getValue().length());
            }
            this.splitConditions = splitConditions;
        }




        updateConditionLengths(conditionLengths);
    }

    /**
     * 문장 구분 조건 제거
     *
     * @param unnecessarySplitCondition 제거할 문장 구분 조건
     */
    public synchronized void deleteSplitConditions(SplitCondition unnecessarySplitCondition) {

        boolean removeLengthFlag = true;
        if (unnecessarySplitCondition.isPattern()) {

            SplitCondition[] patternSplitConditions = new SplitCondition[this.patternSplitConditions.length - 1];

            for (int i = 0; i < patternSplitConditions.length; i++) {

                if (!this.patternSplitConditions[i].getValue().equals(unnecessarySplitCondition.getValue())) {
                    patternSplitConditions[i] = this.patternSplitConditions[i];
                }
            }

            this.patternSplitConditions = patternSplitConditions;
            removeLengthFlag = false;
        } else {
            SplitCondition[] splitConditions = new SplitCondition[this.splitConditions.length - 1];

            for (int i = 0; i < splitConditions.length; i++) {
                if (!this.splitConditions[i].getValue().equals(unnecessarySplitCondition.getValue())) {
                    splitConditions[i] = this.splitConditions[i];
                }
            }
            this.splitConditions = splitConditions;

            splitConditionValues.remove(unnecessarySplitCondition.getValue());

            // 같은 길이의 조건이 있다면
            // 문자열 길이 set에서 해당 길이를 제거하지 않는다.
            for (String value : splitConditionValues) {
                if (value.length() == unnecessarySplitCondition.getValue().length()) {
                    removeLengthFlag = false;
                    break;
                }
            }
        }




        if (removeLengthFlag) {
            List<Integer> conditionLengths = new ArrayList<>();
            for (int i : this.conditionLengths) {
                conditionLengths.add(i);
            }

            conditionLengths.removeIf(length -> length == unnecessarySplitCondition.getValue().length());

            updateConditionLengths(conditionLengths);

        }
    }

    private void updateConditionLengths(List<Integer> conditionLengths) {
        this.conditionLengths = new int[conditionLengths.size()];

        conditionLengths.sort(Comparator.reverseOrder());

        int i = 0;
        for (Integer length : conditionLengths) {
            this.conditionLengths[i++] = length;
        }

    }

    /**
     * 문장 유효성 추가
     *
     * @param additionalValidation 추가 할 유효성
     */
    public synchronized void addValidation(Validation additionalValidation) {
        for (SplitCondition splitCondition : splitConditions) {
            splitCondition.getValidations().add(additionalValidation);
        }
    }

    /**
     * 문장 유효성 제거
     *
     * @param unnecessaryValidation 제거 할 문장 유효성
     */
    public synchronized void deleteValidation(Validation unnecessaryValidation) {
        for (SplitCondition splitCondition : splitConditions) {
            splitCondition.getValidations().removeIf(item -> item.getValue().equals(unnecessaryValidation.getValue()));
        }
    }
}
