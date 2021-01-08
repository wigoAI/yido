package org.moara.ner.person;

import org.moara.ner.person.PersonEntity;

public class ReporterEntity extends PersonEntity {

    public ReporterEntity(String value, int begin, int end) {
        super(value, "REPORTER", begin, end);
    }
}
