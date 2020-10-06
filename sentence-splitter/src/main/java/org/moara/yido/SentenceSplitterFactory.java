package org.moara.yido;

import java.util.HashMap;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 */
public class SentenceSplitterFactory {
    private static final int BASIC_SENTENCE_SPLITTER_ID = 1;
    private static final int NEWS_SENTENCE_SPLITTER_ID = 2;
    private static final String JSON_DATA_TYPE = "json";
    private static final String TEXT_DATA_TYPE = "text";
    private static final String SNS_DOC_TYPE = "SNS";
    private static final String NEWS_DOC_TYPE = "news";
    private static final String STT_DOC_TYPE = "stt";
    private static final String QA_DOC_TYPE = "qa";

    /**
     *  id 1. 기본 문장구분기
     */
    private static final HashMap<Integer, SentenceSplitter> sentenceSplitterHashMap = new HashMap<>();
    private static final SentenceSplitterFactory sentenceSplitterFactory = new SentenceSplitterFactory();

    private SentenceSplitterFactory() { }

    public Sentence[] split(String text) {
        if(isKeyEmpty(BASIC_SENTENCE_SPLITTER_ID)) {
            createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID);
        }

        return sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID).split(text);
    }


    public SentenceSplitter getSentenceSplitter() {
        if(isKeyEmpty(BASIC_SENTENCE_SPLITTER_ID)) { createSentenceSplitter(BASIC_SENTENCE_SPLITTER_ID); }
        return sentenceSplitterHashMap.get(BASIC_SENTENCE_SPLITTER_ID);
    }

    public SentenceSplitter getSentenceSplitter(String docType, String dataType) { return null; }
    public SentenceSplitter getSentenceSplitter(int id) {
        if(isKeyEmpty(id)) {
            createSentenceSplitter(id);
        }
        return sentenceSplitterHashMap.get(id);
    }

    private void createSentenceSplitter(int id) {
        if(id == BASIC_SENTENCE_SPLITTER_ID) {
            sentenceSplitterHashMap.put(BASIC_SENTENCE_SPLITTER_ID,
                    new BasicSentenceSplitter());
        } else if(id == NEWS_SENTENCE_SPLITTER_ID) {
            sentenceSplitterHashMap.put(NEWS_SENTENCE_SPLITTER_ID,
                    new NewsSentenceSplitter());
        }
    }

    private boolean isKeyEmpty(int key) { return !sentenceSplitterHashMap.containsKey(key); }
    public static SentenceSplitterFactory getInstance() { return SentenceSplitterFactory.sentenceSplitterFactory; }


}

