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
package org.moara.splitter;

import org.moara.splitter.utils.Area;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;

import java.util.List;

/**
 * 구분기 추상체
 *
 * @author wjrmffldrhrl
 */
public interface Splitter {

    /**
     * 입력된 데이터를 구분 단위에 맞게 반환
     * @param text String
     * @return Area Array
     */
    Area[] split(String text);

    /**
     * 구분 조건 추가
     * @param additionalSplitCondition 추가할 구분 조건
     */
    void addSplitConditions(List<SplitCondition> additionalSplitCondition);

    /**
     * 구분 조건 제거
     * @param unnecessarySplitCondition 제거할 구분 조건
     */
    void deleteSplitConditions(List<SplitCondition> unnecessarySplitCondition);

    /**
     * 조건 유효성 추가
     * @param validations 추가할 조건 유효성
     */
    void addValidation(List<Validation> validations);

    /**
     * 조건 유효성 제거
     * @param validations 제거할 조건 유효성
     */
    void deleteValidation(List<Validation> validations);
}
