/*
 * Copyright (C) 2021 Wigo Inc.
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
package org.moara.ner.entity;

import com.seomse.commons.data.BeginEnd;

/**
 * 개체명 정보 추상체
 *
 * @author wjrmffldrhrl
 */
public interface NamedEntity extends BeginEnd {

    /**
     * 개체명 값 반환
     * @return 개체 정보
     */
    String getText();

    /**
     * 개체명 타입 반환
     *
     * @return 개체명 타입
     */
    String getType();

}
