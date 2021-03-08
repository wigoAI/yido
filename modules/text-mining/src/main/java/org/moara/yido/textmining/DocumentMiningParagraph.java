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

import com.seomse.commons.data.BeginEnd;
import org.moara.yido.splitter.Splitter;
import org.moara.yido.textmining.sentence.ContentsSentence;
import org.moara.yido.textmining.sentence.SentenceSplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * 문단 구성이 필요한 문서 마이닝
 * @author macle
 */
public class DocumentMiningParagraph extends DocumentMining{

    //내용 문단구성
    Paragraph[] paragraphs;

    /**
     * 생성자
     *
     * @param document 문서
     */
    public DocumentMiningParagraph(Document document) {
        super(document);
    }

    /**
     * 문단 단위 마이닝
     * @return 문단 배열
     */
    public Paragraph[] miningParagraph(){

        if(document.contents == null){
            return null;
        }

        List<Sentence> sentenceList = new ArrayList<>();
        //문장유형 쪽 구현
        //범위로 컨텐츠 얻어오는 기능 구현
        Splitter splitter = DocumentModule.getParagraphSplitter(document.type);
        BeginEnd[] beginEnds = splitter.split(document.contents);
        Paragraph[] paragraphs = new Paragraph[beginEnds.length];
        Contents documentContents = () -> document.contents;

        for (int i = 0; i <paragraphs.length ; i++) {
            Paragraph paragraph  = new Paragraph(this, beginEnds[i].getBegin(), beginEnds[i].getEnd());

            final String text = paragraph.getContents();
            Contents contents = () -> text;

            SentenceSplitter sentenceSplitter = DocumentModule.getSentenceSplitter(document.type);
            Sentence[] sentences = sentenceSplitter.split(contents);
            tokenExtract(sentences);
            
            for(Sentence sentence : sentences){
                //원문 단위 정보로 변경하기
                ContentsSentence contentsSentence = (ContentsSentence)sentence;
                contentsSentence.changeContents(documentContents);
                sentence.begin += paragraph.begin;
                sentence.end += paragraph.begin;
                sentenceList.add(sentence);
            }
            paragraph.sentences = sentences;
            paragraphs[i] = paragraph;
        }
        this.paragraphs = paragraphs;
        this.sentences = sentenceList.toArray(new Sentence[0]);
        sentenceList.clear();

        return this.paragraphs;

    }


    @Override
    public Sentence [] miningContents(){
        miningParagraph();
        return this.sentences;
    }

    /**
     * 문단 얻기
     * @return 문단 배열
     */
    public Paragraph[] getParagraphs() {
        if(paragraphs != null){
            return paragraphs;
        }

        return miningParagraph();
    }
}
