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
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 괄호영역 처리기
 *
 * TODO 1. 괄호의 수가 짝수가 아닐 때 처리
 *
 * @author 조승현
 */
public class BracketProcessor implements RegularExpressionProcessor {
    private Pattern bracketPattern;

    /**
     * Constructor
     * TODO 1. diction load 안되는 문제 해결
     * @param roleManager RoleManager
     */
    public BracketProcessor(RoleManager roleManager) {
        HashSet<String> patternDic = roleManager.getException();
        System.out.println("PatternDic " + patternDic.toString());
        bracketPattern = createPattern(patternDic);
    }

    @Override
    public List<Area> find(String data) {

        Matcher bracketMatcher = bracketPattern.matcher(data);
        List<Area> bracketArea = new ArrayList<>();

        while(bracketMatcher.find()) {
            bracketArea.add(new Area(bracketMatcher.start(), bracketMatcher.end()));
        }

        return bracketArea;
    }

    private Pattern createPattern(HashSet<String> patternData) {

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder centerLeft = new StringBuilder("[^]*");
        StringBuilder centerRight = new StringBuilder("[^]*");
        StringBuilder right = new StringBuilder("[]+");

       for(String data : patternData) {

            if(data == null)
                continue;

            System.out.println(data);
           left.insert(1, "\\" + data.charAt(0));
           centerRight.insert(2, "\\" + data.charAt(0));

           right.insert(1, "\\" + data.charAt(1));
           centerLeft.insert(2, "\\" + data.charAt(1));

       }

       String pattern = left.append(centerLeft).append(centerRight).append(right).toString();

       System.out.println(pattern);

        return  Pattern.compile(pattern);
    }
}