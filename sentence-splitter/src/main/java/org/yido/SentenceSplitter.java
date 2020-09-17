package org.yido;

import scala.Char;

import java.util.ArrayList;
import java.util.List;

public class SentenceSplitter {
    private List<String> result;
    private int minimumSentenceLength;
    private List<Character> exceptionSignList;


    public List<String> sentenceSplit(String inputData) {
        List<Area> exceptionArea = checkExceptionArea(inputData);



        for(int i = 0 ; i < inputData.length() ; i++) {

        }


        return this.result;
    }


    private List<Area> checkExceptionArea(String inputData) {
        List<Area> exceptionArea = new ArrayList<>();
        int exceptionIndex = 1;

        while(exceptionIndex != -1) {
//            exceptionIndex = inputData.indexOf();
        }

        return exceptionArea;
    }


    private boolean isExceptionSign(char sign) {
        for(char exceptionSign : this.exceptionSignList) {

            // exceptionSign [] {} ()
            if (exceptionSign == sign)
                return true;
        }
        return false;
    }

    public List<String> getResult() {
        return this.result;
    }

}
