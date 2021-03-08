package org.moara.yido.splitter.evaluation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moara.yido.classification.binary.BinaryClassificationEvaluation;
import org.moara.yido.splitter.TestFileInitializer;
import org.moara.yido.splitter.evaluator.SplitterEvaluator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitterEvaluatorTest {
    String filePath = "./dic/splitter/evaluation/test_answer.txt";

    String[] answerSheet = {"apple.", "orange.", "banana.", "melon."};
    String[] splitterSheetWrongCount3 = {"apple.", "orange.b", "anana.", "melo", "n."};
    String[] splitterSheetWrongCount4 = {"apple.orange.b", "anana.", "melo", "n."};
    String[] splitterSheetWrongCount5 = {"a", "p", "p", "l", "e", ".", "o", "r", "a", "n", "g", "e", ".", "b", "a", "n", "a", "n", "a", ".", "m", "e", "l", "o", "n", "."};
    String[] splitterSheetWrongCount6 = {"apple.orange.banana.melon."};
    String[] notSameStringWithAnswerSheet = {"apple.", "ornage.", "banana.", "melon."};
    String[] sameStringWithAnswerSheet = {"app  le.", "orange.    ", "ban", "a na.", "me  lon."};

    @BeforeEach
    public void initializeTest() {
        TestFileInitializer.initialize();
    }

    @AfterEach
    public void tearDownTest() {
        TestFileInitializer.tearDown();
    }

    @Test
    public void testAnswerCheckerInitWithFileName() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            for (String str : answerSheet) {
                bw.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SplitterEvaluator splitterEvaluator = new SplitterEvaluator("evaluation/test_answer");

        int index = 0;
        for (String answer : answerSheet) {
            assertEquals(answer, splitterEvaluator.getAnswerSheet().get(index++));

        }
    }

    @Test
    public void testInputSplitterSheet() {
        SplitterEvaluator splitterEvaluator = new SplitterEvaluator("evaluation/test_answer");
        splitterEvaluator.initSplitterSheet(splitterSheetWrongCount3);

        int index = 0;
        for (String sheet : splitterSheetWrongCount3) {
            assertEquals(sheet, splitterEvaluator.getSplitterSheet().get(index++));
        }

        splitterEvaluator.initSplitterSheet("evaluation/test_answer");

        index = 0;
        for (String sheet : answerSheet) {
            assertEquals(sheet, splitterEvaluator.getSplitterSheet().get(index++));
        }

    }

    @Test
    public void testAnswerCheck() {
        String[] answer = {"BinaryClassificationEvaluation : accuracy=0.88, errorRate=0.12, precision=0.5, sensitivity=0.6666666666666666, specificity=0.9090909090909091, f1Score=0.5714285714285714, geometricMean=0.7784989441615229",
                "BinaryClassificationEvaluation : accuracy=0.84, errorRate=0.16, precision=0.3333333333333333, sensitivity=0.3333333333333333, specificity=0.9090909090909091, f1Score=0.3333333333333333, geometricMean=0.5504818825631803",
                "BinaryClassificationEvaluation : accuracy=0.12, errorRate=0.88, precision=0.12, sensitivity=1.0, specificity=0.0, f1Score=0.21428571428571427, geometricMean=0.0",
                "BinaryClassificationEvaluation : accuracy=0.88, errorRate=0.12, precision=NaN, sensitivity=0.0, specificity=1.0, f1Score=NaN, geometricMean=0.0"};

        SplitterEvaluator splitterEvaluator = new SplitterEvaluator("evaluation/test_answer");
        splitterEvaluator.initSplitterSheet(splitterSheetWrongCount3);

        BinaryClassificationEvaluation binaryClassificationEvaluation = splitterEvaluator.answerCheck();

        assertEquals(answer[0], binaryClassificationEvaluation.toString());



        splitterEvaluator.initSplitterSheet(splitterSheetWrongCount4);

        binaryClassificationEvaluation = splitterEvaluator.answerCheck();

        assertEquals(answer[1], binaryClassificationEvaluation.toString());

        splitterEvaluator.initSplitterSheet(splitterSheetWrongCount5);

        binaryClassificationEvaluation = splitterEvaluator.answerCheck();

        assertEquals(answer[2], binaryClassificationEvaluation.toString());


        splitterEvaluator.initSplitterSheet(splitterSheetWrongCount6);
        binaryClassificationEvaluation = splitterEvaluator.answerCheck();

        assertEquals(answer[3], binaryClassificationEvaluation.toString());


    }

    @Test
    public void testInvalidSplitterSheet() {
        boolean sameStringFlag = true;
        boolean notSameStringFlag = false;
        SplitterEvaluator splitterEvaluator = new SplitterEvaluator("/evaluation/test_answer");

        try {
            splitterEvaluator.initSplitterSheet(sameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            sameStringFlag = false;
        }

        try {
            splitterEvaluator.initSplitterSheet(notSameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            notSameStringFlag = true;
        }

        assertTrue(sameStringFlag);
        assertTrue(notSameStringFlag);
    }
}