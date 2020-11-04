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
 *
 * TODO 1. 싱글톤 형태의 중복 코드 제거방법
 *          - 상속이 안된다.
 *
 * @author 조승현
 *
 */
public class BasicRoleManager implements RoleManager {
    private static final BasicRoleManager BASIC_ROLE_MANAGER = new BasicRoleManager();
    private final HashSet<String> connective = new HashSet<>();
    private final HashSet<String> terminator = new HashSet<>();
    private final HashSet<String> exception = new HashSet<>();
    private final HashSet<String> regx = new HashSet<>();
    private final FileManager fileManager = new FileManagerImpl();

    private BasicRoleManager() { }

    /**
     * 기본 메타데이터 관리자 인스턴스 반환
     * @return BasicRoleManager
     */
    public static BasicRoleManager getRoleManager() { return BASIC_ROLE_MANAGER; }

    @Override
    public HashSet<String> getTerminator() {
        if(terminator.size() == 0) { initTerminator(); }
        return terminator;
    }

    @Override
    public HashSet<String> getException() {
        if(exception.size() == 0) { initException(); }
        return exception;
    }

    @Override
    public HashSet<String> getConnective() {
        if (connective.size() == 0){ initConnective(); }
        return connective;
    }

    @Override
    public HashSet<String> getRegx() {
        if(regx.size() == 0) { initRegx(); }

        return regx;
    }

    private void initConnective() {
        fileManager.readFile(dicPath + "connective.dic");
        connective.addAll(fileManager.getFile());
        connective.remove(null);

    }
    private void initTerminator() {
        fileManager.readFile(dicPath + "terminator.dic");
        terminator.addAll(fileManager.getFile());
        terminator.remove(null);
    }

    private void initException() {
        fileManager.readFile(dicPath + "exception.dic");
        exception.addAll(fileManager.getFile());
        exception.remove(null);

    }

    private void initRegx() {
        fileManager.readFile(dicPath + "regx.dic");
        regx.addAll(fileManager.getFile());
        regx.remove(null);

    }



}
