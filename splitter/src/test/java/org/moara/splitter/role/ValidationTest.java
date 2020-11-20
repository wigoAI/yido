package org.moara.splitter.role;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.utils.file.FileManager;

import java.util.Collection;
import java.util.List;

public class ValidationTest {

    @Test
    public void testCreateValidationObject() {
        Validation validation = new Validation("면", 'N', 'B');
        Assert.assertEquals("면", validation.getValue());
        Assert.assertEquals('N', validation.getMatchFlag());
        Assert.assertEquals('B', validation.getComparePosition());

    }

    @Test
    public void testValidationManager() {

        List<Validation> validations = ValidationManager.getValidations("V_N_B_001");

        Assert.assertNotEquals(0, validations.size());

        Collection<String> dataList = FileManager.readFile("/string_group/validation/V_N_B_001.role");

        int validationIndex = 0;
        for (String data : dataList) {
            Validation validation = validations.get(validationIndex++);
            Assert.assertEquals(data, validation.getValue());
            Assert.assertEquals('N', validation.getMatchFlag());
            Assert.assertEquals('B', validation.getComparePosition());
        }

    }


    @Test
    public void testInvalidRoleName() {
        boolean roleTypeException = false;
        boolean matchFlagException = false;
        boolean comparePositionException = false;

        try {

            ValidationManager.getValidations("T_Y_B_001");
        } catch (RuntimeException e) {
            roleTypeException = true;
        }
        try {

            ValidationManager.getValidations("V_K_B_001");
        } catch (RuntimeException e) {
            matchFlagException = true;
        }
        try {

            ValidationManager.getValidations("V_Y_C_001");
        } catch (RuntimeException e) {
            comparePositionException = true;
        }

        Assert.assertTrue(roleTypeException);
        Assert.assertTrue(matchFlagException);
        Assert.assertTrue(comparePositionException);
    }


}
