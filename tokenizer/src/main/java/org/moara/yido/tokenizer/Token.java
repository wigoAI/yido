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
 * token 기본형
 * @author macle
 */
public interface Token {


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
     * @return end index + 1  substring(begin,end)
     */
    int getEnd();


}
