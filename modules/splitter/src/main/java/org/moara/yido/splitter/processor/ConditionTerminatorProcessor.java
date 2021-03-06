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
package org.moara.yido.splitter.processor;

import org.moara.yido.splitter.manager.SplitConditionManager;
import org.moara.yido.splitter.utils.Area;
import org.moara.yido.splitter.utils.SplitCondition;
import org.moara.yido.splitter.utils.Validation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 구분 영역 처리기
 *
 *
 * @author wjrmffldrhrl
 */
public class ConditionTerminatorProcessor implements TerminatorProcessor{

    private SplitCondition[] splitConditions;
    private SplitCondition[] patternSplitConditions;

    private int[] conditionLengths; // 문장에서 조건을 찾을 때 사용됨

    private final Set<String> splitConditionValues = new HashSet<>(); // 빠른 탐색을 위해 HashSet
    private final int minResultLength;

    /**
     * Constructor
     * 구분기 생성시 함께 초기화 된다.
     * 구분 조건과 구분점 판별 시 사용될 정보들이 초기화 된다.
     *
     * @param splitConditions 구분 조건
     * @param minResultLength 설정값
     *
     */
    public ConditionTerminatorProcessor(List<SplitCondition> splitConditions, int minResultLength) {
        this.minResultLength = minResultLength;
        List<Integer> conditionLengths = new ArrayList<>();
        List<SplitCondition> patternSplitConditionList = new ArrayList<>();
        List<SplitCondition> splitConditionList = new ArrayList<>();
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.isPattern()) {
                patternSplitConditionList.add(splitCondition);

            } else {
                splitConditionList.add(splitCondition);
                splitConditionValues.add(splitCondition.getValue());

                if (!conditionLengths.contains(splitCondition.getValue().length())) {
                    conditionLengths.add(splitCondition.getValue().length());
                }
            }
        }

        this.splitConditions = splitConditionList.toArray(new SplitCondition[0]);
        this.patternSplitConditions = patternSplitConditionList.toArray(new SplitCondition[0]);

        updateConditionLengths(conditionLengths);
    }

    /**
     * 최소 문장 길이가 기본값 5로 설정된다.
     *
     * @param splitConditions 구분 조건 리스트
     */
    public ConditionTerminatorProcessor(List<SplitCondition> splitConditions) {
        this(splitConditions, 5);
    }

    @Override
    public int[] find(String text, List<Area> exceptionAreas) {
        if (patternSplitConditions.length == 0 && splitConditions.length == 0) {
            throw new RuntimeException("This splitter has no conditions");
        }

        if (patternSplitConditions.length > 0) {
            return findByRuleLoop(text, exceptionAreas);
        } else {
            return findByTextLoop(text, exceptionAreas);
        }

    }

    private int[] findByTextLoop(String text, List<Area> exceptionAreas) {

        int[] tmpSplitPoint = new int[text.length() / minResultLength + 1];
        int splitPointCount = 0;


        for (int i = text.length() - minResultLength; i >= 0; i--) {
            for (int processingLength : conditionLengths) {

                if (text.length() < i + processingLength || i <= 0) {
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

                        if (isValidSplitPoint(exceptionAreas, text, splitPoint)) {

                            tmpSplitPoint[splitPointCount] = splitPoint;
                            splitPointCount++;
                            i -= minResultLength;
                        }
                    }
                }
            }
        }

        int[] splitPoints = new int[splitPointCount];

        for (int i = 0; i < splitPoints.length; i++) {
            splitPoints[i] = tmpSplitPoint[--splitPointCount];
        }

        return splitPoints;
    }


    // 룰을 기준으로 구분점을 찾는 경우 구분점을 순서대로 파악하는 것이 아니기 때문에
    // 인접한 구분점을 제거해주는 후처리 작업이 필요하다.
    private int[] findByRuleLoop(String text, List<Area> exceptionAreas) {
        List<Integer> splitPoints = new ArrayList<>();
        for (SplitCondition splitCondition : splitConditions) {
            splitPoints.addAll(findSplitPointWithValue(text, splitCondition, exceptionAreas));
        }
        for (SplitCondition splitCondition : patternSplitConditions) {
            splitPoints.addAll(findSplitPointWithPattern(text, splitCondition, exceptionAreas));
        }

        splitPoints = splitPoints.stream().distinct().sorted().collect(Collectors.toList());

        List<Integer> invalidSplitPoints = new ArrayList<>();

        if (splitPoints.size() < 2) {
            return splitPoints.stream().mapToInt(x -> x).toArray();
        }

        int standardSplitPoint = splitPoints.get(splitPoints.size() - 1);
        for (int i = splitPoints.size() - 2; i >= 0; i--) {
            int targetSplitPoint = splitPoints.get(i);

            if (standardSplitPoint - targetSplitPoint < minResultLength) {
                invalidSplitPoints.add(targetSplitPoint);
            } else {
                standardSplitPoint = targetSplitPoint;
            }
        }

        splitPoints.removeIf(invalidSplitPoints::contains);

        return splitPoints.stream().mapToInt(x -> x).toArray();
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
            }

            if (isValidSplitPoint(exceptionAreas, text, splitPoint)) {
                splitPoints.add(splitPoint);
            }

            if (splitCondition.getSplitPosition() == 'F') { // 문장 구분점 앞
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

            if (isValidSplitPoint(exceptionAreas, text, splitPoint)) {

                splitPoints.add(splitPoint);
            }

        }

        return splitPoints;
    }

    private SplitCondition getSplitConditionByValue(String targetString) {
        for (SplitCondition splitCondition : splitConditions) {
            if (splitCondition.getValue().equals(targetString)) {
                return splitCondition;
            }
        }

        throw new RuntimeException("No condition with this value : [" + targetString + "]");
    }

    private boolean isValidCondition(String text, SplitCondition splitCondition, int conditionBeginPoint) {
        boolean isValid = true;

        // 구분점 범위 체크
        if (conditionBeginPoint < minResultLength || conditionBeginPoint > text.length() - minResultLength) {
            return false;
        }


        // 구분 조건 유효성 체크
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

            if ((!validation.getMatchFlag() && isEquals) ||
                    (validation.getMatchFlag() && !isEquals)) {
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

    private boolean isValidSplitPoint(List<Area> exceptionAreas, String tmpText, int splitPoint) {


        for (Area exceptionArea : exceptionAreas) {
            if (exceptionArea.contains(splitPoint)) {
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
            SplitCondition[] patternSplitConditions = new SplitCondition[this.patternSplitConditions.length + 1];

            int i = 0;
            for (; i < this.patternSplitConditions.length; i++) {
                patternSplitConditions[i] = this.patternSplitConditions[i];
            }

            patternSplitConditions[i] = additionalSplitCondition;

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

            this.patternSplitConditions = Arrays.stream(this.patternSplitConditions).filter(splitCondition ->
                    !splitCondition.getValue().equals(unnecessarySplitCondition.getValue())).toArray(SplitCondition[]::new);

            removeLengthFlag = false;

        } else {
            this.splitConditions = Arrays.stream(this.splitConditions).filter(splitCondition ->
                    !splitCondition.getValue().equals(unnecessarySplitCondition.getValue())).toArray(SplitCondition[]::new);


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


    public static void main(String[] args) {
        String data = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다. 역시 토끼정 본 점 답죠?ㅎㅅㅎ 건물은 크지만 간판이 없기 때문에 지나칠 수 있으니 조심하세요 강남 토끼정의 내부 인테리어. 평일 저녁이었지만 강남역 맛집 답게 사람들이 많았어요. 전체적으로 편안하고 아늑한 공간으로 꾸며져 있었습니다ㅎㅎ 한 가지 아쉬웠던 건 조명이 너무 어두워 눈이 침침했던... 저희는 3층에 자리를 잡고 음식을 주문했습니다. 총 5명이서 먹고 싶은 음식 하나씩 골라 다양하게 주문했어요 첫 번째 준비된 메뉴는 토끼정 고로케와 깻잎 불고기 사라다를 듬뿍 올려 먹는 맛있는 밥입니다. 여러가지 메뉴를 한 번에 시키면 준비되는 메뉴부터 가져다 주더라구요. 토끼정 고로케 금방 튀겨져 나와 겉은 바삭하고 속은 촉촉해 맛있었어요! 깻잎 불고기 사라다는 불고기, 양배추, 버섯을 볶아 깻잎을 듬뿍 올리고 우엉 튀김을 곁들여 밥이랑 함께 먹는 메뉴입니다. 사실 전 고기를 안 먹어서 무슨 맛인지 모르겠지만.. 다들 엄청 잘 드셨습니다ㅋㅋ 이건 제가 시킨 촉촉한 고로케와 크림스튜우동. 강남 토끼정에서 먹은 음식 중에 이게 제일 맛있었어요!!! 크림소스를 원래 좋아하기도 하지만, 느끼하지 않게 부드럽고 달달한 스튜와 쫄깃한 우동면이 너무 잘 어울려 계속 손이 가더라구요. 사진을 보니 또 먹고 싶습니다 간사이 풍 연어 지라시입니다. 일본 간사이 지방에서 많이 먹는 떠먹는 초밥(지라시스시)이라고 하네요. 밑에 와사비 마요밥 위에 연어들이 담겨져 있어 코끝이 찡할 수 있다고 적혀 있는데, 난 와사비 맛 1도 모르겠던데...? 와사비를 안 좋아하는 저는 불행인지 다행인지 연어 지라시를 매우 맛있게 먹었습니다ㅋㅋㅋ 다음 메뉴는 달짝지근한 숯불 갈비 덮밥입니다! 간장 양념에 구운 숯불 갈비에 양파, 깻잎, 달걀 반숙을 터트려 비벼 먹으면 그 맛이 크.. (물론 전 안 먹었지만...다른 분들이 그렇다고 하더라구요ㅋㅋㅋㅋㅋㅋㅋ) 마지막 메인 메뉴 양송이 크림수프와 숯불떡갈비 밥입니다. 크림리조또를 베이스로 위에 그루통과 숯불로 구운 떡갈비가 올라가 있어요! 크림스튜 우동 만큼이나 대박 맛있습니다...ㅠㅠㅠㅠㅠㅠ (크림 소스면 다 좋아하는 거 절대 아닙니다ㅋㅋㅋㅋㅋㅋ) 강남 토끼정 요리는 다 맛있지만 크림소스 요리를 참 잘하는 거 같네요 요건 물만 마시기 아쉬워 시킨 뉴자몽과 밀키소다 딸기통통! 유자와 자몽의 맛을 함께 느낄 수 있는 뉴자몽은 상큼함 그 자체였어요. 하치만 저는 딸기통통 밀키소다가 더 맛있었습니다ㅎㅎ 밀키소다는 토끼정에서만 만나볼 수 있는 메뉴라고 하니 한 번 드셔보시길 추천할게요!! 강남 토끼정은 강남역 맛집답게 모든 음식들이 대체적으로 맛있었어요! 건물 위치도 강남 대로변에서 조금 떨어져 있어 내부 인테리어처럼 아늑한 느낌도 있었구요ㅎㅎ 기회가 되면 다들 꼭 들러보세요~";
//        String data = "다.다.다.다.다.다.다.다.다.다.다.(다.다.다.다.다.다.다.다.다.)다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.다.(다.다.다.다.다.다.다.다.다.)다.다.다.다.다.다.다.다.";
        data += data;
        data += data;
        data += data;
//        data += data;

        List<SplitCondition> splitConditions;

        String[] rule = {"terminator"};
        splitConditions = SplitConditionManager.getSplitConditions(rule);
        ConditionTerminatorProcessor conditionTerminatorProcessor = new ConditionTerminatorProcessor(splitConditions);
        List<Area> exceptionAreas;
        ExceptionAreaProcessor exceptionAreaProcessor = new BracketAreaProcessor();
        exceptionAreas = exceptionAreaProcessor.find(data);

//
//        // increase rule
//        for (int i = 0; i < 100000; i++) {
//            conditionTerminatorProcessor.addSplitConditions(new SplitCondition.Builder("TAE", 'B' ).build());
//
//        }


        int ruleLoopTotal = 0;
        int textLoopTotal = 0;
        int arrayTotal = 0;
        List<Integer> ruleLoopResult = new ArrayList<>();
        List<Integer> textLoopResult = new ArrayList<>();
        int[] arrayResult = new int[0];


        int i = 0;
        for (; i < 1000; i++) {

            long start3 = System.nanoTime();
            arrayResult = conditionTerminatorProcessor.findByTextLoop(data, exceptionAreas);
            long end3 = System.nanoTime();
            arrayTotal += (end3 - start3);


        }
//            System.out.print("start : " + start1 + " end : " + end1);
        System.out.println("Rule loop -> 실행 시간 : " + (ruleLoopTotal / i) / 1000000.0 + "ms");

//            System.out.print("start : " + start2 + " end : " + end2);
        System.out.println("Text loop -> 실행 시간 : " + (textLoopTotal / i) / 1000000.0 + "ms");

//            System.out.print("start : " + start3 + " end : " + end3);
        System.out.println("Array -> 실행 시간 : " + (arrayTotal / i) / 1000000.0 + "ms");


        ruleLoopResult.forEach(point -> System.out.print(point + " "));
        System.out.println();

        textLoopResult.forEach(point -> System.out.print(point + " "));
        System.out.println();

        Arrays.stream(arrayResult).forEach(point -> System.out.print(point + " "));
        System.out.println();
    }

    public SplitCondition[] getSplitConditions() {
        return this.splitConditions;
    }
}
