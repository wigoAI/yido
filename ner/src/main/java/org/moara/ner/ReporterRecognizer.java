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
package org.moara.ner;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 기자 개체명 인식기
 *
 * @author wjrmffldrhrl
 */
public class ReporterRecognizer implements NamedEntityRecognizer {

    private final static String TARGET_WORD = "기자";
    private final static String[] SPLITTERS = {"·", "?", "/"};
    private final static String[] exceptionWords = {"엄마", "취재", "인턴", "촬영"};
    private final String splitterStr;


    public ReporterRecognizer() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String splitter : SPLITTERS) {
            stringBuilder.append("\\").append(splitter);
        }

        splitterStr = stringBuilder.toString();
    }


    @Override
    public NamedEntity[] recognize(String corpus) {

        List<NamedEntity> reporterEntityList = new ArrayList<>();

        reporterEntityList.addAll(recognizeBySplitSpace(corpus));


        return reporterEntityList.toArray(new NamedEntity[0]);

    }

    private  List<NamedEntity> recognizeBySplitSpace(String corpus) {
        corpus = corpus.replaceAll("[^가-힣" + splitterStr + "]", " ");
        String[] splitCorpus = corpus.split(" ");

        Set<String> reporterNameSet = new HashSet<>();

        // 공백으로 구분한 데이터 조회
        for (int i = 1; i < splitCorpus.length; i++) {

            if (splitCorpus[i].equals(TARGET_WORD)) {

                for (String splitter : SPLITTERS) {

                    if (splitCorpus[i - 1].contains(splitter)) {

                        String[] reporterNames = splitCorpus[i - 1].trim().split(new StringBuilder().append("\\").append(splitter).toString());

                        reporterNameSet.addAll(Arrays.asList(reporterNames));

                        break;
                    }
                }

                if (splitCorpus[i - 1].length() < 5) {

                    reporterNameSet.add(splitCorpus[i - 1]);
                }
            }
        }


        // 붙어있는 기자 이름 검출
        for (int i = 1; i < splitCorpus.length; i++) {

            if (splitCorpus[i].endsWith(TARGET_WORD)) {
                String containReporterString = splitCorpus[i].substring(0, splitCorpus[i].length() - 2);
                for (String splitter : SPLITTERS) {

                    if (containReporterString.contains(splitter)) {

                        String[] reporterNames = containReporterString.trim().split(new StringBuilder().append("\\").append(splitter).toString());

                        reporterNameSet.addAll(Arrays.asList(reporterNames));
                        break;
                    }
                }

                if (containReporterString.length() < 5) {
                    reporterNameSet.add(containReporterString);
                }
            }
        }


        reporterNameSet = reporterNameSet.stream()
                .map(reporterName -> reporterName.replaceAll("[" + splitterStr + "]", "")).collect(Collectors.toSet());



        for (String exceptionWord : exceptionWords) {
            reporterNameSet.removeIf(r -> r.equals(exceptionWord) || r.length() < 2);
        }

        List<NamedEntity> reporterEntityList = new ArrayList<>();

        for (String reporterName : reporterNameSet) {
            reporterEntityList.add(new PersonEntity(reporterName));
        }
        return reporterEntityList;
    }
}
