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

/**
 * 복합어 토큰
 * @author macle
 */
public class CompoundToken extends WordToken{


    private final String [] wordIds;

    /**
     * 생성자
     *
     * @param id           단어 아이디
     * @param text         단어 텍스트
     * @param partOfSpeech 품사
     * @param begin        시작 위치 (index)
     * @param end          끝 위치 (index + 1) substring(begin, end)
     * @param wordIds      복합어 일때 구성 단어 아이디
     */
    public CompoundToken(String id, String text, String partOfSpeech, int begin, int end
        , String [] wordIds
    
    ) {
        super(id, text, partOfSpeech, begin, end);
        this.wordIds = wordIds;
    }

    /**
     * 구성 단어 아이디 배열 얻기
     * 복합어 일댸
     * @return 구성단어 아이디 배열
     */
    public String[] getWordIds() {
        return wordIds;
    }
}
