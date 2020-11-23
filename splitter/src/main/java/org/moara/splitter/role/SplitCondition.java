package org.moara.splitter.role;

import java.util.List;

/**
 * 구분 조건
 * TODO 1. invalid parameter 처리
 *
 * @author wjrmffldrhrl
 */
public class  SplitCondition {

    private final String value;
    private final List<Validation> validations;
    private final char usePublicValidation;
    private final char splitPosition;

    public SplitCondition(String value, List<Validation> validations, char usePublicValidation, char splitPosition) {
        this.value = value;
        this.validations = validations;
        this.usePublicValidation = usePublicValidation;
        this.splitPosition = splitPosition;
    }

    public String getValue() { return value; }
    public List<Validation> getValidations() { return validations; }
    public char getUsePublicValidation() { return usePublicValidation; }
    public char getSplitPosition() { return splitPosition; }
}
