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

/**
 * DocumentMining 생성기
 * @author macle
 */
public class DocumentMiningFactory {

    /**
     * DocumentMining 생성
     * document 에 적합한 DocumentMining 을 생성하여 돌려준다
     * @param document 문서
     * @return doucment 적합한 마이닝
     */
    public static DocumentMining newDocumentMining(Document document){
        if(document.type == null){
            return new DocumentMining(document);
        }

        if(document.type.equals("json")){
            return new DocumentMiningJson(document);
        }

        //문단 정보가 필요하면
        //문서유형에 _paragraph 를 붙여서 관리하는것으로 정의한다
        //예: news_paragraph
        if(document.type.endsWith("paragraph")){
            return new DocumentMiningParagraph(document);
        }

        
        return new DocumentMining(document);

    }

}
