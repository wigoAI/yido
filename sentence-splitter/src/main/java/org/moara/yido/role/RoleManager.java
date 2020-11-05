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
 *
 *
 *
 * @author 조승현
 */
public class RoleManager {

    String dicPath;
    protected HashSet<String> connective = new HashSet<>();
    protected HashSet<String> terminator = new HashSet<>();
    protected HashSet<String> exception = new HashSet<>();
    protected HashSet<String> regx = new HashSet<>();
    FileManager fileManager = new FileManagerImpl();

    protected RoleManager(String roleName) {
        this.dicPath = "/dic/" + roleName + "/";
    }

    /**
     * 구분자 메타 데이터 반환
     * @return {@code HashSet<String>}
     */
    public HashSet<String> getTerminator() {
        if(terminator.size() == 0) { initTerminator(); }
        return terminator;
    }

    /**
     * 예외영역 메타 데이터 반환
     * @return {@code HashSet<String>}
     */
    public HashSet<String> getException() {
        if(exception.size() == 0) { initException(); }
        return exception;
    }

    /**
     * 연결어 메타 데이터 반환
     * @return {@code HashSet<String>}
     */
    public HashSet<String> getConnective() {
        if (connective.size() == 0){ initConnective(); }
        return connective;
    }

    /**
     * 정규식 반환
     * @return {@code HashSet<String>}
     */
    public HashSet<String> getRegx() {
        if(regx.size() == 0) { initRegx(); }

        return regx;
    }

    protected void initConnective() {
        fileManager.readFile(dicPath + "connective.dic");
        connective.addAll(fileManager.getFile());
        connective.remove(null);
    }

    protected void initTerminator() {
        fileManager.readFile(dicPath + "terminator.dic");
        terminator.addAll(fileManager.getFile());
        terminator.remove(null);
    }

    protected void initException() {
        fileManager.readFile(dicPath + "exception.dic");
        exception.addAll(fileManager.getFile());
        exception.remove(null);
    }

    protected void initRegx() {
        fileManager.readFile(dicPath + "regx.dic");
        regx.addAll(fileManager.getFile());
        regx.remove(null);

    }





}
