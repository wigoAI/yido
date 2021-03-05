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

package org.moara.yido.textmining.sentence;

import org.moara.yido.textmining.Contents;
import org.moara.yido.textmining.Sentence;

/**
 * @author macle
 */
public class ContentsSentence extends Sentence {
    //문장내용
    private Contents contents;

    /**
     * 생성자
     * @param contents 내용
     * @param begin 시작위치
     * @param end 끝위치
     */
    public ContentsSentence(Contents contents, int begin, int end){
        super(begin, end);
        this.contents = contents;
        this.begin = begin;
        this.end = end;
    }

    /**
     * contents 변경
     * @param contents 내용
     */
    public void changeContents(Contents contents) {
        this.contents = contents;
    }

    @Override
    public String getContents() {
        return contents.getText().substring(begin, end);
    }

}
