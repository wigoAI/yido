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
 */
public class SplitConditionManager {
    protected static final String rolePath = "/string_group/split_condition/";


    public static List<SplitCondition> getSplitConditions(String splitConditionRoleName, String[] validationRoleNames) {
        String[] splitConditionRoleNames = {splitConditionRoleName};

        return getSplitConditions(splitConditionRoleNames, validationRoleNames);
    }

    public static List<SplitCondition> getSplitConditions(String[] splitConditionRoleNames, String validationRoleName) {
        String[] validationRoleNames = {validationRoleName};

        return getSplitConditions(splitConditionRoleNames, validationRoleNames);
    }

    public static List<SplitCondition> getSplitConditions(String splitConditionRoleName, String validationRoleName) {
        String[] splitConditionRoleNames = {splitConditionRoleName};
        String[] validationRoleNames = {validationRoleName};

        return getSplitConditions(splitConditionRoleNames, validationRoleNames);
    }


    /**
     * 구분 조건 반환
     * @param splitConditionRoleNames 구분 조건에 사용되는 룰 데이터 이름
     * @param validationRoleNames 유효성에 사용되는 룰 이름
     * @return 구분 조건 리스트
     */
    public static List<SplitCondition> getSplitConditions(String[] splitConditionRoleNames, String[] validationRoleNames) {

        String[] roleInfoArray = splitConditionRoleNames[0].split("_");
        String SplitConditionType = roleInfoArray[0];
        char usePublicValidation = roleInfoArray[1].charAt(0);
        char splitPosition = roleInfoArray[2].charAt(0);

        for (String splitConditionRoleName : splitConditionRoleNames) {

            String[] tmpRoleInfoArray = splitConditionRoleName.split("_");
            String tmpSplitConditionType = tmpRoleInfoArray[0];
            char tmpUsePublicValidation = tmpRoleInfoArray[1].charAt(0);
            char tmpSplitPosition = tmpRoleInfoArray[2].charAt(0);

            if (usePublicValidation != tmpUsePublicValidation || splitPosition != tmpSplitPosition) {
                throw new RuntimeException("SplitConditionRoleNames must have same configs");
            }

            checkRoleName(tmpSplitConditionType, tmpUsePublicValidation, tmpSplitPosition);
        }


        List<Validation> validations = new ArrayList<>();
        for (String validationRoleName : validationRoleNames) {
            validations.addAll(ValidationManager.getValidations(validationRoleName));
        }
        if (usePublicValidation == 'Y') { validations.addAll(PublicValidationManager.getAllPublicValidations()); }


        Collection<String> roleDataList = new ArrayList<>();
        for (String splitConditionRoleName : splitConditionRoleNames) {
            roleDataList.addAll(FileManager.readFile(rolePath + splitConditionRoleName + ".role"));
        }

        List<SplitCondition> splitConditions = new ArrayList<>();
        for (String roleData : roleDataList) {
            SplitCondition splitCondition;
            if (SplitConditionType.startsWith("R")) {
                splitCondition = new SplitCondition(roleData, validations, usePublicValidation, splitPosition, true);
            } else {
                splitCondition = new SplitCondition(roleData, validations, usePublicValidation, splitPosition);
            }
            splitConditions.add(splitCondition);
        }


        return splitConditions;
    }

    private static void checkRoleName(String splitConditionType, char usePublicValidation, char splitPosition) {
        if (!(splitConditionType.equals("SP") || splitConditionType.equals("RSP")) ||
                !(usePublicValidation == 'N' || usePublicValidation == 'Y') ||
                !(splitPosition == 'B' || splitPosition == 'F')) {

            throw new RuntimeException("Invalid role name");
        }
    }
}
