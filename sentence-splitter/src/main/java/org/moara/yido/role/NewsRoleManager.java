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

/**
 * 뉴스 메타데이터 관리자 구현체'
 *
 * @author 조승현
 */
public class NewsRoleManager extends RoleManager {
    private static final NewsRoleManager NEWS_ROLE_MANAGER = new NewsRoleManager("news");

    private NewsRoleManager(String roleManagerName) { super(roleManagerName); }

    /**
     * 뉴스 메타데이터 관리자 인스턴스 반환
     * @return RoleManager instance
     */
    public static RoleManager getRoleManager() { return NEWS_ROLE_MANAGER; }



}
