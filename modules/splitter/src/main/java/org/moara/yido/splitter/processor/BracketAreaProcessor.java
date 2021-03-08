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
package org.moara.yido.splitter.processor;

import org.moara.yido.splitter.manager.ExceptionRuleManager;
import org.moara.yido.splitter.utils.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 예외영역 처리기 구현체
 * 괄호 영역에 대한 처리를 구현함
 *
 * @author wjrmffldrhrl
 */
public class BracketAreaProcessor implements ExceptionAreaProcessor {

    @Override
    public List<Area> find(String text) {
        List<Area> exceptionAreaList = new ArrayList<>();

        Matcher matcher =  ExceptionRuleManager.getDifferentSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        matcher = ExceptionRuleManager.getSameSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        return exceptionAreaList;
    }
}
