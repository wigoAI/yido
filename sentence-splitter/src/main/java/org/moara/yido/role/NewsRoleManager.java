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
 * 뉴스 메타데이터 관리자 구현체
 * @author 조승현
 */
public class NewsRoleManager implements RoleManager {
    private static final NewsRoleManager NEWS_ROLE_MANAGER = new NewsRoleManager();
    private final HashSet<String> newsConnective = new HashSet<>();
    private final HashSet<String> newsTerminator = new HashSet<>();
    private final HashSet<String> newsException = new HashSet<>();
    private final FileManager fileManager = new FileManagerImpl();

    private NewsRoleManager() { }

    /**
     * 뉴스 메타데이터 관리자 인스턴스 반환
     * @return BasicRoleManager
     */
    public static NewsRoleManager getRoleManager() { return NEWS_ROLE_MANAGER; }

    @Override
    public HashSet<String> getTerminator() {
        initTerminator();
        return newsTerminator;
    }

    @Override
    public HashSet<String> getException() {
        initException();
        return newsException;
    }

    @Override
    public HashSet<String> getConnective() {
        initConnective();
        return newsConnective;
    }

    private void initConnective() {
        if(newsConnective.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "connective.dic");
            newsConnective.addAll(fileManager.getFile());
        }
    }

    private void initTerminator() {
        if(newsTerminator.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "terminator.dic");
            newsTerminator.addAll(fileManager.getFile());
        }
    }

    private void initException() {
        if(newsException.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "exception.dic");
            newsException.addAll(fileManager.getFile());
        }
    }
}
