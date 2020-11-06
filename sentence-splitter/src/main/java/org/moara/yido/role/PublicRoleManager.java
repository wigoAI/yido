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
 * 모든 룰 메니저에서 사용 가능한 공용 룰 관리자
 *
 * @author 조승현
 */
public class PublicRoleManager extends RoleManager {

    private static final PublicRoleManager PUBLIC_ROLE_MANAGER = new PublicRoleManager("public");

    private PublicRoleManager(String roleManagerName) {
        super(roleManagerName);
    }

    /**
     * 룰 관리자 인스턴스 반환
     * @return RoleManager instance
     */
    public static RoleManager getRoleManager() {
        return PUBLIC_ROLE_MANAGER;
    }
}
