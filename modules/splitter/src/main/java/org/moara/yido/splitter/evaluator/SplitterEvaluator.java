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
package org.moara.yido.splitter.evaluator;


import org.moara.yido.classification.binary.BinaryClassificationEvaluation;
import org.moara.yido.splitter.utils.FileReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 구분기 성능 평과 클래스
 * @author wjrmffldrhrl
 */
public class SplitterEvaluator {
    private final List<String> answerSheet;
    private final List<Integer> answerSplitPoints;
    private final List<Integer> answerNonSplitPoints;
    private final String answerStr;
    private List<String> splitterSheet;

    /**
     * 정답 데이터 초기화
     *
     * @param answerFileName {@code SentenceExtractor}에 의해 추출된 문장 정보가 담긴 파일 명
     */
    public SplitterEvaluator(String answerFileName) {

        // 정답지 구분 결과 데이터
        this.answerSheet = getSheetByFile(answerFileName);
        this.answerSplitPoints = getSplitPoints(this.answerSheet);

        StringBuilder answerSheetString = new StringBuilder();
        this.answerSheet.stream().map(sheet -> sheet = sheet.replace(" ", ""))
                .forEach(answerSheetString::append);
        this.answerStr = answerSheetString.toString();

        this.answerNonSplitPoints = IntStream.range(1, answerStr.length())
                .filter(point -> !answerSplitPoints.contains(point)).boxed().collect(Collectors.toList());

    }

    /**
     * 구분기 데이터 초기화
     * @param splitterSheet 구분기 결과
     */
    public void initSplitterSheet(String[] splitterSheet) {
        initSplitterSheet(Arrays.asList(splitterSheet.clone()));
    }

    /**
     * 문장 구분점 분류 모델의 결과 초기화
     * @param fileName {@code SentenceExtractor}에 의해 추출된 문장 정보가 담긴 파일 명
     */
    public void initSplitterSheet(String fileName) {
        List<String> splitterSheet = getSheetByFile(fileName);
        initSplitterSheet(splitterSheet);
    }

    /**
     * 장 구분점 분류 모델의 결과 초기화
     * @param splitterSheet 추출된 문장 정보가 담긴 List
     */
    public void initSplitterSheet(List<String> splitterSheet) {
        if (!isValidSplitterSheet(splitterSheet)) {
            throw new RuntimeException("Invalid splitter sheet");
        }

        this.splitterSheet = splitterSheet;
    }

    private boolean isValidSplitterSheet(List<String> sheets) {
        StringBuilder sheetString = new StringBuilder();

        sheets.stream().map(sheet -> sheet = sheet.replace(" ", ""))
                .forEach(sheetString::append);

        return answerStr.equals(sheetString.toString());
    }

    /**
     * 문장 구분기의 성능 평가
     * @return {@code BinaryClassificationEvaluation}
     */
    public BinaryClassificationEvaluation answerCheck() {
        List<Integer> splitterSplitPoints = getSplitPoints(splitterSheet);
        List<Integer> splitterNonSplitPoints = IntStream.range(1, answerStr.length())
                .filter(point -> !splitterSplitPoints.contains(point)).boxed().collect(Collectors.toList());

        List<Integer> truePositivePoints = answerSplitPoints.stream()
                .filter(splitterSplitPoints::contains).collect(Collectors.toList());
        List<Integer> trueNegativePoints = answerNonSplitPoints.stream()
                .filter(splitterNonSplitPoints::contains).collect(Collectors.toList());
        List<Integer> falseNegativePoints = answerSplitPoints.stream()
                .filter(splitterNonSplitPoints::contains).collect(Collectors.toList());
        List<Integer> falsePositivePoints = answerNonSplitPoints.stream()
                .filter(splitterSplitPoints::contains).collect(Collectors.toList());

        int truePositive = truePositivePoints.size();
        int trueNegative = trueNegativePoints.size();
        int falseNegative = falseNegativePoints.size();
        int falsePositive = falsePositivePoints.size();

        return new BinaryClassificationEvaluation(truePositive, falsePositive, falseNegative, trueNegative);
    }

    private List<Integer> getSplitPoints(List<String> sheets) {

        List<Integer> sheetSplitPoints = new LinkedList<>();

        int previousSplitPoint = 0;
        for (int i = 0; i < sheets.size() - 1; i++) {
            previousSplitPoint += sheets.get(i).length();
            sheetSplitPoints.add(previousSplitPoint);
        }

        return sheetSplitPoints;
    }

    private List<String> getSheetByFile(String fileName) {
        List<String> sheet = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(FileReader.ABSTRACT_PATH + fileName + ".txt"), StandardCharsets.UTF_8))) {

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sheet.add(line.replace(" ", ""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public List<String> getAnswerSheet() {
        return answerSheet;
    }

    public List<String> getSplitterSheet() {
        return splitterSheet;
    }

    public static void main(String[] args) {

        SplitterEvaluator splitterEvaluator = new SplitterEvaluator("evaluation/answer3");
        splitterEvaluator.initSplitterSheet("evaluation/hannanum/submit3");
        BinaryClassificationEvaluation binaryClassificationEvaluation = splitterEvaluator.answerCheck();

        double accuracy = binaryClassificationEvaluation.getAccuracy();
        double geometricMean = binaryClassificationEvaluation.getGeometricMean();
        double sensitivity = binaryClassificationEvaluation.getSensitivity();
        double precision = binaryClassificationEvaluation.getPrecision();
        double f1Score = binaryClassificationEvaluation.getF1Score();

        System.out.println("Accuracy (정확도) \t\t\t: " + accuracy);
        System.out.println("sensitivity (재현율)\t\t\t: " + sensitivity );
        System.out.println("Precision (정밀도)\t\t\t: " + precision );
        System.out.println("F1Score (F1 지수)\t\t\t: " + f1Score );
        System.out.println("Geometric Mean (균형 정확도)\t: " + geometricMean );
    }
}
