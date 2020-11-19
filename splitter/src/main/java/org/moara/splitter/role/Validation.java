package org.moara.splitter.role;

public class Validation {

    private final String value;
    private final char matchFlag;
    private final char comparePosition;

    public Validation(String value, char matchFlag, char comparePosition) {
        this.value = value;
        this.matchFlag = matchFlag;
        this.comparePosition = comparePosition;
    }

    public String getValue() {
        return value;
    }

    public char getMatchFlag() {
        return matchFlag;
    }

    public char getComparePosition() {
        return comparePosition;
    }
}
