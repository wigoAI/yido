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

package org.moara.yido.tokenizer.word.ole;

import org.chasen.mecab.Tagger;
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.word.PartOfSpeech;
import org.moara.yido.tokenizer.word.WordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.seomse.commons.utils.ExceptionUtil;
/**
 * mecab을 활용한 tokenizer
 * @author macle
 */
public class MecabTokenizer implements Tokenizer {

    private static final Logger logger = LoggerFactory.getLogger(MecabTokenizer.class);

    static {
        try {
            System.loadLibrary("MeCab");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            logger.error("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
            System.exit(1);
        }
    }




    private final Tagger tagger = new Tagger();

    @Override
    public String getId() {
        return "mecab";
    }

    private final Object lock = new Object();

    @Override
    public Token[] getTokens(String text) {

        // thread safe 하지 않다고 판단되어 synchronized 처리
        synchronized (lock){
            String [] tokens = tagger.parse(text).split("\n");
            //마지막은 EOS 라서 -1
            WordToken [] wordTokens = new WordToken[tokens.length-1];

            int fromIndex = 0;


            for (int i = 0; i <wordTokens.length ; i++) {

                //실험용
                //복합단어 처리는 추가로 진행해야함
                int index = tokens[i].indexOf('\t');
                String tokenText = tokens[i].substring(0,index).trim();

                int last = tokens[i].indexOf(',',index);
                if(last == -1){
                    last = tokens[i].length();
                }

                String partOfSpeech = tokens[i].substring(index +1 ,last).trim();

                int begin = text.indexOf(tokenText, fromIndex);
                fromIndex = begin + tokenText.length();

                wordTokens[i] = new WordToken(
                        tokenText+"/"+partOfSpeech
                        , tokenText
                        , partOfSpeech
                        , begin
                        , fromIndex
                );
            }


            return wordTokens;

        }

    }
}