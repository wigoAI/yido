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
package org.moara.splitter.role;

import org.moara.splitter.utils.file.FileManager;
import org.moara.splitter.utils.file.FileManagerImpl;

import java.util.*;

/**
 * 메타 데이터 관리자
 *
 * TODO 1. DB 설계에 맞게 구조 개선
 *          - 모든 정보는 한 개의 디렉터리에서 관리하며 그룹으로 존재한다.
 *          - Processor 에서 설정값에 맞는 문자 정보를 가져와서 처리하는 방식
 *          - 구분기는 특정 구분 조건들을 사용하고 해당 구분 조건들은 특정한 유효성을 가지고 있다.
 *
 * @author wjrmffldrhrl
 */
public class RoleManager {

    private final String rolePath;
    private final HashMap<String,HashSet<String>> roleMap = new HashMap<>();
    private final HashMap<String, Boolean> isInitialized = new HashMap<>();
    private final List<String> roleNames = new ArrayList<>();


    protected FileManager fileManager = new FileManagerImpl();

    protected RoleManager(String roleManagerName) {
        this.rolePath = "/role/" + roleManagerName + "/";

        String[] roleNames  = {"terminator", "connective", "exception", "regx"};

        for (String roleName : roleNames) {
            roleMap.put(roleName, new HashSet<>());
            isInitialized.put(roleName, false);
        }

        this.roleNames.addAll(Arrays.asList(roleNames));
    }

    /**
     * 임시로 사용할 룰을 추가한다. 메모리에 저장되며 프로그램 종료 시 사라진다.
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     * @param roles 추가할 룰 내용
     */
    public void addRolesToMemory(String roleName, List<String> roles ) {
        getRole(roleName).addAll(roles);
    }

    /**
     * 로컬 룰 파일에 룰을 추가한다.
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     * @param roles 추가할 룰 내용
     */
    public void addRolesToLocal(String roleName, List<String> roles ) {
        fileManager.addLine(rolePath + roleName + ".role", roles);
    }

    /**
     * 메모리에 업로드 된 룰에서 원하는 룰을 임시로 제거한다. 프로그램 종료 시 초기화된다.
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     * @param roles 제거할 룰 내용
     */
    public void removeRolesInMemory(String roleName, List<String> roles ) {
        getRole(roleName).removeAll(roles);

    }

    /**
     * 로컬 룰 파일에서 룰을 제거한다.
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     * @param roles 제거할 룰 내용
     */
    public void removeRolesInLocal(String roleName, List<String> roles ) {

        HashSet<String> role = getRole(roleName);
        role.removeAll(roles);

        fileManager.writeFile(rolePath + roleName + ".role", role);

    }

    /**
     * 로컬 룰 파일로 룰 초기화
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     */
    public void initRole(String roleName) {
        if (!roleNames.contains(roleName)) { throw new RuntimeException("Invalid role name : " + roleName); }

        HashSet<String> role = roleMap.get(roleName);

        role.clear();
        role.addAll(fileManager.readFile(rolePath + roleName + ".role"));
        role.remove(null);
        isInitialized.put(roleName, true);

    }

    /**
     * 모든 룰 초기화
     */
    public void initAllRoles() {
        for (String roleName : roleNames) {
            initRole(roleName);
        }
    }


    /**
     * 원하는 룰 획득
     * 해당 룰은 싱글톤으로 생성되어 참조변수 자체를 반환하기 때문에 반환받은 룰의 내용을 변경하면
     * 동일한 룰 관리자를 사용하는 문장 분리기는 모두 영향을 받는다.
     * @param roleName 존재하는 룰 이름 : connective, exception, regx, terminator
     * @return 선택한 룰의 참조변수 HashSet
     */
    public HashSet<String> getRole(String roleName) {
        if (!roleNames.contains(roleName)) { throw new RuntimeException("Invalid role name : " + roleName); }
        if(!isInitialized.get(roleName)) { initRole(roleName); }

        return roleMap.get(roleName);
    }


}
