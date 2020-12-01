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

import java.util.ArrayList;
import java.util.List;

/**
 * 구분 조건
 *
 *
 * @author wjrmffldrhrl
 */
public class  SplitCondition extends RoleProperty{

    private final String value;
    private final List<Validation> validations;
    private final boolean isPattern;

    public static class Builder {
        private final String value;
        private final char usePublicValidation;
        private final char splitPosition;

        private List<Validation> validations = new ArrayList<>();
        private boolean isPattern = false;

        public Builder(String value, char usePublicValidation, char splitPosition) {
            this.value = value;
            this.usePublicValidation = usePublicValidation;
            this.splitPosition = splitPosition;
        }

        public Builder(String value, RoleProperty roleProperty) {
            this(value, roleProperty.getFlag(), roleProperty.getPosition());
        }

        public Builder validations(List<Validation> val) {
            validations = val;
            return this;
        }

        public Builder isPattern(boolean val) {
            isPattern = val;
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
    public char getUsePublicValidation() { return flag; }
    public char getSplitPosition() { return position; }
    public boolean isPattern() { return isPattern; }


}
