package org.moara.splitter.utils.file;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StringGroupManagerTest {
    @Test
    public void testGetStringGroupList() {
        String[] roleFilesAnswer = {"RSP_N_F_001.role", "SP_N_B_001.role", "SP_N_B_002.role", "SP_N_B_TEST.role", "SP_N_F_NUM.role"};
        String[] jsonFilesAnswer = {"M_different_side_bracket.json", "M_same_side_bracket.json"};

        int answerIndex = 0;
        for (String stringGroup : StringGroupManager.getStringGroupList("split_condition")) {
            Assert.assertEquals(stringGroup, roleFilesAnswer[answerIndex++]);
        }

        answerIndex = 0;
        for (String stringGroup : StringGroupManager.getStringGroupList("meta")) {
            Assert.assertEquals(stringGroup, jsonFilesAnswer[answerIndex++]);
        }
    }

    @Test
    public void testCreateSplitCondition() {
        String category = "split_condition";
        String stringGroupName = "SP_N_B_create";
        String[] values = {"다.", "면..."};

        StringGroupManager.createStringGroup(category, stringGroupName, values);

        Assert.assertArrayEquals(values,
                FileManager.readFile("string_group/" + category + "/" + stringGroupName + ".role").toArray());

        FileManager.deleteFile("string_group/" + category + "/" + stringGroupName + ".role");


        category = "split_condition";
        stringGroupName = "SP_K_B_create";
        values = new String[] {"다.", "면..."};
        boolean exceptionFlag = false;
        try {
            StringGroupManager.createStringGroup(category, stringGroupName, values);

        } catch (RuntimeException e) {
            exceptionFlag = true;
        }

        Assert.assertTrue(exceptionFlag);
        FileManager.deleteFile("string_group/" + category + "/" + stringGroupName + ".role");

    }

    @Test
    public void testCreateValidation() {
        String category = "split_condition";
        String stringGroupName = "SP_N_B_create";
        String[] values = {"다.", "면..."};


        StringGroupManager.createStringGroup(category, stringGroupName, values);

        Assert.assertArrayEquals(values,
                FileManager.readFile("string_group/" + category + "/" + stringGroupName + ".role").toArray());

        FileManager.deleteFile("string_group/" + category + "/" + stringGroupName + ".role");


        category = "split_condition";
        stringGroupName = "SP_N_B_create";
        values = new String[] {"다.", "면..."};
        StringGroupManager.createStringGroup(category, stringGroupName, values);

        Assert.assertArrayEquals(values,
                FileManager.readFile("string_group/" + category + "/" + stringGroupName + ".role").toArray());

        FileManager.deleteFile("string_group/" + category + "/" + stringGroupName + ".role");

    }

}
