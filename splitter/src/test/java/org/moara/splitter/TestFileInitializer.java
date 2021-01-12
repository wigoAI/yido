package org.moara.splitter;


import org.moara.filemanager.FileManager;

import java.io.File;
import java.util.Collections;

public class TestFileInitializer {
    public static String testSplitter = "{\n" +
            "  \"id\": \"test\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "  \"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test\"],\n" +
            "  \"exceptions\": [\"bracket_exception\"]\n" +
            "}";
    public static String testException = "{\n" +
            "  \"id\": \"test\",\n" +
            "  \"value\": [\n" +
            "    {\n" +
            "      \"front\": \"[\",\n" +
            "      \"back\": \"]\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"front\": \"‘\",\n" +
            "      \"back\": \"’\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"front\": \"“\",\n" +
            "      \"back\": \"”\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"front\": \"{\",\n" +
            "      \"back\": \"}\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"front\": \"<\",\n" +
            "      \"back\": \">\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"front\": \"(\",\n" +
            "      \"back\": \")\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    public static String testCondition = "{\n" +
            "  \"id\": \"test\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";
    public static String testFrontSplitPosition = "{\n" +
            "  \"id\": \"test_front_split_position\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"F\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";
    public static String testPublicValidation = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": true,\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": []\n" +
            "}";
    public static String testInvalidProperties = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"K\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": []\n" +
            "}";
    public static String testInvalidPublicOption = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": \"U\",\n" +
            "  \"split_position\": \"F\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": []\n" +
            "}";
    public static String testConditionWithFrontValidations = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"terminator\",\n" +
            "  \"validations\": [\"NFSG_test_front_validation\", \"NBSG_test_back_validation\"]\n" +
            "}";

    public static String testRegxSplitter = "{\n" +
            "  \"id\": \"test_regx\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "\"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test_regx\"],\n" +
            "  \"exceptions\": [\"bracket_exception\"]\n" +
            "}";
    public static String testRegxCondition = "{\n" +
            "  \"id\": \"test_regx_condition\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"F\",\n" +
            "  \"value\": \"regx_test\",\n" +
            "  \"validations\": []\n" +
            "}";
    public static String testInvalidJson1 = "{\n" +
            "  \"id\": \"test_invalid1\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"minimum_spngth\": 5,\n" +
            "\"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test\"],\n" +
            "  \"exceptions\": [\"test\"]\n" +
            "}";
    public static String testInvalidJson2 = "{\n" +
            "  \"id\": \"test_invalid2\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "\"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test\"],\n" +
            "  \"exceptions\": \"test\"\n" +
            "}";
    public static String testNoConditionSplitter = "{\n" +
            "  \"id\": \"test_no_condition\",\n" +
            "  \"name\": \"test_no_condition\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "  \"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test_no_condition\"],\n" +
            "  \"exceptions\": [\"test\"]\n" +
            "}";
    public static String testNoValueCondition = "{\n" +
            "  \"id\": \"test_no_condition\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"F\",\n" +
            "  \"value\": \"test_no_condition\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";
    public static String testRuleLoopSplitter = "{\n" +
            "  \"id\": \"test_rule_loop\",\n" +
            "  \"name\": \"test_rule_loop\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "  \"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"terminator\", \"sns_terminator\", \"test_regx\"],\n" +
            "  \"exceptions\": [\"bracket_exception\"]" +
            "}";

    public static String testReloadSplitter = "{\n" +
            "  \"id\": \"test_reload_splitter\",\n" +
            "  \"name\": \"test_reload_splitter\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "  \"contain_split_condition\": true,\n" +
            "  \"conditions\": [\"test_reload_condition\"],\n" +
            "  \"exceptions\": [\"bracket_exception\"]\n" +
            "}";

    public static String testReloadCondition = "{\n" +
            "  \"id\": \"test_reload_condition\",\n" +
            "  \"use_public_validation\": false,\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"test_reload_string_group\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";

    public static String testReloadStringGroup = "다.\n" +
            "했다";


    public static String testConnectiveDic = "면";
    public static String testTerminator = "했다\n" +
            "다.";
    public static String testFrontValidation = "반가";
    public static String testBackValidation = "면";
    public static String testRegxString = "\\d+\\.";

    public static String testEvaluation = "apple.\n" + "orange.\n" + "banana.\n" + "melon.";

    public static void initialize() {
        createTestFiles("condition/test.json", testCondition);
        createTestFiles("condition/test_front_split_position.json", testFrontSplitPosition);
        createTestFiles("condition/test_public_validation.json", testPublicValidation);
        createTestFiles("condition/test_invalid_properties.json", testInvalidProperties);
        createTestFiles("condition/test_invalid_public_option.json", testInvalidPublicOption);
        createTestFiles("condition/test_condition_with_validations.json", testConditionWithFrontValidations);
        createTestFiles("condition/test_no_condition.json", testNoValueCondition);
        createTestFiles("condition/test_regx.json", testRegxCondition);
        createTestFiles("condition/test_reload_condition.json", testReloadCondition);

        createTestFiles("string_group/test_connective.dic", testConnectiveDic);
        createTestFiles("string_group/test_terminator.dic", testTerminator);
        createTestFiles("string_group/test_front_validation.dic", testFrontValidation);
        createTestFiles("string_group/test_back_validation.dic", testBackValidation);
        createTestFiles("string_group/test_no_condition.dic", "removed");
        createTestFiles("string_group/regx_test.dic", testRegxString);
        createTestFiles("string_group/test_reload_string_group.dic", testReloadStringGroup);

        createTestFiles("splitter/test.json", testSplitter);
        createTestFiles("splitter/test_regx.json", testRegxSplitter);
        createTestFiles("splitter/test_invalid1.json", testInvalidJson1);
        createTestFiles("splitter/test_invalid2.json", testInvalidJson2);
        createTestFiles("splitter/test_no_condition.json", testNoConditionSplitter);
        createTestFiles("splitter/test_no_condition.json", testNoConditionSplitter);
        createTestFiles("splitter/test_rule_loop.json", testRuleLoopSplitter);
        createTestFiles("splitter/test_reload_splitter.json", testReloadSplitter);

        createTestFiles("exception/test.json", testException);
        createTestFiles("evaluation/test_answer.txt", testEvaluation);

    }

    public static void tearDown() {
        FileManager.deleteFile("condition/test.json");
        FileManager.deleteFile("condition/test_front_split_position.json");
        FileManager.deleteFile("condition/test_public_validation.json");
        FileManager.deleteFile("condition/test_invalid_properties.json");
        FileManager.deleteFile("condition/test_invalid_public_option.json");
        FileManager.deleteFile("condition/test_condition_with_validations.json");
        FileManager.deleteFile("condition/test_regx.json");
        FileManager.deleteFile("condition/test_no_condition.json");
        FileManager.deleteFile("splitter/test.json");
        FileManager.deleteFile("splitter/test_invalid1.json");
        FileManager.deleteFile("splitter/test_invalid2.json");
        FileManager.deleteFile("splitter/test_no_condition.json");
        FileManager.deleteFile("splitter/test_rule_loop.json");
        FileManager.deleteFile("splitter/test_regx.json");
        FileManager.deleteFile("string_group/test_connective.dic");
        FileManager.deleteFile("string_group/test_terminator.dic");
        FileManager.deleteFile("string_group/test_back_validation.dic");
        FileManager.deleteFile("string_group/test_front_validation.dic");
        FileManager.deleteFile("string_group/regx_test.dic");
        FileManager.deleteFile("exception/test.json");
        FileManager.deleteFile("evaluation/test_answer.txt");
        FileManager.deleteFile("splitter/test_reload_splitter.json");
        FileManager.deleteFile("string_group/test_reload_string_group.dic");
        FileManager.deleteFile("condition/test_reload_condition.json");
    }
    private static void createTestFiles(String fileName, String value) {
        FileManager.writeFile(fileName, Collections.singletonList(value));
    }
}
