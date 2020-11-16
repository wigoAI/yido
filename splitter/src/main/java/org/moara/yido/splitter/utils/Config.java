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
package org.moara.yido.splitter.utils;

/**
 * 문자 구분기 설정 값
 * @author wjrmffldrhrl
 */
public class Config {
    public final int MIN_RESULT_LENGTH;
    public final int PROCESSING_LENGTH_MAX;
    public final int PROCESSING_LENGTH_MIN;
    public final boolean USE_PUBLIC_ROLE;

    /**
     * Constructor
     * @param MIN_RESULT_LENGTH 최소 문장 처리 길이 값, 해당 값보다 작으면 문장으로 인정되지 않는다.
     * @param PROCESSING_LENGTH_MAX 문장 구분점 체크 시 처리하는 최대 길이
     * @param PROCESSING_LENGTH_MIN 문장 구분점 체크 시 처리하는 최소 길이
     */
    public Config(int MIN_RESULT_LENGTH, int PROCESSING_LENGTH_MAX, int PROCESSING_LENGTH_MIN, boolean USE_PUBLIC_ROLE) {
        this.MIN_RESULT_LENGTH = MIN_RESULT_LENGTH;
        this.PROCESSING_LENGTH_MAX = PROCESSING_LENGTH_MAX;
        this.PROCESSING_LENGTH_MIN = PROCESSING_LENGTH_MIN;
        this.USE_PUBLIC_ROLE = USE_PUBLIC_ROLE;

    }

    /**
     * Basic constructor
     *
     * 기본값으로 초기화를 진행한다.
     */
    public Config() {
        this.MIN_RESULT_LENGTH = 5;
        this.PROCESSING_LENGTH_MAX = 3;
        this.PROCESSING_LENGTH_MIN = 2;
        this.USE_PUBLIC_ROLE = true;

    }

}
