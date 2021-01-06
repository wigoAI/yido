package org.moara.ner;

/**
 * 개체명 인식기 추상체
 */
public interface NamedEntityRecognizer {

    /**
     * Corpus에 존재하는 개체명 추출
     * @param corpus 개체명을 추출 할 Corpus
     * @return 개체명 배열
     */
    NamedEntity[] recognize(String corpus);

}
