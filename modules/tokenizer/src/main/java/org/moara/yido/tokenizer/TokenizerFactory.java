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

package org.moara.yido.tokenizer;

import org.moara.yido.tokenizer.word.ole.MecabTokenizer;

/**
 * Tokenizer 생성기
 * @author macle
 */
public class TokenizerFactory {

    /**
     * 생성자 막음
     */
    private TokenizerFactory(){
        
    }
    
    /**
     * tokenizer 생성
     * @param id tokenizer id
     * @return Tokenizer
     */
    static Tokenizer newTokenizer(String id){
        if(id.equals("mecab")){
            return new MecabTokenizer();
        }
        throw new TokenizerNotFoundException(id);
    }

}
