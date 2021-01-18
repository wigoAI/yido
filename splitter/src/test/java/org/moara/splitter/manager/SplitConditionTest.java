package org.moara.splitter.manager;

import com.google.gson.JsonIOException;
import org.junit.*;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.utils.FileReader;
import org.moara.splitter.utils.SplitCondition;
import org.moara.splitter.utils.Validation;

import java.util.*;

/**
 * TODO 1. 공통 유효성 테스트 수정
 */
public class SplitConditionTest {


    @Before
    public void initializeTest() {
        TestFileInitializer.initialize();
    }

    @After
    public void tearDownTest() {
        TestFileInitializer.tearDown();
    }

    @Test
    public void testCreateRuleObject() {
        // terminator
        SplitCondition splitCondition1 = new SplitCondition.Builder("다." , 'B').build();
        Assert.assertEquals("다.", splitCondition1.getValue());
        Assert.assertFalse(splitCondition1.getUsePublicValidation());
        Assert.assertEquals('B', splitCondition1.getSplitPosition());

        // number
        SplitCondition splitCondition2 = new SplitCondition.Builder("1.", 'F').build();
        Assert.assertEquals("1.", splitCondition2.getValue());
        Assert.assertFalse(splitCondition2.getUsePublicValidation());
        Assert.assertEquals('F', splitCondition2.getSplitPosition());
    }

    @Test
    public void testGetConditionRule() {
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(new String[] {"test"});
        SplitCondition splitCondition = splitConditions.get(0);

        Assert.assertEquals("했다", splitCondition.getValue());
        Assert.assertEquals("면", splitCondition.getValidations().get(0).getValue());


    }

    @Test
    public void testCheckPatternSplitCondition() {
        SplitCondition splitCondition1 = new SplitCondition
                .Builder("\\d+\\.",  'F')
                .isPattern().build();

        Assert.assertTrue(splitCondition1.isPattern());

        SplitCondition splitCondition2 = new SplitCondition
                .Builder("\\d+\\.",  'F').build();

        Assert.assertFalse(splitCondition2.isPattern());

    }

    @Test
    public void testSplitConditionManager() {

        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(new String[] {"test"});


        int splitConditionIndex = 0;
        for (String data : FileReader.readDictionary("/string_group/test_terminator")) {
            SplitCondition splitCondition = splitConditions.get(splitConditionIndex++);
            Assert.assertEquals(data, splitCondition.getValue());
            Assert.assertFalse(splitCondition.getUsePublicValidation());
            Assert.assertEquals('B', splitCondition.getSplitPosition());


            //   "validations": ["NBSG_test_connective"]
            List<Validation> validations = splitCondition.getValidations();

            Assert.assertNotEquals(0, validations.size());


            int validationIndex = 0;
            for (String validationData : FileReader.readDictionary("/string_group/test_connective")) {
                Validation validation = validations.get(validationIndex++);
                Assert.assertEquals(validationData, validation.getValue());
                Assert.assertFalse(validation.getMatchFlag());
                Assert.assertEquals('B', validation.getComparePosition());
            }
        }

    }

//    @Test
//    public void testUsePublicValidation() {
//        String[] splitConditions = {"test_public_validation"};
//
//        List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions);
//
//        Assert.assertEquals(splitConditionList1.get(0).getValidations().get(0).getValue(), "하지만");
//
//    }

    @Test(expected = RuntimeException.class)
    public void testInvalidProperties() {
        String[] splitConditions = {"test_invalid_properties"};
        SplitConditionManager.getSplitConditions(splitConditions);

    }

    @Test(expected = JsonIOException.class)
    public void testInvalidUsePublicOptions() {
        String[] splitConditions = {"test_invalid_public_option"};

        SplitConditionManager.getSplitConditions(splitConditions);

    }

    @Test
    public void testSplitConditionToString() {
        SplitCondition splitCondition = new SplitCondition.Builder("다." , 'B').build();
        Assert.assertEquals("SplitCondition{flag=false, position=B, value='다.', isPattern=false}", splitCondition.toString());
    }




}
