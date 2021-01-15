/*
 * Copyright (C) 2021 Wigo Inc.
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
package org.moara.ner;

/**
 * 개체명 구현체
 *
 * @author wjrmffldrhrl
 */
public class NamedEntityImpl implements NamedEntity {
    protected final String text;
    protected final String type;
    protected final int begin;
    protected final int end;

    /**
     * 개체명 생성자
     * @param text 개체명 내용
     * @param type 개체 범주
     * @param begin 개체가 시작하는 위치
     * @param end 개체가 끝나는 위치
     */
    public NamedEntityImpl(String text, String type, int begin, int end) {
        this.text = text;
        this.type = type;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof NamedEntityImpl)) {
            return false;
        }

        NamedEntityImpl namedEntity = (NamedEntityImpl) o;
        return this.text.equals((namedEntity.getText())) && this.getType().equals(namedEntity.getType())
                && this.getBegin() == namedEntity.getBegin() && this.getEnd() == namedEntity.getEnd();

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getBegin() {
        return begin;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }



    @Override
    public int hashCode() {
        return toString().hashCode();
    }


}
