package org.moara.splitter.role;

import org.moara.splitter.utils.file.FileManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ValidationManager {
    protected static String rolePath = "/string_group/validation/";

    public static List<Validation> getValidations(String roleName)  {
        String[] roleInfoArray = roleName.split("_");
        String validationType = roleInfoArray[0];
        char matchFlag = roleInfoArray[1].charAt(0);
        char comparePosition = roleInfoArray[2].charAt(0);

        if (!(validationType.equals("V") || validationType.equals("PV")) ||
                !(matchFlag == 'N' || matchFlag == 'Y') ||
                !(comparePosition == 'B' || comparePosition == 'F')) {
            throw new RuntimeException("Invalid role name");
        }

        List<Validation> validations = new ArrayList<>();
        Collection<String> roleDataList = FileManager.readFile(rolePath + roleName + ".role");

        for (String roleData : roleDataList) {
            validations.add(new Validation(roleData, matchFlag, comparePosition));
        }
        return validations;

    }
}
