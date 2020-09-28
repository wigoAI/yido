package org.moara.yido;

import org.moara.yido.area.processor.AreaProcessor;
import org.moara.yido.area.processor.ConnectiveAreaProcessor;
import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;

public class TextSentenceSplitter implements SentenceSplitter{
    AreaProcessor terminatorAreaProcessor;
    AreaProcessor connectiveAreaProcessor;
    AreaProcessor exceptionAreaProcessor;

    TextSentenceSplitter() {
        initAreaProcessor();
    }

    private void initAreaProcessor() {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor();
        this.connectiveAreaProcessor = new ConnectiveAreaProcessor();
        this.exceptionAreaProcessor = new ExceptionAreaProcessor();

    }

    @Override
    public Sentence[] split(String text) {
        return new Sentence[0];
    }
}
