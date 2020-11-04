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
package org.moara.yido.processor.regularExpression;


import com.github.wjrmffldrhrl.Area;

import java.util.List;

/**
 * 정규식 처리기
 *
 * TODO 1. 파일로 원하는 정규식 찾아내는 문장 구분기 생성
 *          - 정규식 처리기 생성 완료
 *              - 연결어는 어떻게 처리할 것 인가?
 *
 * * @author 조승현
 */
public interface RegularExpressionProcessor {

    /**
     * 각 정규식에 해당하는 위치를 찾아 Area로 반환한다.
     * @param data String
     * @return {@code List<Area>}
     */
    List<Area> find(String data);

}
