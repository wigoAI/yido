package org.yido.answerChecker;

import org.yido.fileIO.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * 정답체크클래스
 * 줄바꿈으로 나눠진 정답 파일로 초기화한다.
 * 또는 리스트 형태의 정답 데이터로 초기화
 * 생성자로 접근하지 않고 인스턴스를 반환하는 메서드로 접근
 *
 * */
public class AnswerChecker {
    private List<String> answerList = new ArrayList<>();
    private int sizeOfAnswerList;

    private AnswerChecker(String answerFilePath) {
        FileReader fileReader = new FileReader(answerFilePath);
        this.answerList = fileReader.getSplitFileByLine();
        this.sizeOfAnswerList = answerList.size();
    }
    private AnswerChecker(List<String> answerList) {
        this.answerList = answerList;
        this.sizeOfAnswerList = answerList.size();
    }

    public static AnswerChecker setAnswerCheckerByAnswerFile(String answerFilePath) {
        return new AnswerChecker(answerFilePath);
    }

    public static AnswerChecker setAnswerCheckerByAnswerList(List<String> answerList) {
        return new AnswerChecker(answerList);
    }

    public float checkAnswer(List<String> submittedSheet) {

        int sheetIndex = 0;
        int answerIndex = 0;
        float unCorrectCnt = 0;
        float correctCnt = 0;

        // 제출지 임시 저장소
        // 구분점을 지났을 때 사용
        String tmpSheet = "";
        String tmpAnswer = "";

        while(answerIndex < sizeOfAnswerList) {

            String answer = this.answerList.get(answerIndex).replace(" ", "");
            String sheet = submittedSheet.get(sheetIndex).replace(" ", "");

            String targetSheet = "";
            String targetAnswer = "";

            if(tmpAnswer.length() == 0){
                targetAnswer = answer;
                System.out.println("Answer : " + targetAnswer);
            } else{
                targetAnswer = tmpAnswer;
//                System.out.print("tmp ");
                System.out.println("Answer : ");
            }


            if(tmpSheet.length() == 0){
                targetSheet = sheet;
                System.out.println("Sheet  : " + targetSheet);
            } else{
                targetSheet = tmpSheet;
//                System.out.print("tmp ");
                System.out.println("Sheet  : ");
            }

            System.out.println();

            boolean isSheetContainsAnswer = targetSheet.contains(targetAnswer);
            boolean isAnswerContainsSheet = targetAnswer.contains(targetSheet);

            // 정답
            if( targetAnswer.equals(targetSheet)) {
//                System.out.println("Correct!" );

                // 온전한 문장으로 나눴을 때 정답으로 인정한다.
                // 임시 정답지를 사용하지 않았다면 온전한 정답을 맞춘 것이다.
                // 임시 정답지는 올바르지 않은 문장 구분자를 사용하여 답보다 짧게 나누었을 때
                // 사용하기 때문이다.
                if(tmpAnswer.length() == 0) {
                    correctCnt++;
//                    System.out.println("and correct count!");
                }

            // 구분점을 지나쳤을 떄
            } else if(targetAnswer.length() < targetSheet.length() && isSheetContainsAnswer){
//                System.out.println("pass splitter");


                unCorrectCnt++;

                // 구분점으로 임의로 자르기
                tmpSheet = targetSheet.substring(
                        targetSheet.indexOf(targetAnswer) + targetAnswer.length()).replace(" ", "");


            // 잘못된 문장 구분점을 적용했을 때
            } else if(targetAnswer.length() > targetSheet.length() && isAnswerContainsSheet){
//                System.out.println("unCorrect splitter");
                // 답안 문장은 잘문 구분된 문장을 포함하고 있을것이다.
                // 혹시 모르니 체크

                unCorrectCnt++;

                // 구분점을 임의로 자른다.
                tmpAnswer = targetAnswer.substring(
                        targetAnswer.indexOf(targetSheet) + targetSheet.length()).replace(" ", "");



            // 길이만 같고 답이 아닐 때 혹은 길이가 긴 쪽이 짧은쪽을 포함 안할 때
            } else {
                /**
                 * 예외사항 생각할 것
                 *
                 * */
                System.out.println("something is wrong");
                break;

            }

            if(isAnswerContainsSheet){
                tmpSheet = "";
                // 다음 문장 구분으로 이동
                sheetIndex++;
            }
            if(isSheetContainsAnswer) {
                tmpAnswer = "";
                // 다음 정답지로 이동
                answerIndex++;
            }




        }

        System.out.println("Answer List Size : " + this.sizeOfAnswerList);
        System.out.println("Wrong split count : " + unCorrectCnt);
        System.out.println("Correct split count : " + correctCnt);

        // 정확도 0.0 ~ 1.0
        return correctCnt / this.sizeOfAnswerList;
    }


    public List<String> getAnswerList() {
        return this.answerList;
    }

    public int getSizeOfAnswerList() {
        return this.sizeOfAnswerList;
    }


}
