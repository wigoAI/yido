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

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;
import org.moara.splitter.Config;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 구분점 처리기
 *
 * TODO 1. 조건 lock
 * @author wjrmffldrhrl
 */
public class TerminatorAreaProcessor {

    private final List<SplitCondition> splitConditions;
    private final List<SplitCondition> patternSplitConditions = new ArrayList<>();
    private final Set<Integer> conditionLengths = new HashSet<>();
    private final Set<String> splitConditionValues = new HashSet<>();
    private boolean isContainPatternCondition = false;
    private final Config config;

    /**
     * Constructor
     * @param splitConditions 구분 조건
     * @param config 설정값
     */
    public TerminatorAreaProcessor(List<SplitCondition> splitConditions, Config config) {
        this.splitConditions = splitConditions;
        this.config = config;


        for (SplitCondition splitCondition : splitConditions) {
            if (!splitCondition.isPattern()) {
                splitConditionValues.add(splitCondition.getValue());
                conditionLengths.add(splitCondition.getValue().length());
            } else {
                isContainPatternCondition = true;
                patternSplitConditions.add(splitCondition);
            }
        }

    }

    /**
     * 구분점 찾기
     *
     * @param text 구분점을 찾을 데이터
     * @return 구분점
     */
    public List<Integer> find(String text, List<Area> exceptionAreas) {

        text = text.trim();

        List<Integer> splitPoint = findByText(text, exceptionAreas);
        splitPoint.sort(null);

        return splitPoint;
    }

    private List<Integer> findByText(String text, List<Area> exceptionAreas) {
        Set<Integer> splitPoints = new HashSet<>();
        String tmpText = text.trim();

        conditionLengths.stream().sorted(Comparator.reverseOrder()).forEach(processingLength -> {
            for(int i = tmpText.length() - config.MIN_RESULT_LENGTH; i >= 0 ; i--)  {
                if (tmpText.length() < i + processingLength) { continue; }

                Area targetArea = new Area(i, i + processingLength);
                String targetString = tmpText.substring(targetArea.getBegin(), targetArea.getEnd());

                if(splitConditionValues.contains(targetString)) {

                    SplitCondition splitCondition = splitConditions.stream()
                            .filter(c -> c.getValue().equals(targetString)).findAny()
                            .orElseThrow(() -> new RuntimeException("No condition with this value : [" + targetString + "]"));

                    if(isValidCondition(tmpText, splitCondition, targetArea.getBegin())) {
                        int splitPoint;
                        if (splitCondition.getSplitPosition() == 'F') {
                            splitPoint = targetArea.getBegin();
                        } else {
                            int additionalSignLength = getAdditionalSignLength(targetArea.getEnd(), tmpText);
                            splitPoint = targetArea.getEnd() + additionalSignLength;
                        }

                        if (isValidSplitPoint(exceptionAreas, splitPoints, tmpText, splitPoint)) continue;

                        splitPoints.add(splitPoint);
                    }
                }
            }
        });



        if (isContainPatternCondition) {
            for (SplitCondition splitCondition : patternSplitConditions) {
                splitPoints.addAll(findSplitPointWithPattern(text, splitCondition));
            }
        }



        return splitPoints.stream().sorted().collect(Collectors.toList());
    }

    private boolean isValidSplitPoint(List<Area> exceptionAreas, Set<Integer> splitPoints, String tmpText, int splitPoint) {
        boolean invalidSplitPointFlag = false;

        for (Area exceptionArea : exceptionAreas) {
            if (exceptionArea.contains(splitPoint)) {
                invalidSplitPointFlag = true;
                break;
            }
        }

        for (int p = 0; p <= config.MIN_RESULT_LENGTH && !invalidSplitPointFlag; p++) {
            if (splitPoints.contains(splitPoint + p)) {
                invalidSplitPointFlag = true;
                break;
            }
        }

        if (splitPoint < config.MIN_RESULT_LENGTH
                || splitPoint > tmpText.length() - config.MIN_RESULT_LENGTH) {
            return true;
        }

        return invalidSplitPointFlag;
    }


    private List<Integer> findSplitPoint(String text) {
        List<Integer> splitPoint = new ArrayList<>();
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.isPattern()) {
                splitPoint.addAll(findSplitPointWithPattern(text, splitCondition));
            } else {
                splitPoint.addAll(findSplitPointWithValue(text, splitCondition));
            }
        }

        return splitPoint;
    }

    private List<Integer> findSplitPointWithValue(String text, SplitCondition splitCondition) {
        List<Integer> splitPoints = new ArrayList<>();
        int splitPoint = -1;
        while (true) {
            splitPoint = text.indexOf(splitCondition.getValue(), splitPoint);
            if (splitPoint == -1) { break; } // 구분 조건 x

            if (!isValidCondition(text, splitCondition, splitPoint)) {
                splitPoint += splitCondition.getValue().length();
                continue;
            }

            if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                splitPoint += splitCondition.getValue().length();
                splitPoint += getAdditionalSignLength(splitPoint, text);
                splitPoints.add(splitPoint);
            } else { // 앞
                splitPoints.add(splitPoint);
                splitPoint += splitCondition.getValue().length();
            }
        }
        return splitPoints;
    }

    private List<Integer> findSplitPointWithPattern(String text,  SplitCondition splitCondition) {
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
            splitPoints.add(splitPoint);
        }

        return splitPoints;
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


    private boolean isValidCondition(String text, SplitCondition splitCondition, int conditionBeginPoint) {
        boolean isValid = true;

        if (conditionBeginPoint < config.MIN_RESULT_LENGTH || conditionBeginPoint > text.length() - config.MIN_RESULT_LENGTH) {
            return false;
        }

        for (Validation validation : splitCondition.getValidations()) {
            int compareIndexStart = conditionBeginPoint;

            if (validation.getComparePosition() == 'B') {
                compareIndexStart += splitCondition.getValue().length();
            } else {
                compareIndexStart -= validation.getValue().length();
            }

            if (compareIndexStart + validation.getValue().length() > text.length()) { continue; }

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


    /**
     * 문장 구분 조건 추가
     * @param additionalSplitCondition 추가할 문장 구분 조건
     */
    public void addSplitConditions(List<SplitCondition> additionalSplitCondition) {
        for (SplitCondition splitCondition : additionalSplitCondition) {
            splitConditions.add(splitCondition);
            conditionLengths.add(splitCondition.getValue().length());
            splitConditionValues.add(splitCondition.getValue());
        }


    }

    /**
     * 문장 구분 조건 제거
     * @param unnecessarySplitCondition
     */
    public void deleteSplitConditions(List<SplitCondition> unnecessarySplitCondition) {
        for (SplitCondition splitCondition : unnecessarySplitCondition) {
            splitConditions.removeIf(item -> item.getValue().equals(splitCondition.getValue()));
            splitConditionValues.remove(splitCondition.getValue());

            // 같은 길이의 조건이 있다면
            // 문자열 길이 set에서 해당 길이를 제거하지 않는다.
            boolean isContainLength = false;
            for (String value : splitConditionValues) {
                if (value.length() == splitCondition.getValue().length()) {
                    isContainLength = true;
                    break;
                }
            }

            if (!isContainLength) {
                conditionLengths.remove(splitCondition.getValue().length());
            }

        }
    }

    /**
     * 문장 유효성 추가
     * @param additionalValidations 추가 할 유효성
     */
    public void addValidation(List<Validation> additionalValidations) {
        for (SplitCondition splitCondition : splitConditions) {
            splitCondition.getValidations().addAll(additionalValidations);
        }
    }

    /**
     * 문장 유효성 제거
     * @param unnecessaryValidations 제거 할 문장 유효성
     */
    public void deleteValidation(List<Validation> unnecessaryValidations) {
        for (SplitCondition splitCondition : splitConditions) {
            for (Validation validation : unnecessaryValidations) {
                splitCondition.getValidations().removeIf(item -> item.getValue().equals(validation.getValue()));
            }
        }
    }
}
