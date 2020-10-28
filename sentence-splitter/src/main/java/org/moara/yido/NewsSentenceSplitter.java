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
package org.moara.yido;

import org.moara.yido.area.processor.ExceptionAreaProcessor;
import org.moara.yido.area.processor.TerminatorAreaProcessor;
import org.moara.yido.role.NewsRoleManager;

import java.util.TreeSet;

/**
 * 뉴스 데이터 문장 구분기
 *
 * @author
 */
public class NewsSentenceSplitter implements SentenceSplitter{
    TerminatorAreaProcessor terminatorAreaProcessor;
    ExceptionAreaProcessor exceptionAreaProcessor;

    NewsSentenceSplitter(Config config) {
        initAreaProcessor(config);
    }

    private void initAreaProcessor(Config config) {
        this.terminatorAreaProcessor = new TerminatorAreaProcessor(NewsRoleManager.getRoleManager(), config);
        this.exceptionAreaProcessor = new ExceptionAreaProcessor(NewsRoleManager.getRoleManager());
    }

    @Override
    public Sentence[] split(String text) {
        this.exceptionAreaProcessor.find(text);
        TreeSet<Integer> splitPoint = findSplitPoint(text);
        return doSplit(splitPoint, text);
    }

    private TreeSet<Integer> findSplitPoint(String text) {
        return this.terminatorAreaProcessor.find(text, this.exceptionAreaProcessor);
    }

    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String text) {
        int startIndex = 0;
        int endIndex = 0;
        int resultIndex = 0;
        Sentence[] result = new Sentence[splitPoint.size() + 1];

        for(int point : splitPoint) {
            endIndex = point;
            Sentence sentence = new Sentence(startIndex, endIndex, text.substring(startIndex, endIndex).trim());

            result[resultIndex++] = sentence;
            startIndex = endIndex;

        }

        result[resultIndex] =  new Sentence(startIndex, text.length(), text.substring(startIndex).trim());

        return result;
    }
}
