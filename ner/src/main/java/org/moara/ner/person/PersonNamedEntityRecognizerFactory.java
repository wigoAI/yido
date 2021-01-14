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

import org.moara.filemanager.FileManager;
import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 사람 개체명 인식기 팩토리
 *
 * 싱글턴으로 유지한다.
 *
 * 동작이 다른 {@code create()} 메서드가 필요한 경우 override 하면 된다.
 *
 * @author wjrmffldrhrl
 **/
public enum PersonNamedEntityRecognizerFactory {
    REPORTER("reporter", "REPORTER"),
    TOKEN("token", "TOKEN"){
        @Override
        public NamedEntityRecognizer create() {

            String[] targetWords = FileManager.readFile(TARGET_WORDS_PATH + "token.dic").stream()
                    .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
            String[] exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + "token.dic").toArray(new String[]{});
            return new PersonTokenRecognizer(targetWords, exceptionWords);
        }
    },
    EMAIL("email", "EMAIL") {
        @Override
        public NamedEntityRecognizer create() {
            return text -> {
                List<NamedEntity> emailEntities = new ArrayList<>();
                String emailFindRegx = "[0-9a-zA-Z][0-9a-zA-Z\\_\\-\\.]+[0-9a-zA-Z]@[0-9a-zA-Z][0-9a-zA-Z\\_\\-\\.]*[0-9a-zA-Z]";
                Matcher matcher = Pattern.compile(emailFindRegx).matcher(text);


                while (matcher.find()) {
                    emailEntities.add(new PersonEntity(matcher.group(), "EMAIL", matcher.start(), matcher.end()));
                }

                return emailEntities.toArray(new NamedEntity[0]);
            };
        }
    };

    private final String id;
    private final String entityType;

    private final static String TARGET_WORDS_PATH = "ner/target/";
    private final static String EXCEPTION_WORDS_PATH = "ner/exception/";

    PersonNamedEntityRecognizerFactory(String id, String entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    public NamedEntityRecognizer create() {
        String[] targetWords = FileManager.readFile(TARGET_WORDS_PATH + this.id + ".dic").stream()
                .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
        String[] exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + this.id + ".dic").toArray(new String[]{});

        return new PersonNamedEntityRecognizer(targetWords, exceptionWords,  new String[]{"·", "?", "/"}, entityType);
    }
}
