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

import com.google.gson.JsonObject;
import org.moara.filemanager.FileManager;
import org.moara.ner.exception.RecognizerNotFoundException;
import org.moara.splitter.utils.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 개체명 인식기 관리자 구현체
 *
 * TODO 1. JSON 파일 유효성 체크
 *
 * @author wjrmffldrhrl
 */
public class NamedEntityRecognizerManager {

    // key = id, value = NamedEntityRecognizer instance
    final private Map<String, NamedEntityRecognizer> namedEntityRecognizerMap = new HashMap<>();
    private final static String TARGET_WORDS_PATH = "ner/target/";
    private final static String EXCEPTION_WORDS_PATH = "ner/exception/";
    private final static String RECOGNIZER_OPTION_PATH = "ner/recognizer/";
    /**
     *
     * Manager Instance 반환 메서드
     * @return NamedEntityRecognizerManager
     */
    public static NamedEntityRecognizerManager getInstance() {
        return Singleton.instance;
    }
    private static class Singleton {
        private static final NamedEntityRecognizerManager instance = new NamedEntityRecognizerManager();
    }

    // thread lock
    private final Object createLock = new Object();
    public NamedEntityRecognizer getNamedEntityRecognizer(String id) {
        NamedEntityRecognizer namedEntityRecognizer = namedEntityRecognizerMap.get(id);

        if (namedEntityRecognizer == null) {
            synchronized (createLock) {
                namedEntityRecognizer = namedEntityRecognizerMap.get(id);
                if (namedEntityRecognizer == null) {
                    createRecognizer(id);
                    namedEntityRecognizer = namedEntityRecognizerMap.get(id);
                }
            }
        }

        return namedEntityRecognizer;
    }


    private void createRecognizer(String id) {
        NamedEntityRecognizer namedEntityRecognizer;

        String[] targetWords;
        String[] exceptionWords;


        switch (id) {
            case "custom":
            case "ps_reporter":
                JsonObject recognizerOption = FileManager.getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id + ".json");

                String targetWordsDicName = recognizerOption.get("target").getAsString();
                String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
                String entityType = recognizerOption.get("id").getAsString();
                Area entityLength = getEntityLength(recognizerOption);

                targetWords = FileManager.readFile(TARGET_WORDS_PATH + targetWordsDicName + ".dic").stream()
                        .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
                exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + exceptionWordsDicName + ".dic").toArray(new String[]{});
               namedEntityRecognizer = new PersonNamedEntityRecognizer(targetWords, exceptionWords,  new String[]{"·", "?", "/"}, entityType, entityLength);

                break;
            case "token":
                targetWords = FileManager.readFile(TARGET_WORDS_PATH + "token.dic").stream()
                        .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
                exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + "token.dic").toArray(new String[]{});
                namedEntityRecognizer = new TokenRecognizer(targetWords, exceptionWords, "TOKEN");
                break;
            case "email": // TODO regx 데이터 받아서 유동적으로 체크할 수 있게
                String regx = "[0-9a-zA-Z][0-9a-zA-Z\\_\\-\\.]+[0-9a-zA-Z]@[0-9a-zA-Z][0-9a-zA-Z\\_\\-\\.]*[0-9a-zA-Z]";
                Pattern pattern = Pattern.compile(regx);
                namedEntityRecognizer = text -> {
                    List<NamedEntity> emailEntities = new ArrayList<>();
                    Matcher matcher = pattern.matcher(text);

                    while (matcher.find()) {
                        emailEntities.add(new NamedEntityImpl(matcher.group(), "PS_EMAIL", matcher.start(), matcher.end()));
                    }

                    return emailEntities.toArray(new NamedEntity[0]);
                };
                break;
            default:
                throw new RecognizerNotFoundException(id);

        }

        namedEntityRecognizerMap.put(id, namedEntityRecognizer);
    }
    private Area getEntityLength(JsonObject recognizerOption) {
        JsonObject entityLengthJson = recognizerOption.getAsJsonObject("entity_length");
        int min = entityLengthJson.get("min").getAsInt();
        int max = entityLengthJson.get("max").getAsInt();

        return new Area(min, max);
    }
}
