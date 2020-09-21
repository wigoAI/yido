package org.moara.yido;

import org.moara.yido.fileIO.FileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 *
 * 문장 구분기 클래스
 *
 * TODO 1. 단어 길이별로 여러번 반복 5 ~> 2
 *      2. 유효성 검사
 *      3. URL pattern check
 */
public class SentenceSplitter {
    private final String URL_PATTERN = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    private final String BRACKET_PATTERN = "[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]";
    private List<String> result = new ArrayList<>();
    private int minimumSentenceLength;
    private List<Character> exceptionSignList;
    private HashSet<String> connectiveHash;
    private HashSet<String> terminatorHash;
    private String inputData;
    private int inputDataLength;


    public SentenceSplitter(int minimumSentenceLength) {
        FileReader connectiveFileReader = new FileReader("/data/connective.txt");
        FileReader terminatorFileReadr = new FileReader("/data/terminator.txt");

        this.connectiveHash = new HashSet<>();
        this.terminatorHash = new HashSet<>();
        this.minimumSentenceLength = minimumSentenceLength;

        connectiveHash.addAll(connectiveFileReader.getSplitFileByLine());
        terminatorHash.addAll(terminatorFileReadr.getSplitFileByLine());


    }

    public void setData(String inputData) {
        this.inputData = inputData;
        this.inputDataLength = inputData.length();
    }

    public List<String> sentenceSplit() {
        List<Area> exceptionAreaList = findExceptionArea();
        List<Integer> splitPoint = findSplitPoint(exceptionAreaList);
        this.result = doSplit(splitPoint);

        return this.result;
    }


    private List<Area> findExceptionArea() {
        List<Area> exceptionAreaList = new ArrayList<>();
        Pattern bracketPattern = Pattern.compile(this.URL_PATTERN);
        Pattern urlPatter = Pattern.compile(this.BRACKET_PATTERN);
        Matcher bracketMatcher = bracketPattern.matcher(this.inputData);
        Matcher urlMatcher = urlPatter.matcher(this.inputData);

        while(bracketMatcher.find()) {
            exceptionAreaList.add(new Area(bracketMatcher.start(), bracketMatcher.end()));
        }
        while(urlMatcher.find()) {
            exceptionAreaList.add(new Area(urlMatcher.start(), urlMatcher.end()));
        }

        return exceptionAreaList;
    }

    private List<Integer> findSplitPoint(List<Area> exceptionAreaList) {
        List<Integer> splitPoint = new ArrayList<>();
        int targetLength = 2;

        for(int dataIndex = 0 ; dataIndex < this.inputDataLength - targetLength ; dataIndex++) {
            Area targetArea = new Area(dataIndex, dataIndex + targetLength);
            targetArea = avoidExceptionArea(exceptionAreaList, targetArea);

            String targetString = this.inputData.substring(targetArea.getStartIndex(),
                    targetArea.getEndIndex());



            if(this.terminatorHash.contains(targetString)
                    && !isConnective(targetArea.getEndIndex())) {
                int additionalSignLength = getAdditionalSignLength(targetArea.getEndIndex());
                splitPoint.add(targetArea.getEndIndex() + additionalSignLength);
//                splitPoint.add(targetArea.getEndIndex());
             }

            dataIndex = targetArea.getStartIndex();
        }



        return splitPoint;
    }

    private Area avoidExceptionArea(List<Area> exceptionAreaList, Area targetArea) {

        for(int i = 0 ; i < exceptionAreaList.size() ; i++) {

            Area exceptionArea = exceptionAreaList.get(i);

            /** 연속된 예외구간 처리할 것
             *  ex) (hello)(My name)
             */
            if(targetArea.isOverlap(exceptionArea)) {
                targetArea.moveStartIndex(exceptionArea.getEndIndex());
                break;
            }

        }

        return targetArea;
    }

    private boolean isConnective(int startIndex) {


        int connectiveCheckLength = 5;

        if(startIndex + connectiveCheckLength > this.inputDataLength) {
            connectiveCheckLength -= (startIndex + connectiveCheckLength - this.inputDataLength);
        }

        String nextStr = this.inputData.substring(startIndex, startIndex +  connectiveCheckLength);

        for(int i = 0 ; i < nextStr.length() ; i++) {
            String targetString = nextStr.substring(0, nextStr.length() - i);
            if(this.connectiveHash.contains(targetString)) { return true; }
        }

        return false;

    }
    private int getAdditionalSignLength(int startIndex) {
        int additionalSignLength = 0;
        String regular = "[ㄱ-ㅎㅏ-ㅣ\\.\\?\\!\\~\\;\\^]";
        Pattern pattern = Pattern.compile(regular);

        for(int i = 0 ; i + startIndex < this.inputDataLength ; i++ ) {
            String targetStr = this.inputData.substring(startIndex + i, startIndex + i + 1);

            if(!pattern.matcher(targetStr).matches()) {
                break;
            } else {
                additionalSignLength++;
            }
        }

        return additionalSignLength;
    }

    private List<String> doSplit(List<Integer> splitPoint) {
        int startIndex = 0;

        List<String> result = new ArrayList<>();

        for(int point : splitPoint) {
            int endIndex = point;
            String sentence = this.inputData.substring(startIndex, endIndex).trim();

            if(!(sentence.length() == 0)) {
                result.add(sentence);
            }

            startIndex = endIndex;

        }

        result.add(this.inputData.substring(startIndex, this.inputDataLength));

        return result;
    }


    public List<String> getResult() { return this.result; }

}

