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

import org.moara.yido.textmining.sentence.SentenceSplitter;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.word.WordToken;

/**
 * 문서 마이닝 
 * 데이터 마이닝 내용을 보고이름을 지음
 *
 * 데이터 (Data)   : 우리가 알고있는 그 데이터이다. 보통 연구나 조사 등의 바탕이 되는 재료를 말한다.
 *
 * 마이닝 (Mining) : 채굴, 채광, 채광 산업을 말한다. 채광이란 광산에서 광석을 캐내는것을 의미한다.
 *
 * 즉, 데이터마이닝이란 광산에서 광석을 캐내는 것에 비유한 것으로, 금광석에 극히 미량으로 포함된 금을 여러 단계를 거쳐 추출하듯이 "수 많은 데이터의 산에서 가치있는 유용한 정보를 찾아 내는 것" 이다
 *
 * @author macle
 */
public class DocumentMining {

    protected final Document document;

    //제목 문장구성
    protected Sentence[] titleSentences;

    //내용 문장구성
    protected Sentence[] sentences;

    /**
     * 생성자
     * @param document 문서
     */
    public DocumentMining(Document document){
        this.document = document;
    }

    /**
     * 제목 문장단위로 구분
     * 문장 구분 및 문자 을 활용한 토큰 추출
     *
     * @return 문장 배열
     */
    public Sentence [] miningTitle(){
        if(document.title == null){
            return new Sentence[0];
        }

        SentenceSplitter splitter = DocumentModule.getSentenceSplitter(document.type);

        Contents contents = () -> document.title;
        Sentence[] sentences = splitter.split(contents);

        tokenExtract(sentences);
        this.titleSentences = sentences;
        return this.titleSentences;
    }

    protected void tokenExtract(Sentence[] sentences){
        Tokenizer tokenizer = DocumentModule.getTokenizer(document.type);
        for (Sentence sentence : sentences){
            String text = sentence.getContents();
            sentence.tokens = (WordToken[])tokenizer.getTokens(text);
        }
    }

    /**
     * 본문 문장 단위로 구분
     * 문장 구분 및 문장을 활용한 토큰 추출
     * @return 문장 배열
     */
    public Sentence [] miningContents(){

        if(document.title == null){
            return new Sentence[0];
        }
        SentenceSplitter splitter = DocumentModule.getSentenceSplitter(document.type);

        Contents contents = () -> document.contents;
        Sentence[] sentences = splitter.split(contents);
        tokenExtract(sentences);
        this.sentences = sentences;
        return this.sentences;
    }


    /**
     * 원문 얻기
     * @return 원문
     */
    public Document getDocument() {
        return document;
    }

    /**
     * 제목 마이닝 결과 얻기
     * @return 문장배열 및 문장별 단어 토큰 배열
     */
    public Sentence[] getTitleSentences() {
        if(sentences != null){
            return sentences;
        }

        return miningTitle();
    }

    /**
     * 본문 마이닝 결과 얻기
     * @return 문장배열 및 분잘 별 단어 토큰 배열
     */
    public Sentence[] getSentences() {
        if(sentences != null){
            return sentences;
        }

        return miningContents();
    }


}
