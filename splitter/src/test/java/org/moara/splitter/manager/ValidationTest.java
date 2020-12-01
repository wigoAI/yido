package org.moara.splitter.manager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.utils.Validation;
import org.moara.splitter.utils.file.FileManager;

import java.util.Collection;
import java.util.List;

public class ValidationTest {



    @Before
    public void initializeTest() {
        TestFileInitializer.initialize();
    }

    @After
    public void tearDownTest() {
        TestFileInitializer.tearDown();
    }


    @Test
    public void testCreateValidationObject() {
        Validation validation = new Validation("면", 'N', 'B');
        Assert.assertEquals("면", validation.getValue());
        Assert.assertEquals('N', validation.getMatchFlag());
        Assert.assertEquals('B', validation.getComparePosition());

    }

    @Test
    public void testValidationManager() {

        List<Validation> validations = ValidationManager.getValidations("NBSG_test_connective");

        Assert.assertNotEquals(0, validations.size());

        Collection<String> dataList = FileManager.readFile("/string_group/test_connective.dic");

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
        boolean matchFlagException = false;
        boolean comparePositionException = false;
        boolean dataTypeException = false;

        try {
            ValidationManager.getValidations("KBSG_test_connective");
        } catch (RuntimeException e) {
            matchFlagException = true;
        }
        try {

            ValidationManager.getValidations("NXSG_test_connective");
        } catch (RuntimeException e) {
            comparePositionException = true;
        }
        try {
            ValidationManager.getValidations("NBSK_test_connective");
        } catch (RuntimeException e) {
            dataTypeException = true;
        }
        Assert.assertTrue(matchFlagException);
        Assert.assertTrue(comparePositionException);
        Assert.assertTrue(dataTypeException);
    }
}
