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

import java.util.HashMap;
import java.util.Map;

/**
 * 분석원문
 * @author macle
 */
public class Document {

    private String id;

    //제목
    String title;
    //내용
    String contents;

    //문서유형
    String type;

    //시간
    private long time;

    //기타정보 작성자, 문서언어 등
    private Map<String, Object> dataMap;

    /**
     * 문서 아이디 얻기
     * @return 문서 아이디
     */
    public String getId() {
        return id;
    }

    /**
     * 문서 아이디 설정
     * @param id 문서아이디
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 제목 얻기
     * @return 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 제목 설정
     * @param title 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 내용 얻기
     * @return 내용
     */
    public String getContents() {
        return contents;
    }

    /**
     * 내용 설정
     * @param contents 내용
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * 문서유형 얻기
     * @return 문서유형얻기
     */
    public String getType() {
        return type;
    }

    /**
     * 문서 유형 설정
     * @param type 문서 유형
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 시간 얻기
     * @return 시간 unix time
     */
    public long getTime() {
        return time;
    }

    /**
     * 시간 설정
     * @param time 시간 unix time
     */
    public void setTime(long time) {
        this.time = time;
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
