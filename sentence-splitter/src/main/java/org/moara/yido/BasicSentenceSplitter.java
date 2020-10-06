package org.moara.yido;

import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.BasicRoleManager;

import java.util.TreeSet;

/**
 * 문장 분리기
 */
public class BasicSentenceSplitter implements SentenceSplitter {

    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    /**
     * Default constructor
     * only SentenceSplitterManager can use this
     */
    BasicSentenceSplitter() {
        initAreaProcessor();
    }

    private void initAreaProcessor() {
        BasicRoleManager basicRoleManager = BasicRoleManager.getRoleManager();

        this.terminatorAreaProcessor = new TerminatorAreaProcessor(basicRoleManager);
        this.exceptionAreaProcessor = new ExceptionAreaProcessor(basicRoleManager);
    }

    @Override
    public Sentence[] split(String inputData) {
        this.exceptionAreaProcessor.find(inputData);
        TreeSet<Integer> splitPoint = findSplitPoint(inputData);

        return doSplit(splitPoint, inputData);
    }

    private TreeSet<Integer> findSplitPoint(String inputData) {

        return this.terminatorAreaProcessor.find(inputData, this.exceptionAreaProcessor);
    }

    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String inputData) {
        int startIndex = 0;
        int endIndex = 0;
        int resultIndex = 0;
        Sentence[] result = new Sentence[splitPoint.size() + 1];

        for(int point : splitPoint) {
            endIndex = point;
            Sentence sentence = new Sentence(startIndex, endIndex, inputData.substring(startIndex, endIndex).trim());

            result[resultIndex++] = sentence;
            startIndex = endIndex;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }




}

