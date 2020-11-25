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
package org.moara.splitter.role;

import org.moara.splitter.utils.RoleProperty;

/**
 * 유효성
 * @author wjrmffldrhrl
 */
public class Validation {

    private final String value;
    private final char matchFlag;
    private final char comparePosition;

    /**
     * Constructor
     * @param value 유효성 값
     * @param matchFlag 유효성 일치 여부 'Y' or 'N'
     * @param comparePosition 유효성 비교 위치 'B' or 'F'
     */
    public Validation(String value, char matchFlag, char comparePosition) {
        this.value = value;
        this.matchFlag = matchFlag;
        this.comparePosition = comparePosition;
    }

    /**
     * Constructor
     * @param value 유효성 값
     * @param roleProperty 룰 속성값
     */
    public Validation(String value, RoleProperty roleProperty) {
        this(value, roleProperty.getFlag(), roleProperty.getPosition());
    }

    public String getValue() { return value; }
    public char getMatchFlag() { return matchFlag; }
    public char getComparePosition() { return comparePosition; }
}
