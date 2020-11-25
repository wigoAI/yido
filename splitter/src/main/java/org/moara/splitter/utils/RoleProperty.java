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
package org.moara.splitter.utils;


/**
 * 룰 속성 값 유틸
 *
 * @author wjrmffldrhrl
 */
public class RoleProperty {
    private final String type;
    private final char flag;
    private final char position;

    /**
     * 룰 이름으로부터 속성 값 유틸 생성
     * @param roleName 룰 이름
     */
    public RoleProperty(String roleName) {
        String[] roleInfoArray = roleName.split("_");

        this.type = roleInfoArray[0];
        this.flag = roleInfoArray[1].charAt(0);
        this.position = roleInfoArray[2].charAt(0);

        if (!isValid()) {
            throw new RuntimeException("Invalid role name : " + roleName);
        }
    }

    public boolean isValid() {
        return  (flag == 'N' || flag == 'Y') && (position == 'B' || position == 'F');
    }

    public String getType() {
        return type;
    }

    public char getFlag() {
        return flag;
    }

    public char getPosition() {
        return position;
    }
}
