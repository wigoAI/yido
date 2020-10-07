package org.moara.yido;

import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.NewsRoleManager;

import java.util.TreeSet;

public class NewsSentenceSplitter implements SentenceSplitter{
    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    NewsSentenceSplitter(Config config) {
        initAreaProcessor(config);
    }

    private void initAreaProcessor(Config config) {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor(NewsRoleManager.getRoleManager(), config);
        this.exceptionAreaProcessor = new ExceptionAreaProcessor(NewsRoleManager.getRoleManager());
    }

    @Override
    public Sentence[] split(String text) {
        this.exceptionAreaProcessor.find(text);
        TreeSet<Integer> splitPoint = findSplitPoint(text);
        return doSplit(splitPoint, text);
    }

    private TreeSet<Integer> findSplitPoint(String text) {
        return this.terminatorAreaProcessor.find(text, this.exceptionAreaProcessor);
    }

    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String text) {
        int startIndex = 0;
        int endIndex = 0;
        int resultIndex = 0;
        Sentence[] result = new Sentence[splitPoint.size() + 1];

        for(int point : splitPoint) {
            endIndex = point;
            Sentence sentence = new Sentence(startIndex, endIndex, text.substring(startIndex, endIndex).trim());

            result[resultIndex++] = sentence;
            startIndex = endIndex;

        }

        result[resultIndex] =  new Sentence(startIndex, text.length(), text.substring(startIndex).trim());

        return result;
    }
}
