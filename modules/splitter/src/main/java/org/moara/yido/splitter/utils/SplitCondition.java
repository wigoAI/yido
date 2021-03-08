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
package org.moara.yido.splitter.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 구분 조건
 * 해당 조건에 대한 유효성도 포함되어 있다.
 * @author wjrmffldrhrl
 */
public class SplitCondition extends RuleProperty {

    private final String value;
    private final List<Validation> validations;
    private final boolean isPattern;

    /**
     * 구분 조건 빌더
     * 구분 조건에 사용되는 값들 중
     * 선택적으로 사용할 수 있는 값들이 많기 때문에 적용함
     */
    public static class Builder {
        private final String value;
        private final char splitPosition;

        private List<Validation> validations = new ArrayList<>();
        private boolean usePublicValidation = false;
        private boolean isPattern = false;

        /**
         *
         * @param value 구분 조건 값
         * @param splitPosition 구분 위치
         */
        public Builder(String value, char splitPosition) {
            this.value = value;
            this.splitPosition = splitPosition;
        }

        /**
         *
         * @param val 해당 조건 유효성
         * @return Builder
         */
        public Builder validations(List<Validation> val) {
            validations = val;
            return this;
        }

        /**
         * 조건에서의 공통 유효성 사용 여부
         * @return Builder
         */
        public Builder usePublicValidation() {
            usePublicValidation = true;
            return this;
        }

        /**
         * 해당 조건이 패턴 조건일 시 호출
         * @return Builder
         */
        public Builder isPattern() {
            isPattern = true;
            return this;
        }

        public SplitCondition build() {
            return new SplitCondition(this);
        }
    }

    private SplitCondition(Builder builder) {
        super(builder.usePublicValidation, builder.splitPosition);
        this.value = builder.value;
        this.validations = builder.validations;
        this.isPattern = builder.isPattern;
    }


    public String getValue() { return value; }
    public List<Validation> getValidations() { return validations; }
    public boolean getUsePublicValidation() { return flag; }
    public char getSplitPosition() { return position; }
    public boolean isPattern() { return isPattern; }

    @Override
    public String toString() {
        return "SplitCondition{" +
                "flag=" + flag +
                ", position=" + position +
                ", value='" + value + '\'' +
                ", isPattern=" + isPattern +
                '}';
    }
}
