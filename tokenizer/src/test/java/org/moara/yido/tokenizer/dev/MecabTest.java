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

import org.chasen.mecab.Tagger;

/**
 * 개발용 임시
 * @author macle
 */
public class MecabTest {
    static {
        try {
            System.loadLibrary("MeCab");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains");
            System.exit(1);
        }
    }

    public static void main(String[] args) {



        String sentence ="시내버스가 위고에 가요";


        Tagger tagger = new Tagger();
        String result = tagger.parse(sentence);
        String [] words =  result.split("\n");

        for(String word : words){
            System.out.println(word);
        }
    }
}
