package org.moara.ner.person;

import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract class PersonNamedEntityRecognizer implements NamedEntityRecognizer {

    protected final String[] targetWords;
    protected final String[] exceptionWords;
    protected final String[] multipleSymbols;
    protected final String multipleSymbolRegx;

    /**
     * 사람 개체명 인식기 생성자
     * @param targetWords 객체를 가리키는 단어들 (e.g: 직업 -> 기자, 리포터, 앵커) 해당 단어를 기준으로 사람 이름을 인식한다.
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     */
    public PersonNamedEntityRecognizer(String[] targetWords, String[] exceptionWords, String[] multipleSymbols) {
        this.targetWords = targetWords;
        this.exceptionWords = exceptionWords;
        this.multipleSymbols = multipleSymbols;

        StringBuilder stringBuilder = new StringBuilder();
        for (String splitter : multipleSymbols) {
            stringBuilder.append("\\").append(splitter);
        }
        this.multipleSymbolRegx = stringBuilder.toString();
    }


    @Override
    public NamedEntity[] recognize(String text) {
        String preprocessedText = textPreprocessing(text);

        Set<NamedEntity> personNamedEntities = new HashSet<>(recognizeEntities(preprocessedText));

        return personNamedEntities.toArray(new NamedEntity[0]);
    }


    protected abstract String textPreprocessing(String text);
    protected abstract Set<NamedEntity> recognizeEntities(String text);
}
