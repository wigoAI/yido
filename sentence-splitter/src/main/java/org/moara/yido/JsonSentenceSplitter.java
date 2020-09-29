package org.moara.yido;

import org.moara.yido.area.processor.AreaProcessor;
import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;

public class JsonSentenceSplitter implements SentenceSplitter {
    AreaProcessor terminatorAreaProcessor;
    AreaProcessor exceptionAreaProcessor;

    JsonSentenceSplitter() {
        initAreaProcessor();
    }

    private void initAreaProcessor() {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor();
        this.exceptionAreaProcessor = new ExceptionAreaProcessor();

    }

    @Override
    public Sentence[] split(String text) {
        return new Sentence[0];
    }
}
