package org.moara.yido;

import java.util.HashMap;

public class SentenceSplitterManager {

    private static SentenceSplitterManager sentenceSplitterManager = new SentenceSplitterManager();

    private HashMap<Integer, SentenceSplitter> sentenceSplitterHashMap;

    private SentenceSplitterManager() {

    }

    public SentenceSplitter getSentenceSplitter() {
        return null;
    }
    public SentenceSplitter getSentenceSplitter(String docType, String dataType) {
        return null;
    }
    public SentenceSplitter getSentenceSplitter(int id) {
        return null;
    }

    private SentenceSplitter createSentenceSplitter(int id) {
        return this.sentenceSplitterHashMap.get(id);
    }

    public static SentenceSplitterManager getInstance() {
        return SentenceSplitterManager.sentenceSplitterManager;
    }
}
