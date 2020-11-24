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

import java.util.List;

/**
 * 구분 조건
 *
 *
 * @author wjrmffldrhrl
 */
public class  SplitCondition {

    private final String value;
    private final List<Validation> validations;
    private final char usePublicValidation;
    private final char splitPosition;
    private boolean isPattern = false;

    /**
     * Constructor
     * @param value 구분 조건 값
     * @param validations 구분 조건에 해당하는 유효성
     * @param usePublicValidation 공통 유효성 사용 여부 'Y' or 'N'
     * @param splitPosition 구분 위치 'B' or 'F'
     */
    public SplitCondition(String value, List<Validation> validations, char usePublicValidation, char splitPosition) {
        this.value = value;
        this.validations = validations;
        this.usePublicValidation = usePublicValidation;
        this.splitPosition = splitPosition;
    }

    public SplitCondition(String value, List<Validation> validations, char usePublicValidation, char splitPosition, boolean isPattern) {
        this(value, validations, usePublicValidation, splitPosition);
        this.isPattern = isPattern;
    }

    public String getValue() { return value; }
    public List<Validation> getValidations() { return validations; }
    public char getUsePublicValidation() { return usePublicValidation; }
    public char getSplitPosition() { return splitPosition; }
    public boolean isPattern() { return isPattern; }

}
