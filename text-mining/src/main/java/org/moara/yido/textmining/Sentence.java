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
import org.moara.yido.tokenizer.word.WordToken;

import java.util.HashMap;
import java.util.Map;

/**
 * 문장
 * @author macle
 */
public abstract class Sentence implements BeginEnd {


    protected WordToken[] tokens;

    protected int begin;
    protected int end;

    //문장의 그밖의 속성들
    //STT 의 텍스트일경우 값이 생김
    protected Map<String, Object> dataMap;

    
    /**
     * 생성자
     * @param begin 시작위치
     * @param end 끝위치
     */
    public Sentence(int begin, int end){
        this.begin = begin;
        this.end = end;
    }

    /**
     * 문장 내용 얻기
     * @return 문장내용
     */
    public abstract String getContents();

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

    /**
     * 데이터 얻기
     * @param dataKey 데이터키
     * @return 데이터
     */
    public Object getData(String dataKey) {
        if(dataMap == null){
            return null;
        }
        return dataMap.get(dataKey);
    }

    /**
     * 데이터 설정
     * @param dataKey 데이터키
     * @param data 데이터
     */
    public void setDataMap(String dataKey, Object data) {
        if(dataMap == null){
            dataMap = new HashMap<>();
        }
        dataMap.put(dataKey, data);
    }
}
