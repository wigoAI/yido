/*
 * Copyright (C) 2021 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moara.ner.person;

import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;
import java.util.HashSet;
import java.util.Set;

/**
 * 사람 개체명 인식기 추상 클래스
 *
 * {@code recognize} 메서드만 구현되어있으며 해당 클래스를 상속받아 {@code recognize}에서 호출하는 메서드인
 * {@code textPreprocessing} 와 {@code recognizeEntities}를 구현해야 한다.
 * @author wjrmffldrhrl
 */
abstract class PersonNamedEntityRecognizer implements NamedEntityRecognizer {

    protected final String[] targetWords;
    protected final String[] exceptionWords;
    protected final String[] multipleSymbols;
    protected final String multipleSymbolRegx;

    /**
     * 사람 개체명 인식기 생성자
     * @param targetWords 개체명을 가리키는 단어들 (e.g: 직업 -> 기자, 리포터, 앵커) 해당 단어를 기준으로 사람 이름을 인식한다.
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     * @param multipleSymbols 개체명이 여러개가 동시에 등장할 때 해당 개체명들을 나누는 구분 기호들
     *                        e.g.) 김승현/조승현/박승현 기자 -> {@code multipleSymbol = "/"}
     *
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
