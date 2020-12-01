package org.moara.splitter.role;

import org.junit.*;
import org.moara.splitter.TestFileInitializer;
import org.moara.splitter.utils.file.FileManager;

import java.util.*;

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
    public void testCreateRoleObject() {
        // terminator
        SplitCondition splitCondition1 = new SplitCondition.Builder("다.", 'N', 'B').build();
        Assert.assertEquals("다.", splitCondition1.getValue());
        Assert.assertEquals('N', splitCondition1.getUsePublicValidation());
        Assert.assertEquals('B', splitCondition1.getSplitPosition());


        // number
        List<Validation> validations2 = new ArrayList<>();
        SplitCondition splitCondition2 = new SplitCondition.Builder("1.", 'Y', 'F').build();
        Assert.assertEquals("1.", splitCondition2.getValue());
        Assert.assertEquals('Y', splitCondition2.getUsePublicValidation());
        Assert.assertEquals('F', splitCondition2.getSplitPosition());
    }

    @Test
    public void testGetConditionRole() {
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(new String[] {"test"});
        SplitCondition splitCondition = splitConditions.get(0);

        Assert.assertEquals("했다", splitCondition.getValue());
        Assert.assertEquals("면", splitCondition.getValidations().get(0).getValue());


    }

    /**
     * TODO 1. 패턴 조건에 맞는 값 만들고 처리하기
     */
    @Test
    public void testCheckPatternSplitCondition() {
        SplitCondition splitCondition1 = new SplitCondition
                .Builder("\\d+\\.", 'N', 'F')
                .isPattern(true).build();

        Assert.assertTrue(splitCondition1.isPattern());

        SplitCondition splitCondition2 = new SplitCondition
                .Builder("\\d+\\.", 'N', 'F')
                .isPattern(false).build();

        Assert.assertFalse(splitCondition2.isPattern());

    }

    @Test
    public void testSplitConditionManager() {

        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(new String[] {"test"});

        Collection<String> dataList = FileManager.readFile("/string_group/test_terminator.dic");

        int splitConditionIndex = 0;
        for (String data : dataList) {
            SplitCondition splitCondition = splitConditions.get(splitConditionIndex++);
            Assert.assertEquals(data, splitCondition.getValue());
            Assert.assertEquals('N', splitCondition.getUsePublicValidation());
            Assert.assertEquals('B', splitCondition.getSplitPosition());


            //   "validations": ["NBSG_test_connective"]
            List<Validation> validations = splitCondition.getValidations();

            Assert.assertNotEquals(0, validations.size());

            Collection<String> validationDataList = FileManager.readFile("/string_group/test_connective.dic");

            int validationIndex = 0;
            for (String validationData : validationDataList) {
                Validation validation = validations.get(validationIndex++);
                Assert.assertEquals(validationData, validation.getValue());
                Assert.assertEquals('N', validation.getMatchFlag());
                Assert.assertEquals('B', validation.getComparePosition());
            }
        }

    }

    @Test
    public void testUsePublicValidation() {
        String[] splitConditions = {"test_public_validation"};


        List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions);

        Assert.assertEquals(splitConditionList1.get(0).getValidations().get(0).getValue(), "하지만");

    }

    @Test(expected = RuntimeException.class)
    public void testDifferentUsePublicValidationOptions() {
        String[] splitConditions = {"test", "test_public_validation"};

        SplitConditionManager.getSplitConditions(splitConditions);


    }

    @Test(expected = RuntimeException.class)
    public void testDifferentSplitPositionTest() {
        String[] splitConditions = {"test", "test_front_split_position"};

        SplitConditionManager.getSplitConditions(splitConditions);


    }

    @Test(expected = RuntimeException.class)
    public void testInvalidProperties() {
        String[] splitConditions = {"test_invalid_properties"};
        SplitConditionManager.getSplitConditions(splitConditions);

    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUsePublicOptions() {
        String[] splitConditions = {"test_invalid_public_option"};

        SplitConditionManager.getSplitConditions(splitConditions);

    }




}
