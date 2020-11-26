package org.moara.splitter.role;

import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.utils.file.FileManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SplitConditionTest {

    @Test
    public void testCreateRoleObject() {
        // terminator
        List<Validation> validations1 = new ArrayList<>();
        SplitCondition splitCondition1 = new SplitCondition("다.", validations1, 'N', 'B');
        Assert.assertEquals("다.", splitCondition1.getValue());
        Assert.assertEquals('N', splitCondition1.getUsePublicValidation());
        Assert.assertEquals('B', splitCondition1.getSplitPosition());


        // number
        List<Validation> validations2 = new ArrayList<>();
        SplitCondition splitCondition2 = new SplitCondition("1.", validations2, 'Y', 'F');
        Assert.assertEquals("1.", splitCondition2.getValue());
        Assert.assertEquals('Y', splitCondition2.getUsePublicValidation());
        Assert.assertEquals('F', splitCondition2.getSplitPosition());
    }

    @Test
    public void testCheckPatternSplitCondition() {
        SplitCondition splitCondition1 = new SplitCondition("\\d+\\.", new ArrayList<>(), 'N', 'F', true);
        Assert.assertTrue(splitCondition1.isPattern());

        SplitCondition splitCondition2 = new SplitCondition("\\d+\\.", new ArrayList<>(), 'N', 'F', false);
        Assert.assertFalse(splitCondition2.isPattern());

    }

    @Test
    public void testSplitConditionManager() {
        String[] validationList = {"V_N_B_001"};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions(new String[] {"SP_N_B_001"}, validationList);

        Collection<String> dataList = FileManager.readFile("/string_group/split_condition/SP_N_B_001.role");

        int splitConditionIndex = 0;
        for (String data : dataList) {
            SplitCondition splitCondition = splitConditions.get(splitConditionIndex++);
            Assert.assertEquals(data, splitCondition.getValue());
            Assert.assertEquals('N', splitCondition.getUsePublicValidation());
            Assert.assertEquals('B', splitCondition.getSplitPosition());


            List<Validation> validations = splitCondition.getValidations();

            Assert.assertNotEquals(0, validations.size());

            Collection<String> validationDataList = FileManager.readFile("/string_group/validation/V_N_B_001.role");

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
        String[] splitConditions = {"SP_Y_B_TEST_1", "SP_Y_B_TEST_2"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};

        createTestFiles("split_condition", splitConditions);
        createTestFiles("validation", validations);

        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {
            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);

        }

    }

    @Test(expected = RuntimeException.class)
    public void testDifferentUsePublicValidationOptions() {
        String[] splitConditions = {"SP_Y_B_TEST_1", "SP_N_B_TEST_2"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};

        createTestFiles("split_condition", splitConditions);
        createTestFiles("validation", validations);

        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {
            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);
        }

    }

    @Test(expected = RuntimeException.class)
    public void testDifferentSplitPositionTest() {
        String[] splitConditions = {"SP_N_F_TEST_1", "SP_N_B_TEST_2"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};

        createTestFiles("split_condition", splitConditions);
        createTestFiles("validation", validations);

        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {

            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);
        }

    }

    @Test(expected = RuntimeException.class)
    public void testInvalidRoleName() {
        String[] splitConditions = {"POW_N_F_TEST_1"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};
        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {

            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);
        }


    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUsePublicOptions() {
        String[] splitConditions = {"SP_K_F_TEST_1"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};

        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {
            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);

        }


    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSplitPositionOptions() {
        String[] splitConditions = {"SP_N_K_TEST_1"};
        String[] validations = {"V_N_B_TEST_1", "V_N_B_TEST_2"};

        try {

            List<SplitCondition> splitConditionList1 = SplitConditionManager.getSplitConditions(splitConditions, validations);
        } catch (RuntimeException e) {
            throw new RuntimeException();
        } finally {
            tearDown("split_condition", splitConditions);
            tearDown("validation", validations);

        }


    }

    @Test
    public void testCreateSplitConditionWithoutValidations() {
        SplitCondition splitCondition1 = new SplitCondition("다.", 'N', 'B');
        SplitCondition splitCondition2 = new SplitCondition("다.", 'N', 'B', true);

    }

    private void createTestFiles(String path, String[] files) {
        for (String file : files) {
            FileManager.writeFile("string_group/" + path + "/" + file + ".role", new ArrayList<>());
        }
    }

    private void tearDown(String path, String[] files) {
        for (String file : files) {
            FileManager.deleteFile("string_group/" + path + "/" + file + ".role");
        }
    }
}
