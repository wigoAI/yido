/*
 * Copyright (C) 2021 Wigo Inc.
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

package org.moara.yido.tokenizer.word.ole;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.word.WordToken;

import java.util.List;

/**
 * @author macle
 */
public class KomoranTokenizer  implements Tokenizer {
    @Override
    public String getId() {
        return "komoran";
    }

    @Override
    public Token[] getTokens(String text) {

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(text);
        List<kr.co.shineware.nlp.komoran.model.Token> tokenList = analyzeResultList.getTokenList();

        WordToken [] tokens = new WordToken[tokenList.size()];

        for (int i = 0; i <tokens.length ; i++) {


            kr.co.shineware.nlp.komoran.model.Token kToken = tokenList.get(i);

            System.out.println(kToken.getPos());

            tokens[i] = new WordToken(kToken.getMorph() + "/" + kToken.getPos(), kToken.getMorph(), kToken.getPos(), kToken.getBeginIndex(), kToken.getEndIndex());
        }
        return tokens;
    }


    public static void main(String[] args) {
        new KomoranTokenizer().getTokens("김용수는 모아라에 다닌다");
    }
}
