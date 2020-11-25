/*
 * Copyright (C) 2020 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moara.splitter.utils.file;

import org.moara.splitter.role.SplitConditionManager;
import org.moara.splitter.role.ValidationManager;
import org.moara.splitter.utils.RoleProperty;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 문자열 그룹 관리자
 */
public class StringGroupManager extends FileManager{
    private static final String STRING_GROUP_PATH = ABSTRACT_PATH + "string_group" + pathSeparator;

    private StringGroupManager() { }

    /**
     * 해당 카테고리에 존재하는 문자열 그룹 반환
     * @param category 카테고리
     * @return 해당 카테고리에 존재하는 문자열 그룹
     */
    public static List<String> getStringGroupList(String category) {
        String fileExtension = ".role";
        if (category.equals("meta")) {
            fileExtension = ".json";
        }
        return getFileList(STRING_GROUP_PATH + category, fileExtension).stream().map(File::getName).collect(Collectors.toList());

    }


    private static List<File> getFileList(String path, String fileExtension){
        List<File> fileList = new ArrayList<>();
        File file = new File(path);

        addFiles(fileList, file);

        List<File> resultFileList = new ArrayList<>();
        for(File f : fileList){
            if(f.isDirectory()){
                continue;
            }

            if(f.getName().endsWith(fileExtension)){
                resultFileList.add(f);
            }
        }
        fileList.clear();
        fileList = null;

        return resultFileList;
    }

    private static void addFiles(List<File> fileList, File file){
        fileList.add(file);
        if(file.isDirectory()){
            File [] files = file.listFiles();

            //noinspection ConstantConditions
            for(File f : files){
                addFiles(fileList, f);
            }
        }
    }

    /**
     * 문자열 그룹 생성
     * TODO 1. 이상한 이름으로 생성되는 룰 막기
     *
     * @param category 문자열 그룹을 생성할 카테고리 위치
     * @param stringGroupName 생성할 문자열 그룹 이름
     * @param values 문자열 그룹 내용
     */
    public static void createStringGroup(String category, String stringGroupName, String[] values) {

        new RoleProperty(stringGroupName);
        if (category.equals("split_condition")) {
            SplitConditionManager.checkRoleName(stringGroupName);
        } else if (category.equals("validation")) {
            ValidationManager.checkRoleName(stringGroupName);
        } else {
            throw new RuntimeException("Invalid category name : " + category);
        }

        writeFile( "string_group" + pathSeparator + category + pathSeparator + stringGroupName + ".role",
                Arrays.asList(values.clone()));
    }

    /**
     * 문자열 그룹 제거
     *
     * TODO 1. 카테고리 위치 입력 안해도 제거할 수 있게 변경
     * @param category 제거할 문자열 그룹이 있는 카테고리 위치
     * @param stringGroupName 제거할 문자열 그룹
     * @return 제거 성공 여부
     */
    public static boolean deleteStringGroup(String category, String stringGroupName) {
        return deleteFile( "string_group" + pathSeparator + category + pathSeparator + stringGroupName + ".role");
    }
}
