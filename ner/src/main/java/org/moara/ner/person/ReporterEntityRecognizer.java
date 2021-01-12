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

import java.util.*;

/**
 * 사람 개체명 인식기
 *
 * TODO 1. [조승현 **전문 기자]와 같은 유형 체크
 * @author wjrmffldrhrl
 */
class ReporterEntityRecognizer extends PersonNamedEntityRecognizer {


    ReporterEntityRecognizer(String targetWord, String[] exceptionWords) {
        super(targetWord, exceptionWords, new String[]{"·", "?", "/"});
    }

    @Override
    protected String textPreprocessing(String text) {
        for (String multipleSymbol : multipleSymbols) {
            text = text.replace(targetWord + multipleSymbol, targetWord + " ");
        }

        text = text.replaceAll("[^가-힣" + multipleSymbolRegx + "]", " ")
                .replaceAll("[" + multipleSymbolRegx + "]", "M");

        return text;
    }

    @Override
    protected List<NamedEntity> recognizeEntities(String text) {

        List<NamedEntity> personNamedEntities = getEntities(text, " " + targetWord + " ");
        personNamedEntities.addAll(getEntities(text, targetWord + " "));
        personNamedEntities.addAll(getEntities(text, " " + targetWord + "입니다 "));

        return personNamedEntities;

    }

    private List<NamedEntity> getEntities(String text, String targetWord) {

        // 가장 앞에 기자 이름이 붙어있는 경우 해결
        text = " " + text + " ";

        List<NamedEntity> personNamedEntities = new ArrayList<>();
        int targetIndex = 0;
        while (targetIndex < text.length()) {
            targetIndex = text.indexOf(targetWord, targetIndex);

            // targetWord 없음
            if (targetIndex == -1) {
                break;
            }


            int entityBegin = text.substring(0, targetIndex).lastIndexOf(" ") + 1;


            if (entityBegin == 0 || entityBegin == targetIndex) {
                targetIndex++;
                continue;
            }

            String[] names = text.substring(entityBegin, targetIndex).split("M");


            nameLoop:
            for (String name : names) {
                int entityEnd = entityBegin + name.length();

                ReporterEntity reporterEntity = new ReporterEntity(name, entityBegin - 1, entityEnd - 1);
                entityBegin = entityEnd + 1;

                if (name.length() < 2 || name.length() > 4) {
                    continue;
                }
//                if (name.length() != 3) {
//                    continue;
//                }

                for (String exceptionWord : exceptionWords) {
                    if (name.contains(exceptionWord)) {
                        continue nameLoop;
                    }
                }


                personNamedEntities.add(reporterEntity);

            }

            targetIndex++;

        }

        return personNamedEntities;
    }
}