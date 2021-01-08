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
package org.moara.ner.person;


import org.moara.ner.NamedEntity;

/**
 * 사람 개체명
 *
 * @author wjrmffldrhrl
 */
public class PersonEntity implements NamedEntity {

    private final String text;
    private final String type;
    private final int begin;
    private final int end;

    public PersonEntity(String text, String subType, int begin, int end) {
        this.text = text;
        this.type = "PS_" + subType;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof PersonEntity)) {
            return false;
        }

        PersonEntity personEntity = (PersonEntity) o;
        return this.text.equals((personEntity.getText())) && this.getType().equals(personEntity.getType())
                && this.getBegin() == personEntity.getBegin() && this.getEnd() == personEntity.getEnd();

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
}
