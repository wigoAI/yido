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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seomse.commons.config.Config;

import com.seomse.commons.data.BeginEnd;
import org.moara.ner.entity.NamedEntity;
import org.moara.ner.exception.RecognizerNotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private final  Map<String, NamedEntityRecognizer> namedEntityRecognizerMap = new HashMap<>();
    private final static String defaultRecognizerIdStr = Config.getConfig("yido.ner.default.id", "PS_REPORTER,TMI_EMAIL,TOKEN");
    private final static String ABSTRACT_PATH = Config.getConfig("yido.ner.data.path", "dic/ner/");
    private final static String TARGET_WORDS_PATH = "target/";
    private final static String EXCEPTION_WORDS_PATH = "exception/";
    private final static String RECOGNIZER_OPTION_PATH = "recognizer/";

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
    public NamedEntity[][] recognize(String text) {
        return recognize(text, defaultRecognizerIds);
    }

    /**
     * 특정 {@code NamedEntityRecognizer}로 전달받은 {@code text}에서 개체명 인식
     * @param text 개체명 인식을 수행할 문자열
     * @param recognizerIds 개체명 인식을 수행할 개체명 인식기
     * @return 인식된 개체명
     */
    public NamedEntity[][] recognize(String text, String[] recognizerIds) {
        NamedEntityRecognizer[] namedEntityRecognizers = new NamedEntityRecognizer[recognizerIds.length];

        for (int i = 0; i < recognizerIds.length; i++) {
            namedEntityRecognizers[i] = getNamedEntityRecognizer(recognizerIds[i]);
        }

        NamedEntity[][] results = new NamedEntity[namedEntityRecognizers.length][];

        int recognizerIndex = 0;
        for (NamedEntityRecognizer namedEntityRecognizer : namedEntityRecognizers) {
            results[recognizerIndex++] = namedEntityRecognizer.recognize(text);
        }

        return results;
    }

    private void createRecognizer(String id) {
        NamedEntityRecognizer namedEntityRecognizer;

        if (id.startsWith("PS_")) {
            namedEntityRecognizer = createNamedEntityRecognizer(id);
        } else if (id.startsWith("TOKEN")) {
            namedEntityRecognizer = getTokenRecognizer(id);
        } else if (id.startsWith("TMI_")) {
            namedEntityRecognizer = getRegxRecognizer(id);
        } else {
            throw new RecognizerNotFoundException(id);
        }

        namedEntityRecognizerMap.put(id, namedEntityRecognizer);
    }

    private NamedEntityRecognizer getRegxRecognizer(String id) {

        JsonObject recognizerOption = getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id);

        String targetWordsDicName = recognizerOption.get("target").getAsString();
        String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
        String entityType = recognizerOption.get("id").getAsString();
        BeginEnd entityLength = getEntityLength(recognizerOption);

        String[] regxArray = readDictionary(TARGET_WORDS_PATH + targetWordsDicName).toArray(new String[0]);
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
                    emailEntities.add(new NamedEntity(matcher.group(), entityType, matcher.start(), matcher.end()));
                }
            }

            return emailEntities.toArray(new NamedEntity[0]);
        };

    }

    private NamedEntityRecognizer getTokenRecognizer(String id) {
        String[] exceptionWords;
        String[] targetWords;
        NamedEntityRecognizer namedEntityRecognizer;
        JsonObject recognizerOption = getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id);

        String targetWordsDicName = recognizerOption.get("target").getAsString();
        String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
        String entityType = recognizerOption.get("id").getAsString();
        BeginEnd entityLengthLimit = getEntityLength(recognizerOption);

        targetWords = readDictionary(TARGET_WORDS_PATH + targetWordsDicName).stream()
                .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
        exceptionWords = readDictionary(EXCEPTION_WORDS_PATH + exceptionWordsDicName).toArray(new String[]{});
        namedEntityRecognizer = new TokenRecognizer(targetWords, exceptionWords, entityType, entityLengthLimit);
        return namedEntityRecognizer;
    }

    private NamedEntityRecognizer createNamedEntityRecognizer(String id) {
        String[] exceptionWords;
        String[] targetWords;
        NamedEntityRecognizer namedEntityRecognizer;
        JsonObject recognizerOption = getJsonObjectByFile(RECOGNIZER_OPTION_PATH + id);

        String targetWordsDicName = recognizerOption.get("target").getAsString();
        String exceptionWordsDicName = recognizerOption.get("exception").getAsString();
        String entityType = recognizerOption.get("id").getAsString();
        BeginEnd entityLengthLimit = getEntityLength(recognizerOption);

        targetWords = readDictionary(TARGET_WORDS_PATH + targetWordsDicName).stream()
                .map(line -> line.replaceAll("[\\[\\]]", "")).toArray(String[]::new);
        exceptionWords = readDictionary(EXCEPTION_WORDS_PATH + exceptionWordsDicName).toArray(new String[]{});
        namedEntityRecognizer = new PersonNamedEntityRecognizer(targetWords, exceptionWords, new String[]{"·", "?", "/"}, entityType, entityLengthLimit);
        return namedEntityRecognizer;
    }

    private BeginEnd getEntityLength(JsonObject recognizerOption) {
        JsonObject entityLengthJson = recognizerOption.getAsJsonObject("entity_length");
        int min = entityLengthJson.get("min").getAsInt();
        int max = entityLengthJson.get("max").getAsInt();

        return new BeginEnd(){
            @Override
            public int getBegin() {
                return min;
            }

            @Override
            public int getEnd() {
                return max;
            }
        };
    }

    private JsonObject getJsonObjectByFile(String fileName) {
        JsonElement element;

        try {
            element = JsonParser.parseReader(new FileReader(ABSTRACT_PATH + fileName + ".json"));
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return element.getAsJsonObject();
    }

    public Collection<String> readDictionary(String fileName){
        Collection<String> file = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ABSTRACT_PATH + fileName + ".dic"), StandardCharsets.UTF_8))) {

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                file.add(line);
            }

        }catch (IOException e) {
            e.printStackTrace();

        }


        return file;
    }
}
