package org.moara.splitter;

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.processor.OtherExceptionAreaProcessor;
import org.moara.splitter.processor.OtherTerminatorAreaProcessor;
import org.moara.splitter.role.SplitConditionManager;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class OtherSplitter implements Splitter {
    protected OtherTerminatorAreaProcessor otherTerminatorAreaProcessor;
    protected OtherExceptionAreaProcessor otherExceptionAreaProcessor;

    public OtherSplitter(OtherTerminatorAreaProcessor otherTerminatorAreaProcessor,
                         OtherExceptionAreaProcessor otherExceptionAreaProcessor) {
        this.otherTerminatorAreaProcessor = otherTerminatorAreaProcessor;
        this.otherExceptionAreaProcessor = otherExceptionAreaProcessor;
    }

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
        List<Area> exceptionAreas = otherExceptionAreaProcessor.find(text);
        List<Integer> removeItems = new ArrayList<>();

        for (Area exceptionArea : exceptionAreas) {
            for(int splitPoint : splitPoints) {
                if(exceptionArea.contains(splitPoint)) { removeItems.add(splitPoint); }
            }
        }

        for (int removeItem : removeItems) {
            splitPoints.remove(removeItem);
        }

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
