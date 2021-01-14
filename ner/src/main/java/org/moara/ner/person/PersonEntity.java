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

import org.moara.ner.NamedEntityImpl;

/**
 * 사람 개체명
 *
 * @author wjrmffldrhrl
 */
public class PersonEntity extends NamedEntityImpl {

    /**
     * 사람 개체명 생성자
     *
     * @param text 개체명 내용
     * @param subType 사람 개체명의 세부 범주
     * @param begin 사람 개체명이 시작하는 위치
     * @param end 사람 개체명이 끝나는 위치
     */
    public PersonEntity(String text, String subType, int begin, int end) {
        super(text, "PS_" + subType, begin, end);
    }

}
