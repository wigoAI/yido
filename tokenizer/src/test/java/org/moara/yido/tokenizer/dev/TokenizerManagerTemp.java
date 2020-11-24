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

import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.TokenizerManager;

/**
 * @author macle
 */
public class TokenizerManagerTemp {
    public static void main(String[] args) {


        Token[] tokens = TokenizerManager.getInstance().getTokenizer().getTokens("김용수는 위고에 다녀요");

        for(Token token : tokens){
            System.out.println(token.getText());
        }
    }
}
