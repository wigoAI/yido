package org.yido;

import org.yido.fileIO.FileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSplitter {
    private final String URL_PATTERN = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    private final String BRACKET_PATTERN = "[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]";
    private List<String> result = new ArrayList<>();
    private int minimumSentenceLength;
    private List<Character> exceptionSignList;
    private HashSet<String> connectiveHash;
    private HashSet<String> terminatorHash;


    public SentenceSplitter(int minimumSentenceLength) {
        FileReader connectiveFileReader = new FileReader("/data/connective.txt");
        FileReader terminatorFileReadr = new FileReader("/data/terminator.txt");

        this.connectiveHash = new HashSet<>();
        this.terminatorHash = new HashSet<>();
        this.minimumSentenceLength = minimumSentenceLength;

        for(String str : connectiveFileReader.getSplitFileByLine()) {
            connectiveHash.add(str);
//            System.out.println(str);
        }
        for(String str : terminatorFileReadr.getSplitFileByLine()) {
            terminatorHash.add(str);
//            System.out.println(str);
        }


    }

    public List<String> sentenceSplit(String inputData) {
        List<Area> exceptionArea = checkExceptionArea(inputData);
        List<Integer> splitPoint = new ArrayList<>();
        int targetLength = 2;

        for(int dataIndex = 0 ; dataIndex < inputData.length() - targetLength ; dataIndex++) {
            int targetIndex = avoidExceptionArea(exceptionArea, dataIndex);
            String targetString = inputData.substring(targetIndex, targetIndex + targetLength);

            if(this.terminatorHash.contains(targetString)) {
//                System.out.println(targetString);
                splitPoint.add(targetIndex + targetLength);
            }


            dataIndex = targetIndex;
        }

        this.result = doSplit(inputData, splitPoint);

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

    private List<String> doSplit(String inputData, List<Integer> splitPoint) {
        int startIndex = 0;

        List<String> result = new ArrayList<>();

        for(int point : splitPoint) {
            int endIndex = point;
            result.add(inputData.substring(startIndex, endIndex));

            startIndex = endIndex;

        }

        result.add(inputData.substring(startIndex, inputData.length()));

        return result;
    }


    public List<String> getResult() { return this.result; }

}

