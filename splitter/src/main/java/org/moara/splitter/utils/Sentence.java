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
package org.moara.splitter.utils;

import com.github.wjrmffldrhrl.Area;

/**
 * 문장 단위 데이터
 *
 *
 * @author wjrmffldrhrl
 */
public class Sentence extends Area {
    private final String text;

    /**
     * Constructor
     *
     * @param begin int
     * @param end int
     * @param text String
     */
    public Sentence(int begin, int end, String text) {
        super(begin, end);
        this.text = text;
    }

    public String getText() { return this.text; }

}
