package org.moara.splitter.role;

import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        List<SplitCondition> splitConditions = new ArrayList<>();
        Collection<String> roleDataList = FileManager.readFile(rolePath + roleName + ".role");
        List<Validation> validations = new ArrayList<>();

        for (String validationRoleName : validationList) {
            validations.addAll(ValidationManager.getValidations(validationRoleName));
        }

        for (String roleData : roleDataList) {
            splitConditions.add(new SplitCondition(roleData, validations, usePublicValidation, splitPosition));
        }


        return splitConditions;
    }
}
