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


/**
 *
 * 기본 메타데이터 관리자 추상체
 * @author 조승현
 *
 */
public class BasicRoleManager implements RoleManager {
    private static final BasicRoleManager BASIC_ROLE_MANAGER = new BasicRoleManager();
    private final HashSet<String> basicConnective = new HashSet<>();
    private final HashSet<String> basicTerminator = new HashSet<>();
    private final HashSet<String> basicException = new HashSet<>();
    private final FileManager fileManager = new FileManagerImpl();

    private BasicRoleManager() { }

    /**
     * 기본 메타데이터 관리자 인스턴스 반환
     * @return BasicRoleManager
     */
    public static BasicRoleManager getRoleManager() { return BASIC_ROLE_MANAGER; }

    @Override
    public HashSet<String> getTerminator() {
        initBasicTerminator();

        return basicTerminator;
    }

    @Override
    public HashSet<String> getException() {

        initBasicException();
        return basicException;

    }

    @Override
    public HashSet<String> getConnective() {

        initBasicConnective();
        return basicConnective;

    }

    private void initBasicConnective() {
        if(basicConnective.size() == 0) {
            fileManager.readFile(dicPath + "connective.dic");
            basicConnective.addAll(fileManager.getFile());
        }
    }
    private void initBasicTerminator() {
        if(basicTerminator.size() == 0) {
            fileManager.readFile(dicPath + "terminator.dic");
            basicTerminator.addAll(fileManager.getFile());
        }
    }

    private void initBasicException() {
        if(basicException.size() == 0) {
            fileManager.readFile(dicPath + "exception.dic");
            basicException.addAll(fileManager.getFile());
        }
    }



}
