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
package org.moara.yido.processor.regularExpression;

import com.github.wjrmffldrhrl.Area;
import org.moara.yido.role.PublicRoleManager;
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 괄호영역 처리기
 *
 *
 * @author wjrmffldrhrl
 */
public class BracketProcessor implements RegularExpressionProcessor {
    private final Pattern bracketPattern;

    /**
     * Constructor
     *
     * @param roleManager RoleManager
     */
    public BracketProcessor(RoleManager roleManager) {
        HashSet<String> patternDic = roleManager.getRole("exception");
        bracketPattern = createPattern(patternDic);
    }

    public BracketProcessor(PublicRoleManager publicRoleManager, RoleManager roleManager) {
        HashSet<String> patternDic = roleManager.getRole("exception");
        patternDic.addAll(publicRoleManager.getRole("exception"));
        bracketPattern = createPattern(patternDic);

    }

    @Override
    public List<Area> find(String data) {

        Matcher bracketMatcher = bracketPattern.matcher(data);
        List<Area> bracketArea = new ArrayList<>();

        while(bracketMatcher.find()) {
            bracketArea.add(new Area(bracketMatcher));
        }

        return bracketArea;
    }


    protected Pattern createPattern(HashSet<String> patternData) {

        if (patternData.isEmpty()) {
            return Pattern.compile("");
        }

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder centerLeft = new StringBuilder("[^]*");
        StringBuilder centerRight = new StringBuilder("[^]*");
        StringBuilder right = new StringBuilder("[]+");

       for(String data : patternData) {

           left.insert(1, "\\" + data.charAt(0));
           centerRight.insert(2, "\\" + data.charAt(0));

           right.insert(1, "\\" + data.charAt(1));
           centerLeft.insert(2, "\\" + data.charAt(1));

       }

       String pattern = left.append(centerLeft).append(centerRight).append(right).toString();

        return  Pattern.compile(pattern);
    }
}
