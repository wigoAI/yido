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

import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.TokenizerManager;
import org.moara.yido.tokenizer.word.CompoundToken;
import org.moara.yido.tokenizer.word.WordToken;

/**
 * TokenizerExample 예제 소스
 * @author macle
 */
public class TokenizerExample {
    public static void main(String[] args) {
        Token[] tokens = TokenizerManager.getInstance().getTokenizer().getTokens("시내버스가 위고에 다녀요");
        for(Token token : tokens){
            WordToken wordToken = (WordToken)token;

            System.out.print(token.getText() +", " + wordToken.getPartOfSpeech());

            if(wordToken instanceof CompoundToken){
                CompoundToken compoundToken =(CompoundToken) wordToken;
                String [] wordIds = compoundToken.getWordIds();
                for (String wordId : wordIds) {
                    System.out.print(" " + wordId);
                }
            }
            System.out.println();
        }
    }
}
