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

package org.moara.yido.tokenizer.word.ole;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;
import org.moara.yido.tokenizer.Token;
import org.moara.yido.tokenizer.Tokenizer;
import org.moara.yido.tokenizer.word.WordToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 한나눔 토크나이져
 * @author macle
 */
public class HnnTokenizer implements Tokenizer {

    @Override
    public String getId() {
        return "hnn";
    }

    @Override
    public Token[] getTokens(String text) {
        Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);

        try{

            workflow.activateWorkflow(true);

            workflow.analyze(text);

            String value = workflow.getResultOfDocument().trim();
            String [] array = value.split("\n\n");

            int fromIndex = 0;


            List<WordToken> tokenList = new ArrayList<>();

            for(String split : array){

                int index = split.indexOf('\n');

                String find = split.substring(0, index);
                String tagger = split.substring(index+1).trim();


                int splitBegin = text.indexOf(find, fromIndex);
                int splitEnd = splitBegin + find.length();


                String [] tokens = tagger.split("\\+");


                int next = 0;

                for(String tokenValue : tokens){
                    index = tokenValue.indexOf('/');

                    String tokenText = tokenValue.substring(0,index);
                    String partOfSpeech = tokenValue.substring(index+1);


                    int begin ;

                    int end;

                    try {
                        begin =splitBegin +find.indexOf(tokenText, next);
                        if (begin == -1) {
                            begin = splitBegin;
                            end = splitEnd;
                        } else {
                            end = begin + tokenText.length();
                        }

                    }catch(Exception e) {
                        begin = splitBegin;
                        end = splitEnd;
                    }



                    WordToken wordToken = new WordToken(
                            tokenValue
                            , tokenText
                            , partOfSpeech
                            , begin
                            , end
                    );

                    tokenList.add(wordToken);




                }


                fromIndex = splitEnd;

//                System.out.println(find + " " + tagger);


            }

            return tokenList.toArray(new WordToken[0]);
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            try{workflow.clear();}catch(Exception ignore){}
            try{workflow.close();}catch(Exception ignore){}
        }
    }

}
