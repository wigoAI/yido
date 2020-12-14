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

package org.moara.splitter.exception;

/**
 * 존재하지 않는 구분기 로드시 발생하는 예외
 *
 * @author wjrmffldrhrl
 */
public class SplitterNotFoundException extends RuntimeException {

    /**
     * 예외 생성자
     * 매개변수로 받은 구분가 id로 로그 처리
     * @param splitterId 존재하지 않는 구분기 id
     */
    public SplitterNotFoundException(String splitterId) {
        super("splitter not found : " + splitterId);


    }
}
