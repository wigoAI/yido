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

package org.moara.yido.tokenizer;

/**
 * 토크나이져
 * @author macle
 */
public interface Tokenizer {

    /**
     * Tokenizer id 얻기
     * Tokenizer 를 알 수 있는 유니크한 아이디
     * @return Tokenizer id
     */
    String getId();



    /**
     * 토큰 결과 얻기
     * @param text 토큰을 나누기 위한 text
     * @return 토큰 [] (순서대로)
     */
    Token [] getTokens(String text);

    /**
     * 메타 데이터 업데이트
     */
    void updateMetaData();

}
