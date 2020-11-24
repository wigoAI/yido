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

package org.moara.yido.tokenizer.word;

import org.moara.yido.tokenizer.Token;

/**
 * 단어토큰
 * @author macle
 */
public class WordToken implements Token {

    //아이디
    String id;
    //단어
    String text;
    //품사 //품사형태 재정의 VV+EF (다녀요) 에 형태에 떨구어 지는 방식...
    String partOfSpeech;

    int begin;
    int end;

    /**
     * 생성자
     * @param id 단어 아이디
     * @param text 단어 텍스트
     * @param partOfSpeech 품사
     * @param begin 시작 위치 (index)
     * @param end 끝 위치 (index + 1) substring(begin, end)
     */
    public WordToken(
            String id
            , String text
            , String partOfSpeech
            , int begin
            , int end
    ){
        this.id = id;
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.begin =begin;
        this.end = end;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getBegin() {
        return begin;
    }

    @Override
    public int getEnd() {
        return end;
    }

}
