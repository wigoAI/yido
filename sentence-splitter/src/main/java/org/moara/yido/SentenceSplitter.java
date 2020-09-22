package org.moara.yido;

import org.moara.yido.fileIO.FileReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 *
 * 문장 구분기 클래스
 *
 * TODO 1. Thread 적용
 *
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

//        FileReader terminatorFileReadr = new FileReader("/data/talkTerminator.txt");
//        FileReader terminatorFileReadr = new FileReader("/data/newTerminator.txt");
//        FileReader terminatorFileReadr = new FileReader("/data/koTerminator.txt");

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
        TreeSet<Integer> splitPoint = findSplitPoint(exceptionAreaList);
        this.result = doSplit(splitPoint);

        return this.result;
    }


    private List<Area> findExceptionArea() {
        List<Area> exceptionAreaList = new ArrayList<>();

        exceptionAreaList = findBracketPattern(exceptionAreaList);
        exceptionAreaList = findUrlPattern(exceptionAreaList);

        return exceptionAreaList;
    }

    private List<Area> findBracketPattern(List<Area> exceptionAreaList) {
        Pattern bracketPattern = Pattern.compile(this.BRACKET_PATTERN);
        Matcher bracketMatcher = bracketPattern.matcher(this.inputData);

        while(bracketMatcher.find()) { exceptionAreaList.add(new Area(bracketMatcher.start(), bracketMatcher.end())); }

        return exceptionAreaList;
    }

    private List<Area> findUrlPattern(List<Area> exceptionAreaList) {
        Pattern urlPatter = Pattern.compile(this.URL_PATTERN);
        Matcher urlMatcher = urlPatter.matcher(this.inputData);

        while(urlMatcher.find()) { exceptionAreaList.add(new Area(urlMatcher.start(), urlMatcher.end())); }

        return exceptionAreaList;
    }

    private TreeSet<Integer> findSplitPoint(List<Area> exceptionAreaList) {
        TreeSet<Integer> splitPoint = new TreeSet<>();

        /**
         * 1. 전체조회 -> 2 ~ 3 길이로 반복
         * 2. 2 ~ 3 길이로 반복 -> 전체조회
         */
//        for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {
//            for(int dataIndex = 0 ; dataIndex < this.inputDataLength - targetLength ; dataIndex++) {
        for(int dataIndex = 0 ; dataIndex < this.inputDataLength - 5 ; dataIndex++) {
            for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {
                Area targetArea = new Area(dataIndex, dataIndex + targetLength);
                targetArea = avoidExceptionArea(exceptionAreaList, targetArea);

                String targetString = this.inputData.substring(targetArea.getStartIndex(),
                        targetArea.getEndIndex());
                System.out.println("[" + targetString + "] " + targetArea.getStartIndex() + " , " + targetArea.getEndIndex());


                if(this.terminatorHash.contains(targetString) && !isConnective(targetArea.getEndIndex())) {
                    int additionalSignLength = getAdditionalSignLength(targetArea.getEndIndex());
                    int targetSplitPoint = targetArea.getEndIndex() + additionalSignLength;

                    System.out.println("-> " + targetString);

                    splitPoint.add(targetSplitPoint);

                    //  한 점을 예외영역으로 지정하는 것이 의미가 있는가?
//                    exceptionAreaList.add(new Area(targetSplitPoint, targetSplitPoint));
                    targetArea.moveStartIndex(targetArea.getStartIndex() + additionalSignLength);


                    dataIndex = targetArea.getStartIndex();
                    break;
                }

                dataIndex = targetArea.getStartIndex();
            }
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
        int connectiveCheckLength = (startIndex + 5 > this.inputDataLength) ? (startIndex + 5 - this.inputDataLength) : 5;
        String nextStr = this.inputData.substring(startIndex, startIndex +  connectiveCheckLength);

        for(int i = 0 ; i < nextStr.length() ; i++) {
            String targetString = nextStr.substring(0, nextStr.length() - i);

            if(this.connectiveHash.contains(targetString)) {
                System.out.println("connective! : " + targetString);
                return true;
            }
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

    private List<String> doSplit(TreeSet<Integer> splitPoint) {
        int startIndex = 0;

        List<String> result = new ArrayList<>();

        for(int point : splitPoint) {
            int endIndex = point;
            String sentence = this.inputData.substring(startIndex, endIndex).trim();

            result.add(sentence);
            startIndex = endIndex;

        }

        result.add(this.inputData.substring(startIndex, this.inputDataLength));

        return result;
    }


    public List<String> getResult() { return this.result; }

}

