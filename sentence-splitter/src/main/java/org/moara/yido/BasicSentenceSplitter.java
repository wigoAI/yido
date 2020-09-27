package org.moara.yido;

import org.moara.yido.area.Area;
import org.moara.yido.role.RoleManagerTemp;

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
 *
 */
public class BasicSentenceSplitter implements SentenceSplitter {
    private final String URL_PATTERN = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    private final String BRACKET_PATTERN = "[\\(\\{\\[][^\\)\\]\\}]*[^\\(\\[\\{]*[\\)\\]\\}]";
    private List<String> result = new ArrayList<>();
    private int minimumSentenceLength;

    private HashSet<String> connectiveHash;
    private HashSet<String> terminatorHash;
    private String inputData;
    private int inputDataLength;

    BasicSentenceSplitter(int minimumSentenceLength, String inputData) {
        RoleManagerTemp roleManagerTemp = RoleManagerTemp.getRoleManager();

        this.connectiveHash = roleManagerTemp.getConnective();
        this.terminatorHash = roleManagerTemp.getTerminator();
        this.inputData = inputData;
        this.inputDataLength = inputData.length();
        this.minimumSentenceLength = minimumSentenceLength;

    }

    public List<String> split() {
        List<Area> exceptionAreaList = findExceptionArea();
        TreeSet<Integer> splitPoint = findSplitPoint(exceptionAreaList);
        this.result = doSplit(splitPoint);

        return this.result;
    }
    @Override
    public Sentence[] split(String text) {
        return new Sentence[0];
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

//        for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {
//            for(int dataIndex = 0 ; dataIndex < this.inputDataLength - minimumSentenceLength ; dataIndex++) {
        for(int dataIndex = 0 ; dataIndex < this.inputDataLength - minimumSentenceLength ; dataIndex++) {
            for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {

                Area targetArea = avoidExceptionArea(exceptionAreaList, new Area(dataIndex, dataIndex + targetLength));

                String targetString = this.inputData.substring(targetArea.getStart(),
                        targetArea.getEnd());
//                System.out.println("[" + targetString + "] " + targetArea.getStartIndex() + " , " + targetArea.getEndIndex());



                if(this.terminatorHash.contains(targetString) && !isConnective(targetArea.getEnd())) {
                    int additionalSignLength = getAdditionalSignLength(targetArea.getEnd());
                    int targetSplitPoint = targetArea.getEnd() + additionalSignLength;
//                    System.out.println("-> " + targetString);

                    splitPoint.add(targetSplitPoint);

                    //  한 점을 예외영역으로 지정하는 것이 의미가 있는가?
//                    exceptionAreaList.add(new Area(targetSplitPoint, targetSplitPoint));
                    targetArea.moveStart(targetArea.getStart() + additionalSignLength);
                    dataIndex = targetArea.getStart();
                    break;
                }

                dataIndex = targetArea.getStart();
            }
        }


        return splitPoint;
    }

    private Area avoidExceptionArea(List<Area> exceptionAreaList, Area targetArea) {

        for(int i = 0 ; i < exceptionAreaList.size() ; i++) {

            Area exceptionArea = exceptionAreaList.get(i);


            if(targetArea.isOverlap(exceptionArea)) {
                targetArea.moveStart(exceptionArea.getEnd());

                // 이동시킨 위치가 예외 영역에 포함되지 않는지 다시 체크
                i = -1;
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
//                System.out.println("connective! : " + targetString);
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

