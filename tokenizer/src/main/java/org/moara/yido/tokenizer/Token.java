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
 * @author macle
 */
public interface Token {

    /**
     * token id
     * 단어 같은 경우에는->위고/NNG
     * 위오 같은 형태처럼 특정 아이디를 지정하여 쓰기 위한 기능
     * 초기 구상단계이므로 바뀔 수 있음
     * @return token id
     */
    String getId();


    /**
     * 토근 텍스트
     * @return token text
     */
    String getText();

    /**
     * 시작위치
     * @return begin index
     */
    int getBegin();

    /**
     * 끝위치
     * @return end index + 1 -> substring(begin,end)
     */
    int getEnd();


}
