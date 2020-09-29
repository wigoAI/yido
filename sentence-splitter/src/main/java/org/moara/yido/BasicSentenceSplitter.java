package org.moara.yido;

import org.moara.yido.area.Area;
import org.moara.yido.area.processor.AreaProcessor;
import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.RoleManagerTemp;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Pattern;




/**
 *
 * 문장 구분기 클래스
 *
 *
 */
public class BasicSentenceSplitter implements SentenceSplitter {

    AreaProcessor terminatorAreaProcessor;
    AreaProcessor exceptionAreaProcessor;

    private int minimumSentenceLength;

    private HashSet<String> connectiveHash;
    private HashSet<String> terminatorHash;

    private String inputData;
    private int inputDataLength;

    /**
     * Default constructor
     * only SentenceSplitterManager can use this
     *
     * @param minimumSentenceLength
     */
    BasicSentenceSplitter(int minimumSentenceLength) {
        initAreaProcessor();

        RoleManagerTemp roleManagerTemp = RoleManagerTemp.getRoleManager();

        this.connectiveHash = roleManagerTemp.getConnective();
        this.terminatorHash = roleManagerTemp.getTerminator();
        this.minimumSentenceLength = minimumSentenceLength;

    }

    private void initAreaProcessor() {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor();
        this.exceptionAreaProcessor = new ExceptionAreaProcessor();
    }

    @Override
    public Sentence[] split(String inputData) {
        this.inputData = inputData;
        this.inputDataLength = inputData.length();
        exceptionAreaProcessor.find(inputData);
        TreeSet<Integer> splitPoint = findSplitPoint();

        return doSplit(splitPoint);
    }



    private TreeSet<Integer> findSplitPoint() {
        TreeSet<Integer> splitPoint = new TreeSet<>();

        for(int dataIndex = 0 ; dataIndex < this.inputDataLength - minimumSentenceLength ; dataIndex++) {
            for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {

                Area targetArea = exceptionAreaProcessor.avoid(new Area(dataIndex, dataIndex + targetLength));
                String targetString = this.inputData.substring(targetArea.getStart(), targetArea.getEnd());
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

    private Sentence[] doSplit(TreeSet<Integer> splitPoint) {
        int startIndex = 0;
        int endIndex = 0;
        int resultIndex = 0;
        Sentence[] result = new Sentence[splitPoint.size() + 1];

        for(int point : splitPoint) {
            endIndex = point;
            Sentence sentence = new Sentence(startIndex, endIndex, this.inputData.substring(startIndex, endIndex).trim());

            result[resultIndex++] = sentence;
            startIndex = endIndex;

        }

        result[resultIndex++] =  new Sentence(startIndex, this.inputDataLength, this.inputData.substring(startIndex, this.inputDataLength).trim());

        return result;
    }




}

