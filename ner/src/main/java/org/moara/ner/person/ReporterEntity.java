package org.moara.ner.person;


import java.util.ArrayList;
import java.util.List;

public class ReporterEntity extends PersonEntity {

    private List<String> nearTokens = new ArrayList<>();

    public ReporterEntity(String value, int begin, int end) {
        super(value, "REPORTER", begin, end);
    }

    public void setNearTokens(List<String> nearTokens) {
        this.nearTokens = nearTokens;
    }
}
