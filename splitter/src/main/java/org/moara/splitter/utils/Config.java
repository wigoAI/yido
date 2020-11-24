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
package org.moara.splitter.utils;

/**
 * 문자 구분기 설정 값
 *
 *
 * TODO 1. 교체된 DB 구조에 맞춰서 설정값 변경경
 *          - MIN_RESULT_LENGTH 이외에 필요한 설정 값이 있는가?
*
 * @author wjrmffldrhrl
 */
public class Config {
    public final int MIN_RESULT_LENGTH;

    /**
     * Constructor
     * @param MIN_RESULT_LENGTH 최소 문장 처리 길이 값, 해당 값보다 작으면 문장으로 인정되지 않는다.
     *
     */
    public Config(int MIN_RESULT_LENGTH) {
        this.MIN_RESULT_LENGTH = MIN_RESULT_LENGTH;

    }

    /**
     * Basic constructor
     *
     * 기본값으로 초기화를 진행한다.
     */
    public Config() {
        this.MIN_RESULT_LENGTH = 5;

    }

}
