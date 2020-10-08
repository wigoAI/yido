package org.moara.yido;

public class Config {
    public final int MIN_SENTENCE_LENGTH;
    public final int PROCESSING_LENGTH_MAX;
    public final int PROCESSING_LENGTH_MIN;
    public final String DOC_TYPE;
    public final String DATA_TYPE;

    public Config(int MIN_SENTENCE_LENGTH, int PROCESSING_LENGTH_MAX, int PROCESSING_LENGTH_MIN, String DATA_TYPE, String DOC_TYPE ) {
        this.MIN_SENTENCE_LENGTH = MIN_SENTENCE_LENGTH;
        this.PROCESSING_LENGTH_MAX = PROCESSING_LENGTH_MAX;
        this.PROCESSING_LENGTH_MIN = PROCESSING_LENGTH_MIN;
        this.DATA_TYPE = DATA_TYPE;
        this.DOC_TYPE = DOC_TYPE;

    }

    public Config() {
        this.MIN_SENTENCE_LENGTH = 5;
        this.PROCESSING_LENGTH_MAX = 3;
        this.PROCESSING_LENGTH_MIN = 2;
        this.DATA_TYPE = "text";
        this.DOC_TYPE = "basic";

    }
}
