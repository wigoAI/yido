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

import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO 1. 이전에 사용하던 룰 메모리 관련 기능들 추가하기
 *          - 메모리 룰 추가
 *          - 파일 룰 추가
 *      2. invalid parameter 처리
 */
public class SplitConditionManager {
    protected static final String rolePath = "/string_group/split_condition/";

    public static List<SplitCondition> getSplitConditions(String roleName, String[] validationList) {
        String[] roleInfoArray = roleName.split("_");
        String splitConditionType = roleInfoArray[0];
        char usePublicValidation = roleInfoArray[1].charAt(0);
        char splitPosition = roleInfoArray[2].charAt(0);

        if (!(splitConditionType.equals("SP") || splitConditionType.equals("PSP")) ||
                !(usePublicValidation == 'N' || usePublicValidation == 'Y') ||
                !(splitPosition == 'B' || splitPosition == 'F')) {
            throw new RuntimeException("Invalid role name");
        }

        Collection<String> roleDataList = FileManager.readFile(rolePath + roleName + ".role");
        List<SplitCondition> splitConditions = new ArrayList<>();
        List<Validation> validations = new ArrayList<>();

        for (String validationRoleName : validationList) {
            validations.addAll(ValidationManager.getValidations(validationRoleName));
        }

        if (usePublicValidation == 'Y') { validations.addAll(PublicValidationManager.getAllPublicValidations()); }

        for (String roleData : roleDataList) {
            splitConditions.add(new SplitCondition(roleData, validations, usePublicValidation, splitPosition));
        }


        return splitConditions;
    }
}
