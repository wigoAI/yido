package org.moara.ner.entity;

import com.seomse.commons.data.BeginEnd;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 개체명 인식 결과 구현체
 *
 * @author wjrmffldrhrl
 */
public class RecognizeResult{

    private NamedEntity[] entities;

    /**
     * 개체명 인식 결과 생성자
     * @param entities 인식된 개체명들
     */
    public RecognizeResult(NamedEntity[] entities) {
        this.entities = entities;
    }

    public void sortByIndex() {
        this.entities = Arrays.stream(this.entities).sorted(Comparator.comparingInt(BeginEnd::getBegin)).toArray(NamedEntity[]::new);
    }

    public void sortByName() {
        this.entities = Arrays.stream(this.entities).sorted(Comparator.comparing(NamedEntity::getText)).toArray(NamedEntity[]::new);
    }



    /**
     * 인식된 개채명들 반환
     *
     * 이때 결과는 원본의 복사본
     * @return NamedEntity array
     */
    public NamedEntity[] getEntities() {
        return this.entities.clone();

    }

    /**
     * 인식된 개체명 수
     * @return Named entities length
     */
    public int size() {
        return entities.length;
    }

}
