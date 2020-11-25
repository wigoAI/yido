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

/**
 * 유효성 룰 관리자
 *
 */
public class ValidationManager {
    protected static String rolePath = "/string_group/validation/";

    /**
     * 유효성 반환
     *
     * @param roleName 유효성 생성에 사용되는 룰 이름
     * @return 유효성 리스트
     */
    public static List<Validation> getValidations(String roleName)  {
        RoleProperty roleProperty = new RoleProperty(roleName);

        checkRoleName(roleName);

        List<Validation> validations = new ArrayList<>();
        for (String roleData : FileManager.readFile(rolePath + roleName + ".role")) {
            validations.add(new Validation(roleData, roleProperty));
        }
        return validations;

    }

    public static void checkRoleName(String roleName) {
        if (!isValid(roleName)) {
            throw new RuntimeException("Invalid role name : " + roleName);
        }
    }

    private static boolean isValid(String roleName) {
        return roleName.startsWith("PV_") || roleName.startsWith("V_");
    }
}
