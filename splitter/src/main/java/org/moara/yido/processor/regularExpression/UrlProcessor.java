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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url 정규식 처리기
 * @author 조승현
 */
public class UrlProcessor implements RegularExpressionProcessor {
    String URL_PATTERN = "((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?";
    Pattern urlPattern = Pattern.compile(URL_PATTERN);

    @Override
    public List<Area> find(String data) {

        Matcher urlMatcher = urlPattern.matcher(data);
        List<Area> urlArea = new ArrayList<>();

        while(urlMatcher.find()) {
            urlArea.add(new Area(urlMatcher.start(), urlMatcher.end()));
        }

        return urlArea;

    }
}
