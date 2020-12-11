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

package org.moara.yido.textmining.splitter;

import com.seomse.commons.data.BeginEnd;
import org.moara.splitter.Splitter;
import org.moara.splitter.SplitterManager;
import org.moara.yido.textmining.Contents;
import org.moara.yido.textmining.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Yido 구분기 프로젝트를 사용하는 문장 구분기
 * @author macle
 */
public class YidoSplitter implements SentenceSplitter{


    private static final Logger logger = LoggerFactory.getLogger(YidoSplitter.class);

    private final Splitter splitter;

    /**
     * 생성자
     * @param splitterId 구분기 아이디
     */
    public YidoSplitter(String splitterId){
        SplitterManager splitterManager = SplitterManager.getInstance();
        Splitter splitter;
        if(splitterId == null){
            splitter = splitterManager.getSplitter();
        }else{
            splitter = splitterManager.getSplitter(splitterId);
            if(splitter == null){
                logger.debug("splitter null default splitter use.... null id: " +  splitterId);
                splitter = splitterManager.getSplitter();
            }
        }


        this.splitter = splitter;
    }

    @Override
    public Sentence[] split(Contents contents) {

        BeginEnd[] positions = splitter.split(contents.getText());




        //splitter 을 이용하여 구분한 결과를 문장결과로 사용

        return new Sentence[0];
    }
}
