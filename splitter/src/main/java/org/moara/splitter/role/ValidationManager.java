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
 * 유효성 룰 관리자
 * TODO 1. Check invalid parameter
 */
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
