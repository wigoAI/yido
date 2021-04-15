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

import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.word.WordToken;
import org.snu.ids.kkma.ma.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 꼬꼬마 형태소 분석기
 * @author macle
 */
public class KKmaTokenizer  implements Tokenizer {
    @Override
    public String getId() {
        return "kkma";
    }

    @Override
    public Token[] getTokens(String text) {
        try {
            MorphemeAnalyzer ma = new MorphemeAnalyzer();
            ma.createLogger(null);

            List<MExpression> ret = ma.analyze(text);

            ret = ma.postProcess(ret);
            List<Sentence> stl = ma.divideToSentences(ret);


            List<WordToken> tokenList = new ArrayList<>();

            for (Sentence st : stl) {
                for (Eojeol eojeol : st) {
                    for (Morpheme morpheme : eojeol) {
                        WordToken wordToken = new WordToken(morpheme.getSmplStr(), morpheme.getString(), morpheme.getTag(), morpheme.getIndex(), morpheme.getIndex() + morpheme.getSmplStr().length());
                        tokenList.add(wordToken);
                    }
                }
            }
            ma.closeLogger();

            return tokenList.toArray(new WordToken[0]);

        } catch (Exception e) {
           throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        new KKmaTokenizer().getTokens("김용수는 모아라에 다닌다");
    }
}
