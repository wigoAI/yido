package org.moara.ner;

public class PersonEntity implements NamedEntity {

    private final String value;
    private static final String tag = "PER";

    public PersonEntity(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
