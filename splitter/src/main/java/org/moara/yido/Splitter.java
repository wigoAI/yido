/*
 * Copyright (C) 2020 Wigo Inc.
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
package org.moara.yido;

import org.moara.yido.utils.Sentence;

/**
 * 문장 구분기 추상체
 * @author 조승현
 */
public interface Splitter {

    /**
     * 입력된 문자를 문장 단위로 나누어 반환
     * @param text String
     * @return Sentence Array
     */
    Sentence[] split(String text);
}