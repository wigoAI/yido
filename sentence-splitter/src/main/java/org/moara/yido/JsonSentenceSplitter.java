package org.moara.yido;

import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.BasicRoleManager;

public class JsonSentenceSplitter implements SentenceSplitter {
    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    JsonSentenceSplitter() {
        initAreaProcessor();
    }

    private void initAreaProcessor() {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor(BasicRoleManager.getRoleManager());
        this.exceptionAreaProcessor = new ExceptionAreaProcessor(BasicRoleManager.getRoleManager());

    }

    @Override
    public Sentence[] split(String text) {
        return new Sentence[0];
    }
}
