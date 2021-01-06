package org.moara.ner;

import java.util.ArrayList;
import java.util.List;

public class ReporterRecognizer implements NamedEntityRecognizer {

    @Override
    public NamedEntity[] recognize(String corpus) {
        corpus = corpus.replaceAll("[^가-힣]", " ");
        String[] splitCorpus = corpus.split(" ");

        List<NamedEntity> reporterEntityList = new ArrayList<>();

        for (int i = 1; i < splitCorpus.length; i++) {
            if (splitCorpus[i].equals("기자")) {
                reporterEntityList.add(new PersonEntity(splitCorpus[i - 1]));
            }
        }


        return reporterEntityList.toArray(new NamedEntity[0]);

    }
}
