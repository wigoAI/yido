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
package org.moara.ner;

import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;
import org.moara.splitter.utils.Area;

import java.util.*;

/**
 * 사람 개체명 인식기 구현체
 *
 * {@code PersonNamedEntityRecognizerFactory}에서만 접근 가능
 *
 * @author wjrmffldrhrl
 */
class PersonNamedEntityRecognizer implements NamedEntityRecognizer {

    protected final String[] targetWords;
    protected final String[] exceptionWords;
    protected final String[] multipleSymbols;
    protected final String multipleSymbolRegx;
    protected final String entityType;

    private final int minEntityLength;
    private final int maxEntityLength;
    /**
     * 사람 개체명 인식기 생성자
     * @param targetWords 개체명을 가리키는 단어들 (e.g: 직업 -> 기자, 리포터, 앵커) 해당 단어를 기준으로 사람 이름을 인식한다.
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     * @param multipleSymbols 개체명이 여러개가 동시에 등장할 때 해당 개체명들을 나누는 구분 기호들
     *                        e.g.) 김승현/조승현/박승현 기자 -> {@code multipleSymbol = "/"}
     */
    public PersonNamedEntityRecognizer(String[] targetWords, String[] exceptionWords, String[] multipleSymbols, String entityType, Area entityLength) {
        this.targetWords = targetWords;
        this.exceptionWords = exceptionWords;
        this.multipleSymbols = multipleSymbols;
        this.entityType = entityType;

        this.minEntityLength = entityLength.getBegin();
        this.maxEntityLength = entityLength.getEnd();

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
    

    protected String textPreprocessing(String text) {
        // 개체나 targetWord 가 맨 앞이나 뒤에 뭍어있는 경우 처리
        text = " " + text + " ";

        // 목표 단어와 구분 문자가 붙어있을 경우 검출이 안된다.
        // 목표 단어와 붙어있는 구분 문자 공백으로 변경
        for (String targetWord : targetWords) {
            targetWord = targetWord.replace(" ", "");
            text = text.replaceAll(targetWord + "[" + multipleSymbolRegx + "]", targetWord + " ");

        }

        // 1. 구분 문자와 한글을 제외한 모든 문자 공백으로 변경
        // 2. 모든 구분 문자 "M"으로 변경
        text = text.replaceAll("[^가-힣" + multipleSymbolRegx + "]", " ")
                .replaceAll("[" + multipleSymbolRegx + "]", "M");

        return text;
    }

    protected Set<NamedEntity> recognizeEntities(String text) {

        Set<NamedEntity> personNamedEntities = new HashSet<>();
        for (String targetWord : targetWords) {
            personNamedEntities.addAll(getEntities(text, targetWord));
        }

        return personNamedEntities;
    }

    private List<NamedEntity> getEntities(String text, String targetWord) {
        List<NamedEntity> personNamedEntities = new ArrayList<>();
        int targetIndex = 0;
        while (targetIndex < text.length()) {
            targetIndex = text.indexOf(targetWord, targetIndex);

            // targetWord 없음
            if (targetIndex == -1) {
                break;
            }

            // 개체의 시작점 " 조승현 기자" 일 경우 " 조승현" 에서 " "의 위치를 찾는다.
            int entityBegin = text.substring(0, targetIndex).lastIndexOf(" ") + 1;

            if (entityBegin == 0 || entityBegin == targetIndex) {
                targetIndex++;
                continue;
            }



            String[] names = text.substring(entityBegin, targetIndex).split("M");

            nameLoop:
            for (String name : names) {
                int entityEnd = entityBegin + name.length();

                NamedEntityImpl personEntity = new NamedEntityImpl(name, entityType, entityBegin - 1, entityEnd - 1);
                entityBegin = entityEnd + 1;

                if (name.length() < minEntityLength || name.length() > maxEntityLength) {
                    continue;
                }

                for (String exceptionWord : exceptionWords) {
                    if (name.contains(exceptionWord)) {
                        continue nameLoop;
                    }
                }

                personNamedEntities.add(personEntity);
            }

            targetIndex++;
        }

        return personNamedEntities;
    }
}
