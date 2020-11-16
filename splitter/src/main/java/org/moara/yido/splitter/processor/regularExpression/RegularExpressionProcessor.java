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
package org.moara.yido.splitter.processor.regularExpression;


import com.github.wjrmffldrhrl.Area;

import java.util.List;

/**
 * 정규식 처리기
 *
 *
 * @author wjrmffldrhrl
 */
public interface RegularExpressionProcessor {

    /**
     * 각 정규식에 해당하는 위치를 찾아 Area로 반환한다.
     * @param data String
     * @return {@code List<Area>}
     */
    List<Area> find(String data);

}
