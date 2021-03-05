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

import com.seomse.commons.data.BeginEnd;
import org.moara.ner.entity.NamedEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 토큰 인식기
 * @author wjrmffldrhrl
 */
public class TokenRecognizer extends PersonNamedEntityRecognizer {


    /**
     * 토큰 인식기 생성자
     *
     * @param targetWord     개체를 가리키는 단어
     * @param exceptionWords 인식된 개체명 중 예외 개체명
     */
    TokenRecognizer(String[] targetWord, String[] exceptionWords, String entityType, BeginEnd entityLength) {
        super(targetWord, exceptionWords, new String[]{"·", "?", "/"}, entityType, entityLength);
    }

    @Override
    protected String textPreprocessing(String text) {
        return text;
    }

    @Override
    protected Set<NamedEntity> recognizeEntities(String text) {
        Set<NamedEntity> personTokens = new HashSet<>();

        for (String targetWord : targetWords) {

            String tokenBoundary = "[가-힣 " + multipleSymbolRegx + "]*";
            String tokenRegx = tokenBoundary + targetWord + tokenBoundary;
            Pattern pattern = Pattern.compile(tokenRegx);
            Matcher matcher = pattern.matcher(text);

            matcherFindLoop:
            while (matcher.find()) {
                for (String exceptionWord : exceptionWords) {
                    if (matcher.group().contains(exceptionWord)) {
                        continue matcherFindLoop;
                    }
                }

                if (matcher.group().length() > targetWord.length()) {
                    NamedEntity token = new NamedEntity(text.substring(matcher.start(), matcher.end()), "TOKEN", matcher.start(), matcher.end());
                    personTokens.add(token);
                }
            }
        }

        return personTokens;
    }
}