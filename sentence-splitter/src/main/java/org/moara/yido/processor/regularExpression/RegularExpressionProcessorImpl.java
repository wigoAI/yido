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
 * 파일 정규식 처리기
 *
 */
public class RegularExpressionProcessorImpl implements RegularExpressionProcessor {
    private final List<Pattern> patterns = new ArrayList<>();

    /**
     * Constructor
     *
     * @param roleManager RoleManager
     */
    public RegularExpressionProcessorImpl(RoleManager roleManager) {
        HashSet<String> patternDic = roleManager.getRegx();
        patterns.addAll(createPatterns(patternDic));
    }

    @Override
    public List<Area> find(String data) {
        List<Area> areas = new ArrayList<>();

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(data);

            while (matcher.find()) {
                areas.add(new Area(matcher));
            }
        }

        return areas;
    }

    private List<Pattern> createPatterns(HashSet<String> patternData) {
        List<Pattern> dicPattern = new ArrayList<>();

        for (String data : patternData) {
            dicPattern.add(Pattern.compile(data));
        }

        return dicPattern;
    }
}
