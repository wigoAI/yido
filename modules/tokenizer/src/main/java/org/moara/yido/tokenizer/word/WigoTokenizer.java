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

package org.moara.yido.tokenizer.word;

import org.moara.Moara;
import org.moara.ara.datamining.textmining.dictionary.word.WordDictionary;
import org.moara.ara.datamining.textmining.dictionary.word.element.WordClassDetail;
import org.moara.ara.datamining.textmining.dictionary.word.extract.ExtractWord;
import org.moara.ara.datamining.textmining.dictionary.word.extract.WordExtractResult;
import org.moara.ara.datamining.textmining.dictionary.word.extract.ko.WordExtract_KO;
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.TokenizerInitializer;

import java.util.List;

/**
 * 기본형로그에서는 제외됨
 * @author macle
 */
public class WigoTokenizer implements Tokenizer {

    private final WordDictionary wordDictionary = WordDictionary.getInstance();

    private final WordExtract_KO wordExtract = new WordExtract_KO();

    /**
     * 사전 로드 추가
     */
    public WigoTokenizer(){
        Moara.initMeta();
    }


    private boolean isRelationWordExtract = true;
    //관계어 추출여부
    public void setRelationWordExtract(boolean relationWordExtract) {
        isRelationWordExtract = relationWordExtract;
    }

    private boolean isUnknownExtract = false;

    public void setUnknownExtract(boolean unknownExtract) {
        isUnknownExtract = unknownExtract;
    }

    @Override
    public String getId() {
        return "wigo";
    }

    @Override
    public Token[] getTokens(String text) {

        WordExtractResult result = wordExtract.extract(text, "SNS", wordDictionary, false);
        List<ExtractWord> wordList =  result.getExtractWordList();

        Token [] tokens = new Token[wordList.size()];

        for (int i = 0; i <tokens.length ; i++) {

            ExtractWord extractWord = wordList.get(i);

            if(!isRelationWordExtract && !extractWord.isExtractOriginal()){
                continue;
            }

            if(!isUnknownExtract && extractWord.getWordClassDetail() == WordClassDetail.UNDEFINED){
                continue;
            }


            String partOfSpeech;

            WordClassDetail detail = extractWord.getWordClassDetail();
            if(detail != null){
                partOfSpeech = detail.toString();
            }else{
                partOfSpeech = extractWord.getWordClass().toString();
            }

            WordToken wordToken = new WordToken(extractWord.getWordCode(), extractWord.getSyllableValue("KO")
            , partOfSpeech, extractWord.getStartIndex(), extractWord.getEndIndex()+1);
            

            tokens[i] =wordToken;
        }
        
        
        
        return tokens;
    }
}
