package org.moara.ner;

public interface RecognizeResult {


    NamedEntity[] getEntities();

    int size();
}
