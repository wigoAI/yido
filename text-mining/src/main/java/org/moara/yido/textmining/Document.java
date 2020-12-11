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
     *
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
