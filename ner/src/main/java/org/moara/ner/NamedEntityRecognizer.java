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
 * 개체명 인식기 추상체
 *
 * @author wjrmffldrhrl
 */
public interface NamedEntityRecognizer {

    /**
     * {@code text}에 존재하는 개체명 추출
     * @param text 개체명을 추출 할 텍스트
     * @return 개체명 배열
     */
    NamedEntity[] recognize(String text);

}
