package org.moara.splitter.role;

/**
 * TODO 1. invalid parameter
 */
public class Role {

    private final String value;
    private final boolean usePublicValidation;
    private final char splitPosition;

    public Role(String value, boolean usePublicValidation, char splitPosition) {
        this.value = value;
        this.usePublicValidation = usePublicValidation;
        this.splitPosition = splitPosition;
    }

    public String getValue() {
        return value;
    }

    public boolean isUsePublicValidation() {
        return usePublicValidation;
    }

    public char getSplitPosition() {
        return splitPosition;
    }
}
