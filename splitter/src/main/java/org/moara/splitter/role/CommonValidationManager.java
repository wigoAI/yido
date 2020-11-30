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

import java.util.ArrayList;
import java.util.List;

/**
 * 공통 유효성 룰 관리자
 *
 * @author wjrmffldrhrl
 */
public class PublicValidationManager extends ValidationManager {

    private static final String[] roles = {"PV_N_B", "PV_N_F", "PV_Y_B", "PV_Y_F"};
    private static final List<Validation> publicValidations = initAllPublicValidations();

    /**
     * 생성해둔 공용 유효성 반환
     * @return public validations
     */
    public static List<Validation> getAllPublicValidations() {
        return publicValidations;
    }

    private static List<Validation> initAllPublicValidations() {
        List<Validation> validations = new ArrayList<>();

        for (String role : roles) {
            validations.addAll(getValidations(role));
        }

        return validations;
    }
}
