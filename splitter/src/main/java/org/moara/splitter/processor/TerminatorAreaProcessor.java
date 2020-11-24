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

import org.moara.splitter.role.SplitCondition;
import org.moara.splitter.role.Validation;
import org.moara.splitter.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 구분점 처리기
 *
 *
 * @author wjrmffldrhrl
 */
public class TerminatorAreaProcessor {

    private final List<SplitCondition> splitConditions;
    private final Config config;

    /**
     * Constructor
     * @param splitConditions 구분 조건
     * @param config 설정값
     */
    public TerminatorAreaProcessor(List<SplitCondition> splitConditions, Config config) {
        this.splitConditions = splitConditions;
        this.config = config;
    }

    /**
     * 구분점 찾기
     * @param text 구분점을 찾을 데이터
     * @return 구분점 TreeSet
     */
    public TreeSet<Integer> find(String text) {
        TreeSet<Integer> splitPointSet = new TreeSet<>();
        text = text.trim();

        findSplitPoint(text, splitPointSet);

        removeInvalidItem(splitPointSet, text.length());
        return splitPointSet;
    }

    private void findSplitPoint(String text, TreeSet<Integer> splitPointSet) {
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.isPattern()) {
                findSplitPointWithPattern(splitPointSet, text, splitCondition);
            } else {
                findSplitPointWithValue(splitPointSet, text, splitCondition);
            }
        }
    }

    private void findSplitPointWithValue(TreeSet<Integer> splitPointSet, String text, SplitCondition splitCondition) {
        int splitPoint = -1;
        while (true) {
            splitPoint = text.indexOf(splitCondition.getValue(), splitPoint);
            if (splitPoint == -1) { break; } // 구분 조건 x

            if (!isValid(text, splitCondition, splitPoint)) {
                splitPoint += splitCondition.getValue().length();
                continue;
            }

            if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                splitPoint += splitCondition.getValue().length();
                splitPoint += getAdditionalSignLength(splitPoint, text);
                splitPointSet.add(splitPoint);
            } else { // 앞
                splitPointSet.add(splitPoint);
                splitPoint += splitCondition.getValue().length();
            }
        }
    }

    private void findSplitPointWithPattern(TreeSet<Integer> splitPointSet, String text,  SplitCondition splitCondition) {
        Pattern pattern = Pattern.compile(splitCondition.getValue());
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                int splitPoint = matcher.end();
                splitPoint += splitCondition.getValue().length();
                splitPoint += getAdditionalSignLength(splitPoint, text);
                splitPointSet.add(splitPoint);
            } else { // 앞
                int splitPoint = matcher.start();
                splitPointSet.add(splitPoint);
            }
        }
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

    private boolean isValid(String text, SplitCondition splitCondition, int splitPoint) {
        boolean isValid = true;

        if (splitPoint < config.MIN_RESULT_LENGTH || splitPoint > text.length() - config.MIN_RESULT_LENGTH) {
            return false;
        }

        for (Validation validation : splitCondition.getValidations()) {
            int compareIndexStart = splitPoint;
            if (validation.getComparePosition() == 'B') { compareIndexStart += splitCondition.getValue().length();
            } else { compareIndexStart -= validation.getValue().length(); }

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

    private void removeInvalidItem(TreeSet<Integer> splitPoints, int textLength) {
        splitPoints.removeIf(item -> item < config.MIN_RESULT_LENGTH ||
                item > textLength - config.MIN_RESULT_LENGTH);

        List<Integer> removeItems = new ArrayList<>();
        int previousItem = 0;
        for (int item : splitPoints) {
            if (item - previousItem < config.MIN_RESULT_LENGTH) {
                removeItems.add(previousItem);
            }

            previousItem = item;
        }
        splitPoints.removeAll(removeItems);
    }
}
