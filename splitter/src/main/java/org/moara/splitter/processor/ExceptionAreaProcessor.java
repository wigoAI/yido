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
package org.moara.splitter.processor;

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.role.MetaManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 예외 영역 처리기
 * TODO 1. 코드 정리하기
 *          - 중복 코드 제거
 *      2. url 처리방식 정하기
 *          - url을 예외 영역으로 지정할 필요가 없을 것 같음
 * @author wjrmffldrhrl
 */
public class ExceptionAreaProcessor {


    public List<Area> find(String text) {
        List<Area> exceptionAreaList = new ArrayList<>();

        Matcher matcher =  MetaManager.getDifferentSideBracketPattern().matcher(text);

        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        matcher = MetaManager.getSameSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        return exceptionAreaList;
    }
}
