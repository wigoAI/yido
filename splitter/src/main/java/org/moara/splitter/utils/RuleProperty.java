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
public class RuleProperty {
    protected final char flag;
    protected final char position;

    /**
     * 룰 속성 생성자
     * @param flag 룰에서의 일치여부, 사용 여부 등
     * @param position 룰의 사용, 비교 위치
     */
    public RuleProperty(char flag, char position) {
        this.flag = flag;
        this.position = position;

        if (!isValid()) {
            throw new RuntimeException("Invalid property");
        }
    }

    public boolean isValid() { return  (flag == 'N' || flag == 'Y') && (position == 'B' || position == 'F'); }
    public char getFlag() { return flag; }
    public char getPosition() { return position; }
}
