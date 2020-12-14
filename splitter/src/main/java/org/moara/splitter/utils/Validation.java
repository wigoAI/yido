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
package org.moara.splitter.utils;

/**
 * 유효성
 * @author wjrmffldrhrl
 */
public class Validation extends RuleProperty {
    private final String value;

    /**
     * 유효성의 모든 정보를 초기화 하는 생성자
     * @param value 유효성 값
     * @param matchFlag 유효성 일치 여부
     * @param comparePosition 유효성 비교 위치
     */
    public Validation(String value, char matchFlag, char comparePosition) {
        super(matchFlag, comparePosition);
        this.value = value;
    }

    /**
     * 이미 생성된 룰 속성을 통해 초기화
     * @param value 유효성 값
     * @param ruleProperty 룰 속성
     */
    public Validation(String value, RuleProperty ruleProperty) {
        this(value, ruleProperty.getFlag(), ruleProperty.getPosition());
    }

    public String getValue() { return value; }
    public char getMatchFlag() { return flag; }
    public char getComparePosition() { return position; }
}
