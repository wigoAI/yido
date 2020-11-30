package org.moara.splitter;

import org.moara.splitter.utils.file.FileManager;

import java.util.Collections;

public class TestFileInitializer {
    public static String testSplitter = "{\n" +
            "  \"id\": \"test\",\n" +
            "  \"name\": \"test\",\n" +
            "  \"minimum_split_length\": 5,\n" +
            "  \"conditions\": [\"test\"],\n" +
            "  \"exceptions\": [\"test\"]\n" +
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
            "  \"use_public_validation\": \"N\",\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";
    public static String testFrontSplitPosition = "{\n" +
            "  \"id\": \"test_front_split_position\",\n" +
            "  \"use_public_validation\": \"N\",\n" +
            "  \"split_position\": \"F\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": [\"NBSG_test_connective\"]\n" +
            "}";
    public static String testPublicValidation = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": \"Y\",\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"test_terminator\",\n" +
            "  \"validations\": []\n" +
            "}";
    public static String testInvalidProperties = "{\n" +
            "  \"id\": \"test_public_validation\",\n" +
            "  \"use_public_validation\": \"N\",\n" +
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
            "  \"use_public_validation\": \"N\",\n" +
            "  \"split_position\": \"B\",\n" +
            "  \"value\": \"terminator\",\n" +
            "  \"validations\": [\"NFSG_test_front_validation\", \"NBSG_test_back_validation\"]\n" +
            "}";


    public static String testConnectiveDic = "면";
    public static String testTerminator = "했다\n" +
            "다.";
    public static String testFrontValidation = "반가";
    public static String testBackValidation = "면";

    public static void initialize() {
        createTestFiles("condition/test.json", testCondition);
        createTestFiles("condition/test_front_split_position.json", testFrontSplitPosition);
        createTestFiles("condition/test_public_validation.json", testPublicValidation);
        createTestFiles("condition/test_invalid_properties.json", testInvalidProperties);
        createTestFiles("condition/test_invalid_public_option.json", testInvalidPublicOption);
        createTestFiles("condition/test_condition_with_validations.json", testConditionWithFrontValidations);
        createTestFiles("string_group/test_connective.dic", testConnectiveDic);
        createTestFiles("string_group/test_terminator.dic", testTerminator);
        createTestFiles("string_group/test_front_validation.dic", testFrontValidation);
        createTestFiles("string_group/test_back_validation.dic", testBackValidation);
        createTestFiles("splitter/test.json", testSplitter);
        createTestFiles("exception/test.json", testException);
    }

    public static void tearDown() {
        FileManager.deleteFile("condition/test.json");
        FileManager.deleteFile("condition/test_front_split_position.json");
        FileManager.deleteFile("condition/test_public_validation.json");
        FileManager.deleteFile("splitter/test.json");
        FileManager.deleteFile("exception/test.json");
        FileManager.deleteFile("string_group/test_connective.dic");
        FileManager.deleteFile("string_group/test_terminator.dic");
        FileManager.deleteFile("condition/test_invalid_properties.json");
        FileManager.deleteFile("condition/test_invalid_public_option.json");
        FileManager.deleteFile("condition/test_condition_with_validations.json");
        FileManager.deleteFile("string_group/test_back_validation.dic");
        FileManager.deleteFile("string_group/test_front_validation.dic");
    }
    private static void createTestFiles(String fileName, String value) {
        FileManager.writeFile(fileName, Collections.singletonList(value));
    }
}