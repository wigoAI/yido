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
package org.moara.splitter;

import org.moara.splitter.utils.Sentence;

/**
 * 구분기 추상체
 *
 * TODO 1. 구분기에 할당된 구분 조건이나 유효성 데이터를 어떻게 사용자가 수정할 수 있을지 정하기
 *          - 직접 데이터를 넣는 경우에는 해당 데이터의 주소값을 통해 수정할 수 있지만 기본 구분기를 사용하는 경우 불가능하다.
 *
 * @author wjrmffldrhrl
 */
public interface Splitter {

    /**
     * 입력된 문자를 문장 단위로 나누어 반환
     * @param text String
     * @return Sentence Array
     */
    Sentence[] split(String text);

}
