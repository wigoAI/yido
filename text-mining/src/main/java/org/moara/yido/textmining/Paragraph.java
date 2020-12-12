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

package org.moara.yido.textmining;

import com.seomse.commons.data.BeginEnd;

/**
 * 문단
 * @author macle
 */
public class Paragraph implements BeginEnd {

    DocumentMining documentMining;

    final int begin;
    final int end;

    //문단에 속하는 문장구성 정보
    //단 문단에 속해 있지만 문서 기준의 인덱스를 가진다.

    /**
     * 생성자
     * @param documentMining 내용
     * @param begin 시작위치
     * @param end 끝위치
     */
    public Paragraph(DocumentMining documentMining, int begin, int end){
        this.documentMining = documentMining;
        this.begin = begin;
        this.end = end;
    }

    /**
     * 문단 내용 얻기
     * @return 문장내용
     */
    public String getContents() {
        return documentMining.document.contents.substring(begin, end);
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
