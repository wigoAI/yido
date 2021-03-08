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
package org.moara.yido.splitter.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import org.moara.yido.splitter.utils.FileReader;
import org.moara.yido.splitter.utils.RuleProperty;
import org.moara.yido.splitter.utils.SplitCondition;
import org.moara.yido.splitter.utils.Validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 구분 조건 관리자
 * @author wjrmffldrhrl
 */
public class SplitConditionManager {
    protected static final String CONDITION_PATH = "condition/";

    /**
     * 문장 구분시에 사용할 구분 조건이 명시된 json 파일 이름을 넘겨받는다.
     * 해당 json 파일에서 정보를 가져와 구분 조건 리스트를 반환한다.
     * @param splitConditionRuleNames json file names
     * @return 구분 조건 리스트
     */
    public static List<SplitCondition> getSplitConditions(List<String> splitConditionRuleNames) {
        ArrayList<String> arrayList = (ArrayList<String>) splitConditionRuleNames;

        return getSplitConditions( arrayList.toArray(new String[0]));
    }

    /**
     * 조건 rule로부터 조건값을 가져옴
     * rule name에서 파일명을 추출하여 해당 json 파일에 접근
     *
     * @param splitConditionRuleNames JSON file name
     * @return {@code List<SplitCondition>}
     */
    public static List<SplitCondition> getSplitConditions(String[] splitConditionRuleNames) {
        List<SplitCondition> splitConditions = new ArrayList<>();

        for (String splitConditionRuleName : splitConditionRuleNames) {
            splitConditions.addAll(getSplitConditionsByRule(splitConditionRuleName));
        }

        return splitConditions;

    }

    private static List<SplitCondition> getSplitConditionsByRule(String splitConditionRuleName) {
        List<SplitCondition> splitConditions = new ArrayList<>();
        JsonObject conditionRuleJson = FileReader.getJsonObjectByFile(CONDITION_PATH + splitConditionRuleName);

        checkConditionJsonValidation(conditionRuleJson);
        List<Validation> validations = getValidations(conditionRuleJson);

        String conditionValueName = conditionRuleJson.get("value").getAsString();
        Collection<String> conditionValues = FileReader.readDictionary("string_group/" + conditionValueName);

        for (String conditionValue : conditionValues) {

            // Don't use empty condition
            if (conditionValue.length() < 1) { continue; }

            SplitCondition.Builder splitConditionBuilder = new SplitCondition
                    .Builder(conditionValue, getRuleProperty(conditionRuleJson).getPosition())
                    .validations(validations);
            if (conditionValueName.startsWith("regx_")) {
                splitConditionBuilder.isPattern();
            }

            splitConditions.add(splitConditionBuilder.build());
        }

        return splitConditions;
    }

    private static List<Validation> getValidations(JsonObject conditionRuleJson) {
        List<String> validationRuleNames = new ArrayList<>();
        JsonArray validationJsonArray = conditionRuleJson.getAsJsonArray("validations");
        for (int i = 0; i < validationJsonArray.size(); i++) {
            validationRuleNames.add(validationJsonArray.get(i).getAsString());
        }

        List<Validation> validations = new ArrayList<>();
        for (String validationRuleName : validationRuleNames) {
            validations.addAll(ValidationManager.getValidations(validationRuleName));
        }

        if (getRuleProperty(conditionRuleJson).getFlag()) {
            validations.addAll(CommonValidationManager.getAllPublicValidations());
        }

        return validations;
    }

    private static RuleProperty getRuleProperty(JsonObject conditionRuleJson) {
        return new RuleProperty(conditionRuleJson.get("use_public_validation").getAsBoolean(),
                conditionRuleJson.get("split_position").getAsString().charAt(0));
    }


    private static void checkConditionJsonValidation(JsonObject conditionRuleJson) {
        System.out.println(conditionRuleJson.get("use_public_validation").getAsString());
        if (!conditionRuleJson.isJsonObject()
                || conditionRuleJson.get("id") == null
                || conditionRuleJson.get("split_position") == null
                || conditionRuleJson.get("value") == null
                || !(conditionRuleJson.get("use_public_validation").getAsString().equals("true") ||
                    conditionRuleJson.get("use_public_validation").getAsString().equals("false"))) {

            throw new JsonIOException("Invalid condition json");
        }

    }



}
