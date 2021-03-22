package org.moara.yido.splitter.manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moara.yido.splitter.TestFileInitializer;
import org.moara.yido.splitter.utils.FileReader;
import org.moara.yido.splitter.utils.Validation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {



    @BeforeAll
    static void initializeTest() {
        TestFileInitializer.initialize();
    }

    @AfterAll
    static void tearDownTest() {
        TestFileInitializer.tearDown();
    }


    @Test
    public void testCreateValidationObject() {
        Validation validation = new Validation("면", false, 'B');
        assertEquals("면", validation.getValue());
        assertFalse(validation.getMatchFlag());
        assertEquals('B', validation.getComparePosition());
    }

    @Test
    public void testValidationManager() {

        List<Validation> validations = ValidationManager.getValidations("NBSG_test_connective");

        assertNotEquals(0, validations.size());



        int validationIndex = 0;
        for (String data : FileReader.readDictionary("/string_group/test_connective")) {
            Validation validation = validations.get(validationIndex++);
            assertEquals(data, validation.getValue());
            assertFalse(validation.getMatchFlag());
            assertEquals('B', validation.getComparePosition());
        }

    }


    @Test
    public void testInvalidRuleName() {
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
        assertTrue(matchFlagException);
        assertTrue(comparePositionException);
        assertTrue(dataTypeException);
    }
}
