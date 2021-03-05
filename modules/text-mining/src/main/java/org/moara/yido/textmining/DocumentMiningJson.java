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
 * json 형태의 문서 마이닝
 * stt 문서로 전달받을때 json 으로 만들어서 사용하는 경우가 많음
 * @author macle
 */
public class DocumentMiningJson extends DocumentMining{


    /**
     * 생성자
     * @param document 문서
     */
    public DocumentMiningJson(Document document) {
        super(document);
    }

    String contents;

    @Override
    public Sentence [] miningContents(){
        super.miningContents();
        
        //json 형태의 문서는 범위를 합쳐놓은 내용이 없으므로 관련내용을 생성한다.
        if(sentences.length > 0){
            StringBuilder sb = new StringBuilder();
            sb.append(sentences[0].getContents());

            for (int i = 1; i <sentences.length ; i++) {
                if(sentences[i].begin != sentences[i-1].end){
                    //전문장의 끝이 시작 아니면
                    int gap = sentences[i].begin - sentences[i-1].end;
                    for (int j = 0; j <gap ; j++) {
                        sb.append(' ');
                    }
                }
                sb.append(sentences[i].getContents());
            }

            contents = sb.toString();
        }

        return this.sentences;
    }

    @Override
    public String subContents(int begin, int end){
        if(contents == null){
            return null;
        }
        return contents.substring(begin,end);
    }

}
