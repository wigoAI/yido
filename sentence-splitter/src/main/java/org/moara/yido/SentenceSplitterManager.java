package org.moara.yido;

public class SentenceSplitterManager {

    private static SentenceSplitterManager sentenceSplitterManager = new SentenceSplitterManager();

    public SentenceSplitter getSentenceSplitter() {
        return null;
    }
    public SentenceSplitter getSentenceSplitter(String docType, String dataType) {
        return null;
    }
    public SentenceSplitter getSentenceSplitter(int id) {
        return null;
    }
    public SentenceSplitterManager getInstance() {
        return this.sentenceSplitterManager;
    }
}
