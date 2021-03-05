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

import org.moara.yido.textmining.Sentence;

/**
 * @author macle
 */
public class TextSentence extends Sentence {

    private final String text;

    /**
     * 생성자
     * @param text 텍스트
     * @param begin 시작위치
     * @param end   끝위치
     */
    public TextSentence(String text, int begin, int end) {
        super(begin, end);
        this.text = text;
    }

    @Override
    public String getContents() {
        return text;
    }
}
