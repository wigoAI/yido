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

/**
 * 문자 구분기 설정 값
 * @author 조승현
 */
public class Config {
    public final int MIN_SENTENCE_LENGTH;
    public final int PROCESSING_LENGTH_MAX;
    public final int PROCESSING_LENGTH_MIN;
    public final String DOC_TYPE;
    public final String DATA_TYPE;

    /**
     * Constructor
     * @param MIN_SENTENCE_LENGTH int
     * @param PROCESSING_LENGTH_MAX int
     * @param PROCESSING_LENGTH_MIN int
     * @param DATA_TYPE String
     * @param DOC_TYPE String
     */
    public Config(int MIN_SENTENCE_LENGTH, int PROCESSING_LENGTH_MAX, int PROCESSING_LENGTH_MIN, String DATA_TYPE, String DOC_TYPE ) {
        this.MIN_SENTENCE_LENGTH = MIN_SENTENCE_LENGTH;
        this.PROCESSING_LENGTH_MAX = PROCESSING_LENGTH_MAX;
        this.PROCESSING_LENGTH_MIN = PROCESSING_LENGTH_MIN;
        this.DATA_TYPE = DATA_TYPE;
        this.DOC_TYPE = DOC_TYPE;

    }

    /**
     * Basic constructor
     *
     * 기본값으로 초기화를 진행한다.
     */
    public Config() {
        this.MIN_SENTENCE_LENGTH = 5;
        this.PROCESSING_LENGTH_MAX = 3;
        this.PROCESSING_LENGTH_MIN = 2;
        this.DATA_TYPE = "text";
        this.DOC_TYPE = "basic";

    }
}
