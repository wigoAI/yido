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
package org.moara.yido.splitter.processor;

import org.moara.yido.splitter.utils.Area;

import java.util.List;

/**
 * 구분 처리기 추상체
 *
 * @author wjrmffldrhrl
 */
public interface TerminatorProcessor {
    /**
     * @param text           구분점을 찾을 데이터
     * @param exceptionAreas 예외 영역
     * @return 구분점 리스트
     */
    int[] find(String text, List<Area> exceptionAreas);
}
