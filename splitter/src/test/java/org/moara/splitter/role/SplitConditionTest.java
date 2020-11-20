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
    public void testSplitConditionManager() {
        String[] validationList = {"V_N_B_001"};
        List<SplitCondition> splitConditions = SplitConditionManager.getSplitConditions("SP_N_B_001", validationList);

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
}
