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

import org.moara.splitter.utils.FileReader;
import org.moara.splitter.utils.RuleProperty;
import org.moara.splitter.utils.Validation;
import java.util.ArrayList;
import java.util.List;

/**
 * 유효성 룰 관리자
 *
 * @author wjrmffldrhrl
 */
public class ValidationManager {
    protected static String stringGroupPath = "string_group/";

    /**
     * 유효성 반환
     *
     * @param dicName 유효성 생성에 사용되는 사전 데이터 이름
     * @return 유효성 리스트
     */
    public static List<Validation> getValidations(String dicName)  {

        boolean flag;

        if (dicName.charAt(0) == 'Y') {
            flag = true;
        } else if (dicName.charAt(0) == 'N') {
            flag = false;
        } else {
            throw new IllegalArgumentException("Illegal rule data type : " + dicName);
        }

        RuleProperty ruleProperty = new RuleProperty(flag, dicName.charAt(1));

        List<Validation> validations = new ArrayList<>();
        if (dicName.startsWith("SG_", 2)) {
            for (String ruleData : FileReader.readDictionary(stringGroupPath + dicName.substring(5))) {
                if (ruleData.contains("\\s")) {
                    ruleData = ruleData.replace("\\s", " ");
                }
                validations.add(new Validation(ruleData, ruleProperty));
            }

        } else {
            throw new IllegalArgumentException("Illegal rule data type : " + dicName);
        }

        return validations;

    }

}
