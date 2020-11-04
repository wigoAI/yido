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
package org.moara.yido.processor;

import com.github.wjrmffldrhrl.Area;
import org.moara.yido.processor.regularExpression.BracketProcessor;
import org.moara.yido.processor.regularExpression.UrlProcessor;
import org.moara.yido.role.RoleManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 예외영역 처리기
 *
 * @author 조승현
 */
public class ExceptionAreaProcessor {
    private final UrlProcessor urlProcessor = new UrlProcessor();
    private final BracketProcessor bracketProcessor;

    /**
     * Constructor
     *
     * @param roleManager RoleManager
     *
     */
    public ExceptionAreaProcessor(RoleManager roleManager) {
        bracketProcessor = new BracketProcessor(roleManager);
    }

    /**
     * Role 에 해당하는 예외 영역을 찾아 List 로 반환
     * @param data String
     * @return 예외 영역들이 Area 형태로 담긴 리스트 반환
     */
    public List<Area> find(String data) {
        List<Area> exceptionAreaList = new ArrayList<>();
        exceptionAreaList.addAll(urlProcessor.find(data));
        exceptionAreaList.addAll(bracketProcessor.find(data));

        return exceptionAreaList;
    }

}
