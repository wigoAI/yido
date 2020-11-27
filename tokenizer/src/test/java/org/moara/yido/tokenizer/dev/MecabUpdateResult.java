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

package org.moara.yido.tokenizer.dev;

import com.seomse.api.ApiMessage;
import org.moara.yido.tokenizer.TokenizerManager;
import org.moara.yido.tokenizer.word.ole.MecabTokenizer;

/**
 * 서비스를 내리지 않고 사전 업데이트 변경분이 반영되는지 테스트
 * @author macle
 */
public class MecabUpdateResult extends ApiMessage {


    @Override
    public void receive(String message) {
        try{
            MecabTokenizer mecabTokenizer = (MecabTokenizer)TokenizerManager.getInstance().getTokenizer("mecab");
            mecabTokenizer.updateMetaData();
            sendMessage(mecabTokenizer.getMecabResult(message));
        }catch(Exception e){
            e.printStackTrace();
            sendMessage(e.getMessage());
        }
    }
}
