package org.moara.ner;

/**
 * 개체명 정보 추상체
 */
public interface NamedEntity {

    /**
     * 개체명 값 반환
     * @return 개체 정보
     */
    String getValue();

    /**
     * 개체명 테그 범주 반환
     *
     * @return 개체명 테그
     */
    String getTag();

}
