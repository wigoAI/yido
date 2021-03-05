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

package org.moara.yido.tokenizer.example;

import com.seomse.commons.config.Config;

/**
 * @author macle
 */
public class TokenizerSet {
    public static void main(String[] args) {
        // TokenizerManager.getInstance() 부분 보다 설정이 먼저 실행되는게 좋음, 나중에 실행되도 실행에 문제는 없음
        // 기본 토크나이져 설정
        Config.setConfig("yido.tokenizer.default.id", "mecab");
        // 사용할 토크나이져 설정
        // , 단위로 구분
        // 초기에 설정하지 않아도 나중에 불러오는 것도 가능함
        // 단 사전 로딩시간이 들어갈 수 있음 (처음 호출될때)
        // 초기설정하면 TokenizerManager.getInstance() 부분에서 사전 호출
        Config.setConfig("yido.tokenizer.init.ids","yido,mecab");

    }
}
