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

package org.moara.yido.tokenizer.word;

import org.moara.yido.tokenizer.Token;

/**
 * 단어토큰
 * 
 * 품사종류
  NNG	    //일반 명사     세종, mecab
 ,NNP	//고유 명사     세종, mecab
 ,NNB	//의존 명사     세종, mecab

 ,NNBC   //단위를 나타내는 명사 mecab 단독

 ,NR	    //수사    세종, mecab
 ,NP 	//대명사 세종, mecab
 ,VV	    //동사 세종, mecab
 ,VA	    //형용사 세종, mecab
 ,VX	    //보조 용언 세종, mecab
 ,VCP	//긍정 지정사 세종, mecab
 ,VCN	//부정 지정사 세종, mecab
 ,MM	    //관형사 세종, mecab
 ,MAG	//일반 부사 세종, mecab
 ,MAJ	//접속 부사 세종, mecab
 ,IC	    //감탄사 세종, mecab
 ,JKS	//주격 조사 세종, mecab
 ,JKC	//보격 조사 세종, mecab
 ,JKG	//관형격 조사 세종, mecab
 ,JKO	//목적격 조사 세종, mecab
 ,JKB	//부사격 조사 세종, mecab
 ,JKV	//호격 조사 세종, mecab
 ,JKQ	//인용격 조사 세종, mecab
 ,JX	    //보조사 세종, mecab
 ,JC	    //접속 조사 세종, mecab
 ,EP	    //선어말 어미 세종, mecab
 ,EF	    //종결 어미 세종, mecab
 ,EC	    //연결 어미 세종, mecab
 ,ETN	//명사형 전성 어미 세종, mecab
 ,ETM	//관형형 전성 어미 세종, mecab
 ,XPN	//체언 접두사 세종, mecab
 ,XSN	//명사 파생 접미사 세종, mecab
 ,XSV	//동사 파생 접미사 세종, mecab
 ,XSA	//형용사 파생 접미사 세종, mecab
 ,XR	    //어근 세종, mecab


 ,SF	    //마침표, 물음표, 느낌표 세종, mecab
 ,SE	    //줄임표 세종, mecab

 ,SS	    //따옴표,괄호표,줄표 세종 단독
 ,SSO    // 여는 괄호 (, [ mecab 단독
 ,SSC    //닫는 괄호 ), ] mecab 단독

 ,SP	    //쉼표,가운뎃점,콜론,빗금 세종, mecab

 ,SO	    //붙임표(물결,숨김,빠짐) 세종 단독
 ,SW	    //기타기호 (논리수학기호,화폐기호) 세종 단독
 ,SY     //기타기호 mecab 단독

 ,SL 	//외국어 세종, mecab
 ,SH	    //한자 세종, mecab
 ,SN	    //숫자 세종, mecab
 * @author macle
 */
public class WordToken implements Token {

    //아이디
    String id;
    //단어
    String text;
    //품사 //품사형태 재정의 VV+EF (다녀요) 에 형태에 떨구어 지는 방식...
    String partOfSpeech;

    int begin;
    int end;

    /**
     * 생성자
     * @param id 단어 아이디
     * @param text 단어 텍스트
     * @param partOfSpeech 품사
     * @param begin 시작 위치 (index)
     * @param end 끝 위치 (index + 1) substring(begin, end)
     */
    public WordToken(
            String id
            , String text
            , String partOfSpeech
            , int begin
            , int end
    ){
        this.id = id;
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.begin =begin;
        this.end = end;
    }

    /**
     * token id
     * 단어 같은 경우에는 위고/NNG
     * 위오 같은 형태처럼 특정 아이디를 지정하여 쓰기 위한 기능
     * @return token id
     */
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getBegin() {
        return begin;
    }

    @Override
    public int getEnd() {
        return end;
    }

    /**
     * 품사 얻기
     * @return 품사
     */
    public String getPartOfSpeech() {
        return partOfSpeech;
    }
}
