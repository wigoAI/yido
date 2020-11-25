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
import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * TODO 1. role type에 대한 검사
     * @param splitConditionRoleNames 구분 조건에 사용되는 룰 데이터 이름
     * @param validationRoleNames 유효성에 사용되는 룰 이름
     * @return 구분 조건 리스트
     */
    public static List<SplitCondition> getSplitConditions(String[] splitConditionRoleNames, String[] validationRoleNames) {

        RoleProperty roleProperty = new RoleProperty(splitConditionRoleNames[0]);

        for (String splitConditionRoleName : splitConditionRoleNames) {

            RoleProperty tmpRoleProperty = new RoleProperty(splitConditionRoleName);

            checkRoleName(splitConditionRoleName);

            if (roleProperty.getFlag() != tmpRoleProperty.getFlag() ||
                    roleProperty.getPosition() != roleProperty.getPosition()) {
                throw new RuntimeException("Split Condition Roles must have same property");
            }

        }


        List<Validation> validations = new ArrayList<>();
        for (String validationRoleName : validationRoleNames) {
            validations.addAll(ValidationManager.getValidations(validationRoleName));
        }
        if (roleProperty.getFlag() == 'Y') { validations.addAll(PublicValidationManager.getAllPublicValidations()); }


        Collection<String> roleDataList = new ArrayList<>();
        for (String splitConditionRoleName : splitConditionRoleNames) {
            roleDataList.addAll(FileManager.readFile(rolePath + splitConditionRoleName + ".role"));
        }

        List<SplitCondition> splitConditions = new ArrayList<>();
        for (String roleData : roleDataList) {
            SplitCondition splitCondition;
            if (roleProperty.getType().startsWith("R")) {
                splitCondition = new SplitCondition(roleData, validations, roleProperty, true);
            } else {
                splitCondition = new SplitCondition(roleData, validations, roleProperty);
            }
            splitConditions.add(splitCondition);
        }


        return splitConditions;
    }

    public static void checkRoleName(String splitConditionRoleName) {
        if (!isValid(splitConditionRoleName)) {
            throw new RuntimeException("Invalid role name : " + splitConditionRoleName);
        }
    }

    private static boolean isValid(String roleName) {
        return roleName.startsWith("SP_") || roleName.startsWith("RSP_");
    }
}
