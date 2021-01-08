package org.moara.ner.person;


public class ReporterEntity extends PersonEntity {

    public ReporterEntity(String value, int begin, int end) {
        super(value, "REPORTER", begin, end);
    }
}
