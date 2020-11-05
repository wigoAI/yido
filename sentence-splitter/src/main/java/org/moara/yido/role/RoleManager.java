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
package org.moara.yido.role;

import org.moara.yido.file.FileManager;
import org.moara.yido.file.FileManagerImpl;

import java.util.HashSet;
import java.util.List;

/**
 * 메타 데이터 관리자
 *
 * TODO 1. Role 추가기능 만들기
 *          - 업데이트
 *              - 서버에서 업데이트
 *          - 삭제
 *              - 원하는 룰 삭제
 *              - 전체 삭제
 *          - 룰 추가
 *              - 임시 추가
 *              - 파일에 추가
 *      2. 존재하지 않는 룰 관리자를 생성할 때 초기화 과정
 *          - dic 파일 생성
 *          - dic 폴더 생성
 *      3. 팩토리 패턴으로 생성해야하는가?
 *      4. 룰 종류별로 나눠야 하는가?
 *          - String 매개변수로 동작하게 변경하자
 *
 *
 *
 * @author 조승현
 */
public class RoleManager {

    String dicPath;
    protected HashSet<String> terminator = new HashSet<>();
    protected HashSet<String> connective = new HashSet<>();
    protected HashSet<String> exception = new HashSet<>();
    protected HashSet<String> regx = new HashSet<>();
    FileManager fileManager = new FileManagerImpl();

    protected RoleManager(String roleName) {
        this.dicPath = "/dic/" + roleName + "/";
        initRole("terminator");
        initRole("connective");
        initRole("exception");
        initRole("regx");
    }

    /**
     * 룰 데이터 반환
     * @return {@code HashSet<String>}
     */
    public HashSet<String> getRole(String roleName) {

        switch (roleName) {
            case "terminator":
                return terminator;
            case "connective":
                return connective;
            case "exception":
                return exception;
            case "regx":
                return regx;
            default:
                throw new RuntimeException("Invalid role name : " + roleName);
        }

    }


    /**
     * 로컬 사전 파일에 룰을 추가한다.
     * @param dicName 존재하는 사전 이름 : connective, exception, regx, terminator
     * @param roles 추가할 룰 내용
     */
    public void addRolesToLocal(String dicName, List<String> roles ) {
        fileManager.addLine(dicPath + dicName + ".dic", roles);
    }

    /**
     * 임시로 사용할 룰을 추가한다. 메모리에 저장되며 프로그램 종료 시 사라진다.
     * @param dicName 존재하는 사전 이름 : connective, exception, regx, terminator
     * @param roles 추가할 룰 내용
     */
    public void addRolesToMemory(String dicName, List<String> roles ) {
        switch (dicName) {
            case "terminator":
                terminator.addAll(roles);
                break;
            case "connective":
                connective.addAll(roles);
                break;
            case "exception":
                exception.addAll(roles);
                break;
            case "regx":
                regx.addAll(roles);
                break;
            default:
                throw new RuntimeException("Invalid diction name : " + dicName);

        }
    }

    /**
     * 초기화 과정에 문제가 있다.
     * @param roleName
     */
    protected void initRole(String roleName) {
        fileManager.readFile(dicPath + roleName + ".dic");
        HashSet<String> role;
        switch (roleName) {
            case "terminator":
                role = terminator;
                break;
            case "connective":
                role = connective;
                break;
            case "exception":
                role = exception;
                break;
            case "regx":
                role = regx;
                break;
            default:
                throw new RuntimeException("Invalid role name : " + roleName);

        }
        role.addAll(fileManager.getFile());
        role.remove(null);

    }





}
