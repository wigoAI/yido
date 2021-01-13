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

/**
 * 사람 개체를 확장한 기자 개체
 *
 * 서브 타입을 포함한다.
 * @author wjrmffldrhrl
 */
public class ReporterEntity extends PersonEntity {

    /**
     * 기자 개체 생성자
     *
     * @param text 개체 값
     * @param begin 개체 시작 인덱스
     * @param end 개체 끝 인덱스
     */
    public ReporterEntity(String text, int begin, int end) {
        super(text, "REPORTER", begin, end);
    }
}
