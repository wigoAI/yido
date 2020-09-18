package org.yido;

import org.yido.fileIO.FileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSplitter {
    private final String URL_PATTERN = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    private final String BRACKET_PATTERN = "[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]";
    private List<String> result = new ArrayList<>();
    private int minimumSentenceLength;
    private List<Character> exceptionSignList;
    private List<String> roleData;



    public SentenceSplitter(int minimumSentenceLength) {
        FileReader fileReader = new FileReader("/data/role.txt");

        this.roleData = fileReader.getSplitFileByLine();
        this.minimumSentenceLength = minimumSentenceLength;
    }

    public List<String> sentenceSplit(String inputData) {
        List<Area> exceptionArea = checkExceptionArea(inputData);

        for(int dataIndex = 0 ; dataIndex < inputData.length() ; dataIndex++) {
            int targetIndex = avoidExceptionArea(exceptionArea, dataIndex);





            dataIndex = targetIndex;
        }

        return this.result;
    }


    private List<Area> checkExceptionArea(String inputData) {
        List<Area> exceptionArea = new ArrayList<>();
        Pattern bracketPattern = Pattern.compile(this.URL_PATTERN);
        Pattern urlPatter = Pattern.compile(this.BRACKET_PATTERN);
        Matcher bracketMatcher = bracketPattern.matcher(inputData);
        Matcher urlMatcher = urlPatter.matcher(inputData);

        while(bracketMatcher.find()) {
            exceptionArea.add(new Area(bracketMatcher.start(), bracketMatcher.end()));
        }
        while(urlMatcher.find()) {
            exceptionArea.add(new Area(urlMatcher.start(), urlMatcher.end()));
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

