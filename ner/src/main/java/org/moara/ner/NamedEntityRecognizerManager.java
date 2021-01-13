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

import org.moara.ner.exception.RecognizerNotFoundException;
import org.moara.ner.person.PersonNamedEntityRecognizerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 개체명 인식기 관리자 구현체
 *
 * @author wjrmffldrhrl
 */
public class NamedEntityRecognizerManager {

    Map<String, NamedEntityRecognizer> namedEntityRecognizerMap = new HashMap<>();

    /**
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
        if (id.equals("reporter")) {
            namedEntityRecognizerMap.put(id, PersonNamedEntityRecognizerFactory.REPORTER.create());
        } else {
            throw new RecognizerNotFoundException(id);
        }
    }
}
