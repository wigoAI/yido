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

package org.moara.yido.textmining.sentence;


import org.moara.yido.textmining.Contents;
import org.moara.yido.textmining.Sentence;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Set;

/**
 * json 형태의 구분
 * @author macle
 */
public class JsonSplitter implements SentenceSplitter{
    @Override
    public Sentence[] split(Contents contents) {
        
        //gson 을 사려용하려도 객체 형때문에 사용 보류
        
        JSONArray array = new JSONArray(contents.getText());
        //json 관련 부분은 추후에 구현함
        Sentence[] sentences = new Sentence[array.length()];
        for (int i = 0; i <sentences.length ; i++) {
            //data map을 object 형으로 운영하려면 org json이 편함
            JSONObject obj = array.getJSONObject(i);
            Sentence sentence = new TextSentence((String)obj.remove("contents"), (int)obj.remove("begin"), (int)obj.remove("end"));
            Set<String> keys = obj.keySet();
            //정의된 데이터가 아닌부분은 데이터 map에 설정함
            for(String key: keys){
                Object data = obj.get(key);
                if(data.getClass() == JSONObject.class || data.getClass() == JSONArray.class ){
                    //외부에서 gson이나 편한 라이브러리를 사용하도록 문자열 형태로 data에 설정
                    sentence.setDataMap(key, data.toString());
                }else{
                    sentence.setDataMap(key, data);
                }
            }

            sentences[i] = sentence;
        }


        return sentences;
    }


}
