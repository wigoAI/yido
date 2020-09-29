package org.moara.yido;

import org.moara.yido.area.Area;
import org.moara.yido.area.processor.AreaProcessor;
import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;

import java.util.HashSet;
import java.util.List;

public class TextSentenceSplitter implements SentenceSplitter{
    AreaProcessor terminatorAreaProcessor;
    AreaProcessor exceptionAreaProcessor;

    TextSentenceSplitter() {
        initAreaProcessor();
    }

    private void initAreaProcessor() {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor();
        this.exceptionAreaProcessor = new ExceptionAreaProcessor();
    }

    @Override
    public Sentence[] split(String text) {
        List<Area> exceptionArea = this.exceptionAreaProcessor.find(text);
        List<Area> terminatorArea = this.terminatorAreaProcessor.find(text);
        System.out.println("do Split!");
        doSplit(exceptionArea, terminatorArea);

        return new Sentence[0];
    }

    private List<Sentence> doSplit(List<Area> exceptionArea, List<Area> terminatorArea) {
        for(Area targetArea : terminatorArea) {
            System.out.println(targetArea.getStart());
        }

        return null;
    }
}
