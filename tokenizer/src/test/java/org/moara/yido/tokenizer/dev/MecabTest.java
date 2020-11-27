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

package org.moara.yido.tokenizer.dev;

import org.moara.yido.tokenizer.word.ole.MecabTokenizer;

/**
 * 개발용 임시
 * @author macle
 */
public class MecabTest {


    public static void main(String[] args) {
        //maven 등록문제로    System.loadLibrary("MeCab"); 부분은 한소스에만 둔다
        MecabTokenizer mecabTokenizer = new MecabTokenizer();
        String sentence ="시내버스가 우아한형제들에 가요";
        System.out.println(mecabTokenizer.getMecabResult(sentence));


    }
}
