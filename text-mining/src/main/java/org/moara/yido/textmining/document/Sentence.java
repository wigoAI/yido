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

package org.moara.yido.textmining.document;


import com.seomse.commons.data.BeginEnd;
import org.moara.yido.tokenizer.word.WordToken;

/**
 * 문장
 * @author macle
 */
public class Sentence implements BeginEnd {

    //문장내용
    Contents contents;

    WordToken[] tokens;

    private final int begin;
    private final int end;

    /**
     * 생성자
     * @param contents 내용
     * @param begin 시작위치
     * @param end 끝위치
     */
    public Sentence(Contents contents, int begin, int end){
        this.contents = contents;
        this.begin = begin;
        this.end = end;
    }


    /**
     * 문장 내용 얻기
     * @return 문장내용
     */
    public String getContents() {
        return contents.getText().substring(begin, end);
    }

    /**
     * 문장을 구성하는 토큰들 얻기
     * begin end는 문장 단위다
     * 문서단위를 얻으려면 문장의 시작 위치를 더해서 얻어야 한다
     * @return 문장을 구성하는 토큰
     */
    public WordToken[] getTokens() {
        return tokens;
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
