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
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import scala.collection.Seq;

import java.util.List;

/**
 * okt
 * open korean text
 * (twitter)
 * @author macle
 */
public class OktTokenizer  implements Tokenizer {
    @Override
    public String getId() {
        return "okt";
    }

    @Override
    public Token[] getTokens(String text) {

        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);

        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

        List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);

        WordToken [] wordTokens = new WordToken[tokenList.size()];

        for (int i = 0; i <wordTokens.length ; i++) {

            KoreanTokenJava koreanTokenJava = tokenList.get(i);

            wordTokens[i] = new WordToken(koreanTokenJava.getText() + "/" + koreanTokenJava.getPos(), koreanTokenJava.getText(), koreanTokenJava.getPos().name(),koreanTokenJava.getOffset(), koreanTokenJava.getOffset() + koreanTokenJava.getLength());
        }

        return wordTokens;
    }
}
