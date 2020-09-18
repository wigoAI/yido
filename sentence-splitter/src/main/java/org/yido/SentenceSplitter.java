package org.yido;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSplitter {
    private List<String> result;
    private int minimumSentenceLength;
    private List<Character> exceptionSignList;


    public List<String> sentenceSplit(String inputData) {
        List<Area> exceptionArea = checkExceptionArea(inputData);

        for(int dataIndex = 0 ; dataIndex < inputData.length() ; dataIndex++) {
            dataIndex = avoidExceptionArea(exceptionArea, dataIndex);

        }


        return this.result;
    }


    private List<Area> checkExceptionArea(String inputData) {
        List<Area> exceptionArea = new ArrayList<>();
        Pattern bracketPattern = Pattern.compile("[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]");
        Matcher matcher = bracketPattern.matcher(inputData);

        while(matcher.find()){
            exceptionArea.add(new Area(matcher.start(), matcher.end()));
        }

        return exceptionArea;
    }

    private int avoidExceptionArea(List<Area> exceptionArea, int dataIndex) {


        for(int listIndex = 0 ; listIndex < exceptionArea.size() ; listIndex++) {
            Area targetArea = exceptionArea.get(listIndex);


            /** 연속된 예외구간 처리할 것
             *  ex) (hello)(My name)
             */
            if(dataIndex == targetArea.getStartIndex()) {
                dataIndex = targetArea.getEndIndex();
                exceptionArea.remove(listIndex);
                break;
            }
        }


        return dataIndex;
    }


    public List<String> getResult() {
        return this.result;
    }

}

