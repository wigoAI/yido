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

/**
 * 품사 정의
 * 토크나이져 구현에 사용 될 부분
 * @author macle
 */
public enum PartOfSpeech {
    NNG	 //일반 명사
    , NNP	 //고유 명사
    , NNB	 //의존명사
    , NNBC //	단위를 나타내는 명사 (mecab)
    , NR	 //수사
    , NP	 //대명사
    , VV	 //동사
    , VA	 //형용사
    , VX	 //보조 용언
    , VCP	 //긍정 지정사
    , VCN	 //부정 지정사
    , MM	 //관형사
    , MAG	 //일반 부사
    , MAJ	 //접속 부사
    , IC	 //감탄사
    , JKS	 //주격 조사
    , JKC	 //보격 조사
    , JKG	 //관형격 조사
    , JKO	 //목적격 조사
    , JKB	 //부사격 조사
    , JKV	 //호격 조사
    , JKQ	 //인용격 조사
    , JX	 //보조사
    , JC	 //접속 조사
    , EP	 //선어말 어미
    , EF	 //종결 어미
    , EC	 //연결 어미
    , ETN	 //명사형 전성 어미
    , ETM	 //관형형 전성 어미
    , XPN	 //체언 접두사
    , XSN	 //명사 파생 접미사
    , XSV	 //동사 파생 접미사
    , XSA	 //형용사 파생 접미사
    , XR	 //어근
    , SF	 //마침표, 물음표, 느낌표
    , SE	 //줄임표
    , SS	 //따옴표,괄호표,줄표
    , SSO  // 여는 괄호 (, [ (mecab)
    , SSC  // 닫는 괄호 ), ] (mecab)
    , SP	 //쉼표,가운뎃점,콜론,빗금
    , SO	 //붙임표(물결,숨김,빠짐)
    , SW	 //기타기호 (논리수학기호,화폐기호)
    , SL	 //외국어
    , SH	 //한자
    , SN	 //숫자
}
