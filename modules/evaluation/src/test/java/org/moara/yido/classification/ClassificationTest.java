//package org.moara.yido.classification;
//
//
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.moara.yido.classification.binary.BinaryClassificationEvaluation;
//import org.moara.yido.classification.multinomial.MultinomialClassificationEvaluation;
//
//public class ClassificationTest {
//
//
//    int[][] multiClassData = {
//            {9, 1, 0, 0},
//            {1, 15, 3, 1},
//            {5, 0, 24, 1},
//            {0, 4, 1, 15}
//    };
//
//    int[][] binaryData = {
//            {9, 1},
//            {1, 9}
//    };
//
//    @Test
//    public void testBinaryClassification() {
//        BinaryClassificationEvaluation evaluation1 = new BinaryClassificationEvaluation(binaryData);
//
//        BinaryClassificationEvaluation evaluation2 = new BinaryClassificationEvaluation(binaryData[0][0], binaryData[0][1], binaryData[1][0], binaryData[1][1]);
//
//
//        Assertions.assertEquals(evaluation1.getAccuracy(), evaluation2.getAccuracy(), 0.001);
//        Assertions.assertEquals(evaluation1.getErrorRate(), evaluation2.getErrorRate(), 0.001);
//        Assertions.assertEquals(evaluation1.getPrecision(), evaluation2.getPrecision(), 0.001);
//        Assertions.assertEquals(evaluation1.getSensitivity(), evaluation2.getSensitivity(), 0.001);
//        Assertions.assertEquals(evaluation1.getSpecificity(), evaluation2.getSpecificity(), 0.001);
//        Assertions.assertEquals(evaluation1.getF1Score(), evaluation2.getF1Score(), 0.001);
//        Assertions.assertEquals(evaluation1.getGeometricMean(), evaluation2.getGeometricMean(), 0.001);
//
//    }
//
//    @Test
//    public void testMultinomialClassificationEvaluation() {
//        BinaryClassificationEvaluation binary = new BinaryClassificationEvaluation(binaryData);
//
//        MultinomialClassificationEvaluation multinomial = new MultinomialClassificationEvaluation(binaryData);
//
//        System.out.println("Binary org.moara.yido.classification");
//        System.out.println("Accuracy " + binary.getAccuracy());
//        System.out.println("Precision " + binary.getPrecision());
//        System.out.println("Specificity " + binary.getSpecificity());
//        System.out.println("Sensitivity " + binary.getSensitivity());
//        System.out.println("F1 score " + binary.getF1Score());
//        System.out.println();
//        System.out.println("Multinomial org.moara.yido.classification");
//        System.out.println("Accuracy " + multinomial.getAccuracy());
//        System.out.println("Precision " + multinomial.getPrecision());
//        System.out.println("Specificity " + multinomial.getSpecificity());
//        System.out.println("Sensitivity " + multinomial.getSensitivity());
//        System.out.println("F1 score " + multinomial.getF1Score());
//
//        Assertions.assertEquals(binary.getAccuracy(), multinomial.getAccuracy(), 0.001);
//        Assertions.assertEquals(binary.getErrorRate(), multinomial.getErrorRate(), 0.001);
//
//        Assertions.assertEquals(binary.getPrecision(), multinomial.getPrecision(), 0.001);
//        Assertions.assertEquals(binary.getSensitivity(), multinomial.getSensitivity(), 0.001);
//        Assertions.assertEquals(binary.getSpecificity(), multinomial.getSpecificity(), 0.001);
//
//        Assertions.assertEquals(binary.getF1Score(), multinomial.getF1Score(), 0.001);
//        Assertions.assertEquals(binary.getGeometricMean(), multinomial.getGeometricMean(), 0.001);
//
//
//    }
//}
