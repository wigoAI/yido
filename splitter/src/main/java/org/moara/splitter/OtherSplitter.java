package org.moara.splitter;

import org.moara.splitter.processor.OtherTerminatorAreaProcessor;
import org.moara.splitter.utils.Sentence;

import java.util.TreeSet;

public class OtherSplitter implements Splitter {
    protected OtherTerminatorAreaProcessor otherTerminatorAreaProcessor;

    @Override
    public Sentence[] split(String text) {
        if (text.isEmpty()) {
            return new Sentence[0];
        }

        TreeSet<Integer> splitPoint = getSplitPoint(text);

        return doSplit(splitPoint, text);

    }

    private TreeSet<Integer> getSplitPoint(String text) {
        TreeSet<Integer> splitPoints = otherTerminatorAreaProcessor.find(text);

        return splitPoints;
    }


    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String inputData) {
        Sentence[] result = new Sentence[splitPoint.size() + 1];
        int startIndex = 0;
        int resultIndex = 0;

        for(int point : splitPoint) {
            Sentence sentence = new Sentence(startIndex, point, inputData.substring(startIndex, point).trim());

            result[resultIndex++] = sentence;
            startIndex = point;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }
}
