package org.moara.classification.multinomial;

import org.moara.classification.binary.BinaryClassificationEvaluation;

/**
 * 다항 분류 평가
 */
public class MultinomialClassificationEvaluation {

    private final BinaryClassificationEvaluation[] binaryClassificationEvaluations;


    private final double[] accuracies;
    private final double[] errorRates;

    private final double[] precisions;
    private final double[] specificities;
    private final double[] sensitivities;

    private final double[] f1Scores;
    private final double[] geometricMeans;

    private final double accuracy; // 정확도, 제대로 분류된 데이터의 비율 (TP + TN) / (P + N)
    private final double errorRate; // 오류율, 잘못 분류한 데이터의 비율 (FN + FP) / (P + N) , 1 - Accuracy

    private final double precision; // 정밀도, 예측한 정답 중 실제 정답인 것 TP / (TP + FP) 모델 관점
    private final double specificity; //  it measures how much a classifier can recognize negative examples  TN / (TN + FP)
    private final double sensitivity; // 재현율, 실제 정답 중 예측에 성공한 것 TP / (TP + FN) 데이터 관점, 참인 정답이 적을 때 유효

    private final double f1Score; // precision과 recall의 조화평균, 2 / (1 / precision) + (1 / recall) 0에서 1사이의 값을 가지며 높을수록 좋음, recall과 precision의 조화 평균
    private final double geometricMean; // 균형 정확도, 참에 대한 정확도와, 거짓에 대한 정확도를 따로 분류해 기하 평균을 구함 sqrt((TP / (TP + FN)) * (TN / (FP + TN)))

    /**
     * 분류 모델의 오차행렬을 통해 평가 지표를 측정한다.
     *
     * @param confusionMatrix 오차행렬
     */
    public MultinomialClassificationEvaluation(int[][] confusionMatrix) {

        if (confusionMatrix.length < 2 ||
                confusionMatrix.length != confusionMatrix[0].length) {
            throw new RuntimeException("Invalid confusion matrix");
        }

        int itemNum = confusionMatrix.length;



        this.binaryClassificationEvaluations = new BinaryClassificationEvaluation[itemNum];
        double[] truePositive = new double[itemNum]; // 참을 참이라고 한 횟수
        double[] trueNegative = new double[itemNum]; // 거짓을 거짓이라고 한 횟수
        double[] falseNegative = new double[itemNum]; // 참을 거짓이라고 한 횟수
        double[] falsePositive = new double[itemNum]; // 거짓을 참이라고 한 횟수

        // 실제 값에 대한 모델의 분류를 순서대로
        for (int i = 0; i < itemNum; i++) {
            for (int j = 0; j < itemNum; j++) {
                int value = confusionMatrix[i][j];

                if (i == j) {
                    truePositive[j] = value;
                }

                for (int k = 0; k < itemNum; k++) {
                    if (k != i && k != j) {
                        trueNegative[k] += value;
                    }
                    if (j == k && i != k) {
                        falsePositive[k] += value;
                    }

                    if (i == k && j != k) {
                        falseNegative[k] += value;
                    }
                }
            }
        }


        this.accuracies = new double[itemNum];
        this.errorRates = new double[itemNum];
        this.precisions = new double[itemNum];
        this.specificities = new double[itemNum];
        this.sensitivities = new double[itemNum];
        this.f1Scores = new double[itemNum];
        this.geometricMeans = new double[itemNum];

        double accuracy = 0;
        double errorRate = 0;
        double precision = 0;
        double specificity = 0;
        double sensitivity = 0;
        double f1Score = 0;
        double geometricMean = 0;

        for (int i = 0; i < itemNum; i++) {
            this.binaryClassificationEvaluations[i] = new BinaryClassificationEvaluation(
                    truePositive[i], falsePositive[i],
                    falseNegative[i], trueNegative[i]
            );

            this.accuracies[i] = this.binaryClassificationEvaluations[i].getAccuracy();
            accuracy += this.accuracies[i];
            this.errorRates[i] = this.binaryClassificationEvaluations[i].getErrorRate();
            errorRate += this.errorRates[i];
            this.precisions[i] = this.binaryClassificationEvaluations[i].getPrecision();
            precision += this.precisions[i];
            this.sensitivities[i] = this.binaryClassificationEvaluations[i].getSensitivity();
            sensitivity += this.sensitivities[i];
            this.specificities[i] = this.binaryClassificationEvaluations[i].getSpecificity();
            specificity += this.specificities[i];
            this.f1Scores[i] = this.binaryClassificationEvaluations[i].getF1Score();
            f1Score += this.f1Scores[i];
            this.geometricMeans[i] = this.binaryClassificationEvaluations[i].getGeometricMean();
            geometricMean += this.geometricMeans[i];
        }

        this.accuracy = accuracy / ((double) itemNum);
        this.errorRate = errorRate / ((double) itemNum);
        this.precision = precision / ((double) itemNum);
        this.specificity = specificity / ((double) itemNum);
        this.sensitivity = sensitivity / ((double) itemNum);
        this.f1Score = f1Score / ((double) itemNum);
        this.geometricMean = geometricMean / ((double) itemNum);
    }

    public BinaryClassificationEvaluation[] getBinaryClassificationEvaluations() {
        return binaryClassificationEvaluations;
    }

    public double getAccuracy() {
        return accuracy;
    }
    public double getErrorRate() {
        return errorRate;
    }
    public double getPrecision() {
        return precision;
    }
    public double getSpecificity() {
        return specificity;
    }
    public double getSensitivity() {
        return sensitivity;
    }
    public double getF1Score() {
        return f1Score;
    }
    public double getGeometricMean() {
        return geometricMean;
    }

    public double[] getAccuracies() {
        return accuracies;
    }
    public double[] getErrorRates() {
        return errorRates;
    }
    public double[] getPrecisions() {
        return precisions;
    }
    public double[] getSpecificities() {
        return specificities;
    }
    public double[] getSensitivities() {
        return sensitivities;
    }
    public double[] getF1Scores() {
        return f1Scores;
    }
    public double[] getGeometricMeans() {
        return geometricMeans;
    }


}
