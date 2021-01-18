package org.moara.splitter;



import org.moara.splitter.utils.FileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        createTestFiles("splitter/condition/test.json", testCondition);
        createTestFiles("splitter/condition/test_front_split_position.json", testFrontSplitPosition);
        createTestFiles("splitter/condition/test_public_validation.json", testPublicValidation);
        createTestFiles("splitter/condition/test_invalid_properties.json", testInvalidProperties);
        createTestFiles("splitter/condition/test_invalid_public_option.json", testInvalidPublicOption);
        createTestFiles("splitter/condition/test_condition_with_validations.json", testConditionWithFrontValidations);
        createTestFiles("splitter/condition/test_no_condition.json", testNoValueCondition);
        createTestFiles("splitter/condition/test_regx.json", testRegxCondition);
        createTestFiles("splitter/condition/test_reload_condition.json", testReloadCondition);

        createTestFiles("splitter/string_group/test_connective.dic", testConnectiveDic);
        createTestFiles("splitter/string_group/test_terminator.dic", testTerminator);
        createTestFiles("splitter/string_group/test_front_validation.dic", testFrontValidation);
        createTestFiles("splitter/string_group/test_back_validation.dic", testBackValidation);
        createTestFiles("splitter/string_group/test_no_condition.dic", "removed");
        createTestFiles("splitter/string_group/regx_test.dic", testRegxString);
        createTestFiles("splitter/string_group/test_reload_string_group.dic", testReloadStringGroup);

        createTestFiles("splitter/splitter/test.json", testSplitter);
        createTestFiles("splitter/splitter/test_regx.json", testRegxSplitter);
        createTestFiles("splitter/splitter/test_invalid1.json", testInvalidJson1);
        createTestFiles("splitter/splitter/test_invalid2.json", testInvalidJson2);
        createTestFiles("splitter/splitter/test_no_condition.json", testNoConditionSplitter);
        createTestFiles("splitter/splitter/test_no_condition.json", testNoConditionSplitter);
        createTestFiles("splitter/splitter/test_rule_loop.json", testRuleLoopSplitter);
        createTestFiles("splitter/splitter/test_reload_splitter.json", testReloadSplitter);

        createTestFiles("splitter/exception/test.json", testException);
        createTestFiles("splitter/evaluation/test_answer.txt", testEvaluation);

    }

    public static void tearDown() {
        deleteFile("splitter/condition/test.json");
        deleteFile("splitter/condition/test_front_split_position.json");
        deleteFile("splitter/condition/test_public_validation.json");
        deleteFile("splitter/condition/test_invalid_properties.json");
        deleteFile("splitter/condition/test_invalid_public_option.json");
        deleteFile("splitter/condition/test_condition_with_validations.json");
        deleteFile("splitter/condition/test_regx.json");
        deleteFile("splitter/condition/test_no_condition.json");
        deleteFile("splitter/splitter/test.json");
        deleteFile("splitter/splitter/test_invalid1.json");
        deleteFile("splitter/splitter/test_invalid2.json");
        deleteFile("splitter/splitter/test_no_condition.json");
        deleteFile("splitter/splitter/test_rule_loop.json");
        deleteFile("splitter/splitter/test_regx.json");
        deleteFile("splitter/string_group/test_connective.dic");
        deleteFile("splitter/string_group/test_terminator.dic");
        deleteFile("splitter/string_group/test_back_validation.dic");
        deleteFile("splitter/string_group/test_front_validation.dic");
        deleteFile("splitter/string_group/regx_test.dic");
        deleteFile("splitter/exception/test.json");
        deleteFile("splitter/evaluation/test_answer.txt");
        deleteFile("splitter/splitter/test_reload_splitter.json");
        deleteFile("splitter/string_group/test_reload_string_group.dic");
        deleteFile("splitter/condition/test_reload_condition.json");
    }


    public static void createTestFiles(String fileName, String value) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileReader.ABSTRACT_PATH + fileName))){
            bw.write(value + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * File 삭제
     * @param fileName 삭제 할 파일 이름
     * @return 파일 삭제 성공 여부
     */
    public static void deleteFile(String fileName) {
        File file = new File(FileReader.ABSTRACT_PATH + fileName);

        file.delete();

    }
}
