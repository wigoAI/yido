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

/**
 * 개체명인식기 팩토리 추상체
 *
 * @author wjrmffldrhrl
 */
public interface NamedEntityRecognizerManager {

    /**
     * 인식기 획득
     * @param id 획득하려는 인식기의 id
     * @return NamedEntityRecognizer
     */
    NamedEntityRecognizer getNamedEntityRecognizer(String id);

}
