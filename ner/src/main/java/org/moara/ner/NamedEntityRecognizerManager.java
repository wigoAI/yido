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
import com.seomse.commons.config.Config;

import org.moara.filemanager.FileManager;
import org.moara.ner.exception.RecognizerNotFoundException;
import org.moara.splitter.utils.Area;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 개체명 인식기 관리자 구현체
 * <p>
 * TODO 1. JSON 파일 유효성 체크
 *
 * @author wjrmffldrhrl
 */
public class NamedEntityRecognizerManager {

    // key = id, value = NamedEntityRecognizer instance
    private final  Map<String, NamedEntityRecognizer> namedEntityRecognizerMap = new HashMap<>();
    private final static String defaultRecognizerIdStr = Config.getConfig("yido.ner.default.id", "ps_reporter,tm_email,token");
    private final static String TARGET_WORDS_PATH = "ner/target/";
    private final static String EXCEPTION_WORDS_PATH = "ner/exception/";
    private final static String RECOGNIZER_OPTION_PATH = "ner/recognizer/";
    private final String[] defaultRecognizerIds;

    private NamedEntityRecognizerManager() {
        this.defaultRecognizerIds = Arrays.stream(defaultRecognizerIdStr.split(","))
                .map(String::trim).toArray(String[]::new);
    }

    /**
     * Manager Instance 반환 메서드
     *
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

    /**
     * 전달받은 {@code id}로 NamedEntityRecognizer instance 를 반환한다.
     * 모든 인스턴스는 싱글턴으로 유지되며 최초 호출시에 생성되고 계속해서 유지된다.
     *
     * @param id NamedEntityRecognizer id
     * @return NamedEntityRecognizer instance
     */
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

    /**
     * 기본으로 설정된 {@code NamedEntityRecognizer}로 전달받은 {@code text}에서 개체명 인식
     *
     * @param text 개체명 인식을 수행 할 문자열
     * @return 인식된 개체명
     */
    public NamedEntity[] recognize(String text) {
        return recognize(text, defaultRecognizerIds);
    }

    /**
     * 특정 {@code NamedEntityRecognizer}로 전달받은 {@code text}에서 개체명 인식
     * @param text 개체명 인식을 수행할 문자열
     * @param recognizerIds 개체명 인식을 수행할 개체명 인식기
     * @return 인식된 개체명
     */
    public NamedEntity[] recognize(String text, String[] recognizerIds) {
        NamedEntityRecognizer[] namedEntityRecognizers = new NamedEntityRecognizer[recognizerIds.length];

        for (int i = 0; i < recognizerIds.length; i++) {
            namedEntityRecognizers[i] = getNamedEntityRecognizer(recognizerIds[i]);
        }

        NamedEntity[][] entities = new NamedEntity[namedEntityRecognizers.length][];

        int recognizerIndex = 0;
        for (NamedEntityRecognizer namedEntityRecognizer : namedEntityRecognizers) {
            entities[recognizerIndex++] = namedEntityRecognizer.recognize(text);
        }

        return Arrays.stream(entities).flatMap(Arrays::stream).toArray(NamedEntity[]::new);
    }

    private void createRecognizer(String id) {
        NamedEntityRecognizer namedEntityRecognizer;

        if (id.startsWith("ps_")) {
            namedEntityRecognizer = createNamedEntityRecognizer(id);
        } else if (id.startsWith("token")) {
            namedEntityRecognizer = getTokenRecognizer(id);
        } else if (id.startsWith("tm_")) {
            namedEntityRecognizer = getRegxRecognizer(id);
        } else {
            throw new RecognizerNotFoundException(id);
        }

        namedEntityRecognizerMap.put(id, namedEntityRecognizer);
    }

    private NamedEntityRecognizer getRegxRecognizer(String id) {

        JsonObject recognizerOption = FileManager.getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id + ".json");

        String targetWordsDicName = recognizerOption.get("target").getAsString();
        String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
        String entityType = recognizerOption.get("id").getAsString();
        Area entityLength = getEntityLength(recognizerOption);

        String[] regxArray = FileManager.readFile(TARGET_WORDS_PATH + targetWordsDicName + ".dic").toArray(new String[0]);
        Pattern[] patternArray = new Pattern[regxArray.length];
        for (int i = 0; i < regxArray.length; i++) {
             patternArray[i] = Pattern.compile(regxArray[i]);
        }

        return text -> {
            List<NamedEntity> emailEntities = new ArrayList<>();
            for (Pattern pattern : patternArray) {
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    if (matcher.group().length() < entityLength.getBegin()
                            || matcher.group().length() > entityLength.getEnd()) {
                        continue;
                    }
                    emailEntities.add(new NamedEntityImpl(matcher.group(), entityType, matcher.start(), matcher.end()));
                }
            }

            return emailEntities.toArray(new NamedEntity[0]);
        };

    }

    private NamedEntityRecognizer getTokenRecognizer(String id) {
        String[] exceptionWords;
        String[] targetWords;
        NamedEntityRecognizer namedEntityRecognizer;
        targetWords = FileManager.readFile(TARGET_WORDS_PATH + id + ".dic").stream()
                .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
        exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + id + ".dic").toArray(new String[]{});
        namedEntityRecognizer = new TokenRecognizer(targetWords, exceptionWords, "TOKEN", new Area(4, 99));
        return namedEntityRecognizer;
    }

    private NamedEntityRecognizer createNamedEntityRecognizer(String id) {
        String[] exceptionWords;
        String[] targetWords;
        NamedEntityRecognizer namedEntityRecognizer;
        JsonObject recognizerOption = FileManager.getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id + ".json");

        String targetWordsDicName = recognizerOption.get("target").getAsString();
        String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
        String entityType = recognizerOption.get("id").getAsString();
        Area entityLength = getEntityLength(recognizerOption);

        targetWords = FileManager.readFile(TARGET_WORDS_PATH + targetWordsDicName + ".dic").stream()
                .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
        exceptionWords = FileManager.readFile(EXCEPTION_WORDS_PATH + exceptionWordsDicName + ".dic").toArray(new String[]{});
        namedEntityRecognizer = new PersonNamedEntityRecognizer(targetWords, exceptionWords, new String[]{"·", "?", "/"}, entityType, entityLength);
        return namedEntityRecognizer;
    }

    private Area getEntityLength(JsonObject recognizerOption) {
        JsonObject entityLengthJson = recognizerOption.getAsJsonObject("entity_length");
        int min = entityLengthJson.get("min").getAsInt();
        int max = entityLengthJson.get("max").getAsInt();

        return new Area(min, max);
    }



}
