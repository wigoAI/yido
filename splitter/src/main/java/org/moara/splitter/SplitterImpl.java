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
package org.moara.splitter;

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.processor.ExceptionAreaProcessor;
import org.moara.splitter.processor.TerminatorAreaProcessor;
import org.moara.splitter.utils.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 구분기 구현체
 *
 * @author wjrmffldrhrl
 */
public class SplitterImpl implements Splitter {
    protected final TerminatorAreaProcessor terminatorAreaProcessor;
    protected final ExceptionAreaProcessor exceptionAreaProcessor;

    SplitterImpl(TerminatorAreaProcessor terminatorAreaProcessor,
                 ExceptionAreaProcessor exceptionAreaProcessor) {
        this.terminatorAreaProcessor = terminatorAreaProcessor;
        this.exceptionAreaProcessor = exceptionAreaProcessor;
    }

    @Override
    public Sentence[] split(String text) {
        if (text.isEmpty()) {
            return new Sentence[0];
        }

        TreeSet<Integer> splitPoint = getSplitPoint(text);

        return doSplit(splitPoint, text);

    }

    private TreeSet<Integer> getSplitPoint(String text) {
        TreeSet<Integer> splitPoints = terminatorAreaProcessor.find(text);
        List<Area> exceptionAreas = exceptionAreaProcessor.find(text);
        List<Integer> removeItems = new ArrayList<>();

        for (Area exceptionArea : exceptionAreas) {
            for(int splitPoint : splitPoints) {
                if(exceptionArea.contains(splitPoint)) { removeItems.add(splitPoint); }
            }
        }

        for (int removeItem : removeItems) {
            splitPoints.remove(removeItem);
        }

        return splitPoints;
    }


    private Sentence[] doSplit(TreeSet<Integer> splitPoint, String inputData) {
        Sentence[] result = new Sentence[splitPoint.size() + 1];
        int startIndex = 0;
        int resultIndex = 0;

        for(int point : splitPoint) {
            Sentence sentence = new Sentence(startIndex, point, inputData.substring(startIndex, point).trim());

            result[resultIndex++] = sentence;
            startIndex = point;

        }

        result[resultIndex] =  new Sentence(startIndex, inputData.length(), inputData.substring(startIndex).trim());

        return result;
    }
}
