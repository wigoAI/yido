package org.moara.splitter.role;

import java.util.ArrayList;
import java.util.List;

public class PublicValidationManager extends ValidationManager {

    private static final String[] roles = {"PV_N_B", "PV_N_F", "PV_Y_B", "PV_Y_F"};
    private static final List<Validation> publicValidations = initAllPublicValidations();

    public static List<Validation> getAllPublicValidations() {
        return publicValidations;
    }

    private static List<Validation> initAllPublicValidations() {
        List<Validation> validations = new ArrayList<>();

        for (String role : roles) {
            validations.addAll(getValidations(role));
        }

        return validations;
    }
}
