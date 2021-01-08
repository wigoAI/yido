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
package org.moara.ner.person;

import org.moara.ner.NamedEntity;
import org.moara.ner.NamedEntityRecognizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 기자 개체명 인식기
 * TODO 두 메서드 모두 테스트하기
 * @author wjrmffldrhrl
 */
class ReporterRecognizer implements NamedEntityRecognizer {

    private final String targetWord = "기자";
    private final String[] SPLITTERS = {"·", "?", "/"};
    private final String[] exceptionWords;
    private final String splitterStr;

    public ReporterRecognizer(String[] exceptionWords) {
        this.exceptionWords = exceptionWords;
        StringBuilder stringBuilder = new StringBuilder();
        for (String splitter : SPLITTERS) {
            stringBuilder.append("\\").append(splitter);
        }

        splitterStr = stringBuilder.toString();
    }

    @Override
    public NamedEntity[] recognize(String corpus) {

        List<NamedEntity> reporterEntityList = new ArrayList<>();

        reporterEntityList.addAll(recognizeByWordParse(corpus));

        return reporterEntityList.toArray(new NamedEntity[0]);

    }

    private  List<NamedEntity> recognizeByWordParse(String corpus) {
        corpus = corpus.replaceAll("[^가-힣" + splitterStr + "]", " ");
        corpus = corpus.replaceAll("[" + splitterStr + "]", "S");
        List<NamedEntity> reporterEntities = new ArrayList<>();

        int targetIndex = 0;
        while (targetIndex < corpus.length()) {
            targetIndex = corpus.indexOf(" " + targetWord + " ", targetIndex);

            if (targetIndex == -1) {
                break;
            }

            int entityBegin = corpus.substring(0, targetIndex).lastIndexOf(" ") + 1;

            if (entityBegin == -1) {
                break;
            }

            String[] reporterNames = corpus.substring(entityBegin, targetIndex).split("S");

            for (String reporterName : reporterNames) {
                if (reporterName.length() == 0 || Arrays.asList(exceptionWords).contains(reporterName)) {
                    continue;
                }

                int entityEnd = entityBegin + reporterName.length();
                reporterEntities.add(new ReporterEntity(reporterName, entityBegin, entityEnd));

                entityBegin = entityEnd + 1;
            }


            System.out.println("split reporter : " + targetIndex);

            targetIndex++;

        }


        targetIndex = 0;
        while (targetIndex < corpus.length()) {
            targetIndex = corpus.indexOf(targetWord + " ", targetIndex);

            if (targetIndex == -1) {
                break;
            }

            // 조승현기자     조승현 기자
            int entityBegin = corpus.substring(0, targetIndex).lastIndexOf(" ") + 1;

            if (entityBegin == -1 || entityBegin == targetIndex) {
                break;
            }



            String[] reporterNames = corpus.substring(entityBegin, targetIndex).split("S");

            for (String reporterName : reporterNames) {
                int entityEnd = entityBegin + reporterName.length();
                ReporterEntity reporterEntity = new ReporterEntity(reporterName, entityBegin, entityEnd);
                entityBegin = entityEnd + 1;

                if (reporterName.length() == 0 || Arrays.asList(exceptionWords).contains(reporterName)) {
                    continue;
                }
                reporterEntities.add(reporterEntity);


            }

            System.out.println("non split reporter : " + targetIndex);
            targetIndex++;
        }

        return reporterEntities;

    }

    private  List<NamedEntity> testrecognizeByWordParse(String corpus) {
        corpus = corpus.replaceAll("[^가-힣" + splitterStr + "]", " ");
        String[] splitCorpus = corpus.split(" ");

        Set<String> reporterNameSet = new HashSet<>();

        // 공백으로 구분한 데이터 조회
        for (int i = 1; i < splitCorpus.length; i++) {

            if (splitCorpus[i].equals(targetWord)) {

                for (String splitter : SPLITTERS) {

                    if (splitCorpus[i - 1].contains(splitter)) {

                        String[] reporterNames = splitCorpus[i - 1].split(new StringBuilder().append("\\").append(splitter).toString());

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

            if (splitCorpus[i].endsWith(targetWord)) {
                String containReporterString = splitCorpus[i].substring(0, splitCorpus[i].length() - 2);
                for (String splitter : SPLITTERS) {

                    if (containReporterString.contains(splitter)) {

                        String[] reporterNames = containReporterString.split(new StringBuilder().append("\\").append(splitter).toString());

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
                .map(reporterName -> reporterName.replaceAll("[" + splitterStr + "]", ""))
                .collect(Collectors.toSet());

        for (String exceptionWord : exceptionWords) {
            reporterNameSet.removeIf(r -> r.equals(exceptionWord) || r.length() < 2);
        }

        List<NamedEntity> reporterEntityList = new ArrayList<>();

        for (String reporterName : reporterNameSet) {
            int begin = corpus.indexOf(reporterName);
            int end = begin + reporterName.length();

            reporterEntityList.add(new ReporterEntity(reporterName, begin, end));
        }
        return reporterEntityList;
    }
}
