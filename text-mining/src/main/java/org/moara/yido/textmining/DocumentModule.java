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

package org.moara.yido.textmining;

import com.seomse.commons.config.Config;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.yido.textmining.sentence.JsonSplitter;
import org.moara.yido.textmining.sentence.YidoSplitter;
import org.moara.yido.textmining.sentence.SentenceSplitter;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.TokenizerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 문서 분석에서 사용하는 모듈
 * @author macle
 */
public class DocumentModule {

    private static final Logger logger = LoggerFactory.getLogger(DocumentModule.class);

    private static final SentenceSplitter JSON_SPLITTER = new JsonSplitter();

    /**
     * 문장 구분기 얻기
     * @param docType 문서유형
     * @return 문장구분기
     */
    public static SentenceSplitter getSentenceSplitter(String docType){

        String splitterInfos;
        if(docType == null){
            return new YidoSplitter(null);
        }else{
            splitterInfos = Config.getConfig("text.mining.sentence.splitter." + docType);
            if(splitterInfos == null){
                return new YidoSplitter(null);
            }

            if(splitterInfos.equals("json")){
                return JSON_SPLITTER;
            }

            return new YidoSplitter(splitterInfos.substring(splitterInfos.indexOf(',')+1));
        }

    }

    /**
     * 문단 구분기 얻기
     * @param docType 문서유형
     * @return 문단구분기
     */
    public static Splitter getParagraphSplitter(String docType){

        String splitterId = Config.getConfig("text.mining.paragraph.splitter." + docType);

        if(splitterId == null){
            splitterId = Config.getConfig("text.mining.paragraph.splitter.default");
        }

        return SplitterManager.getInstance().getSplitter(splitterId);
    }

    /**
     * 도크나이져 얻기
     * @param docType 문서유형
     * @return 토크나이져
     */
    public static Tokenizer getTokenizer(String docType){


        if(docType == null) {
            return TokenizerManager.getInstance().getTokenizer();
        }

        String tokenizerId = Config.getConfig("text.mining.sentence.tokenizer." + docType);
        if(tokenizerId == null){
            return TokenizerManager.getInstance().getTokenizer();
        }
        TokenizerManager tokenizerManager = TokenizerManager.getInstance();
        return tokenizerManager.getTokenizer(tokenizerId);
    }

}
