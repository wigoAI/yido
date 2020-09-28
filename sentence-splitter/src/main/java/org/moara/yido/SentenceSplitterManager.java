package org.moara.yido;

import java.util.HashMap;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 */
public class SentenceSplitterManager {
    private static final int BASIC_SENTENCE_SPLITTER_ID = 1;
    private static final String JSON_DATA_TYPE = "json";
    private static final String TEXT_DATA_TYPE = "text";
    private static final String SNS_DOC_TYPE = "SNS";
    private static final String NEWS_DOC_TYPE = "news";
    private static final String STT_DOC_TYPE = "stt";
    private static final String QA_DOC_TYPE = "qa";

    private static SentenceSplitterManager sentenceSplitterManager = new SentenceSplitterManager();

    /**
     *  id 1. 기본 문장구분기
     */
    private HashMap<Integer, SentenceSplitter> sentenceSplitterHashMap = new HashMap<>();

    private SentenceSplitterManager() {

    }

    public Sentence[] split(String text) {
        if(!this.sentenceSplitterHashMap.containsKey(BASIC_SENTENCE_SPLITTER_ID)) {
            createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID);
        }
        SentenceSplitter sentenceSplitter = this.sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID);

        return sentenceSplitter.split(text);
    }

    private void createSentenceSplitter(int id) {
        if(id == 1) {
            this.sentenceSplitterHashMap.put(BASIC_SENTENCE_SPLITTER_ID,
                    new BasicSentenceSplitter(5));
        }

    }

    public SentenceSplitter getSentenceSplitter() {
        if(!this.sentenceSplitterHashMap.containsKey(BASIC_SENTENCE_SPLITTER_ID)) {
            createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID);
        }
        return this.sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID);
    }

    public SentenceSplitter getSentenceSplitter(String docType, String dataType) { return null; }
    public SentenceSplitter getSentenceSplitter(int id) { return null; }



    public static SentenceSplitterManager getInstance() { return SentenceSplitterManager.sentenceSplitterManager; }
}
