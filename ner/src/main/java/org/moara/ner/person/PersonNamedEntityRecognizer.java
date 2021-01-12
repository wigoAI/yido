package org.moara.ner.person;

import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;

import java.util.ArrayList;
import java.util.List;

abstract class PersonNamedEntityRecognizer implements NamedEntityRecognizer {

    protected final String targetWord;
    protected final String[] exceptionWords;
    protected final String[] multipleSymbols;
    protected final String multipleSymbolRegx;

    /**
     * 사람 개체명 인식기 생성자
     * @param targetWord 객체를 가리키는 단어 (e.g: 직업 -> 기자, 리포터, 앵커) 해당 단어를 기준으로 사람 이름을 인식한다.
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     */
    public PersonNamedEntityRecognizer(String targetWord, String[] exceptionWords, String[] multipleSymbols) {
        this.targetWord = targetWord;
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

        List<NamedEntity> personNamedEntities = new ArrayList<>(recognizeEntities(preprocessedText));

        return personNamedEntities.toArray(new NamedEntity[0]);
    }


    protected abstract String textPreprocessing(String text);
    protected abstract List<NamedEntity> recognizeEntities(String text);
}
