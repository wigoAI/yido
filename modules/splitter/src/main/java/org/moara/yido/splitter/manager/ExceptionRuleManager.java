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
import com.google.gson.JsonObject;
import org.moara.yido.splitter.utils.FileReader;

import java.util.regex.Pattern;

/**
 * 예외영역 룰 관리자
 * @author wjrmffldrhrl
 */
public class ExceptionRuleManager {
    protected static final String RULE_PATH = "exception/";
    private static final String DIFFERENT_SIDE_BRACKET_RULE_NAME = "bracket_exception";
    private static final String SAME_SIDE_BRACKET_RULE_NAME = "same_bracket_exception";

    /**
     * 괄호의 좌우 방향이 다른 괄호에 대한 패턴 찾기
     *
     * @return Pattern
     */
    public static Pattern getDifferentSideBracketPattern() {
        JsonObject metaJson = FileReader.getJsonObjectByFile(RULE_PATH + DIFFERENT_SIDE_BRACKET_RULE_NAME);
        JsonArray dataArray = metaJson.get("value").getAsJsonArray();

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder centerLeft = new StringBuilder("[^]*");
        StringBuilder centerRight = new StringBuilder("[^]*");
        StringBuilder right = new StringBuilder("[]+");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject data  = dataArray.get(i).getAsJsonObject();
            String front = data.get("front").getAsString();
            String back = data.get("back").getAsString();

            left.insert(1, "\\" +front);
            centerRight.insert(2, "\\" + front);

            right.insert(1, "\\" + back);
            centerLeft.insert(2, "\\" + back);

        }

        String pattern = left.append(centerLeft).append(centerRight).append(right).toString();
        return  Pattern.compile(pattern);
    }

    /**
     * 괄호의 좌우 방향이 같은 괄호에 대한 패턴 찾기
     * @return Pattern
     */
    public static Pattern getSameSideBracketPattern() {
        JsonObject metaJson = FileReader.getJsonObjectByFile(RULE_PATH + SAME_SIDE_BRACKET_RULE_NAME);
        JsonArray dataArray = metaJson.get("value").getAsJsonArray();

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder center = new StringBuilder("[^]+");
        StringBuilder right = new StringBuilder("[]");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject data  = dataArray.get(i).getAsJsonObject();
            String bracket = data.get("bracket").getAsString();

            left.insert(1, "\\" +bracket);
            center.insert(2, "\\" + bracket);
            right.insert(1, "\\" + bracket);

        }

        String pattern = left.append(center).append(right).toString();
        return  Pattern.compile(pattern);
    }

}
