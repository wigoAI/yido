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
package org.moara.splitter.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.moara.splitter.utils.RoleProperty;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;
import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 구분 조건 관리자
 * @author wjrmffldrhrl
 */
public class SplitConditionManager {
    protected static final String conditionPath = "condition/";
    public static List<SplitCondition> getSplitConditions(List<String> splitConditionRoleNames) {
        ArrayList<String> arrayList = (ArrayList<String>) splitConditionRoleNames;

        return getSplitConditions( arrayList.toArray(new String[arrayList.size()]));
    }

    public static List<SplitCondition> getSplitConditions(String[] splitConditionRoleNames) {
        List<SplitCondition> splitConditions = new ArrayList<>();


        for (String splitConditionRoleName : splitConditionRoleNames) {
            splitConditions.addAll(getSplitConditionsByRole(splitConditionRoleName));
        }

        return splitConditions;

    }

    private static List<SplitCondition> getSplitConditionsByRole(String splitConditionRoleName) {
        List<SplitCondition> splitConditions = new ArrayList<>();
        JsonObject conditionRoleJson = FileManager.getJsonObjectByFile(conditionPath + splitConditionRoleName + ".json");
        List<Validation> validations = getValidations(conditionRoleJson);

        String conditionValueName = conditionRoleJson.get("value").getAsString();
        Collection<String> conditionValues = FileManager.readFile("string_group/" + conditionValueName + ".dic");

        for (String conditionValue : conditionValues) {
            SplitCondition.Builder splitConditionBuilder = new SplitCondition
                    .Builder(conditionValue, getRoleProperty(conditionRoleJson))
                    .validations(validations);
            if (conditionValueName.startsWith("regx_")) {
                splitConditionBuilder.isPattern(true);
            }

            splitConditions.add(splitConditionBuilder.build());
        }

        return splitConditions;
    }

    private static List<Validation> getValidations(JsonObject conditionRoleJson) {
        List<String> validationRoleNames = new ArrayList<>();
        JsonArray validationJsonArray = conditionRoleJson.getAsJsonArray("validations");
        for (int i = 0; i < validationJsonArray.size(); i++) {
            validationRoleNames.add(validationJsonArray.get(i).getAsString());
        }

        List<Validation> validations = new ArrayList<>();
        for (String validationRoleName : validationRoleNames) {
            validations.addAll(ValidationManager.getValidations(validationRoleName));
        }

        if (getRoleProperty(conditionRoleJson).getFlag() == 'Y') {
            validations.addAll(CommonValidationManager.getAllPublicValidations());
        }

        return validations;
    }



    private static RoleProperty getRoleProperty(JsonObject conditionRoleJson) {
        return new RoleProperty(conditionRoleJson.get("use_public_validation").getAsString().charAt(0),
                conditionRoleJson.get("split_position").getAsString().charAt(0));
    }


}
