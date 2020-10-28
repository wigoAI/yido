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
package org.moara.yido.area.processor;

import org.moara.yido.area.Area;
import org.moara.yido.area.processor.regularExpression.BracketProcessor;
import org.moara.yido.area.processor.regularExpression.UrlProcessor;
import org.moara.yido.role.RoleManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 예외영역 처리기
 *
 * @author 조승현
 */
public class ExceptionAreaProcessor {


    private final List<Area> exceptionAreaList = new ArrayList<>();
    private final UrlProcessor urlProcessor = new UrlProcessor();
    private final BracketProcessor bracketProcessor;
//    private HashSet<String> exceptionRole;

    /**
     * Constructor
     *
     * TODO 1. 현재 괄호 영역에 대한 처리만 이루어지고 있음
     *          - 괄호 영역에 사용되는 dictionary 가 예외영역 dictionary 와 같음
     *          - 예외 영역 처리기의 exceptionRole 을 제거 할 수 있으면 하기
     * @param roleManager RoleManager
     */
    public ExceptionAreaProcessor(RoleManager roleManager) {
//        this.exceptionRole = roleManager.getException();
        this.bracketProcessor = new BracketProcessor(roleManager);
    }

    /**
     * Role 에 해당하는 예외 영역을 찾아 List 로 반환
     * @param data String
     * @return {@code List<Area>}
     */
    public List<Area> find(String data) {
        exceptionAreaList.clear();
        this.exceptionAreaList.addAll(this.urlProcessor.find(data));
        this.exceptionAreaList.addAll(this.bracketProcessor.find(data));

        return this.exceptionAreaList;
    }

    /**
     * 예외영역 회피 메서드
     * TODO 1. have to check overflow
     * @param targetArea Area
     */
    public Area avoid(Area targetArea) {

        for(int i = 0 ; i < exceptionAreaList.size() ; i++) {

            Area exceptionArea = exceptionAreaList.get(i);

            if(targetArea.isOverlap(exceptionArea)) {
                targetArea.moveStart(exceptionArea.getEnd());

                // 이동시킨 위치가 예외 영역에 포함되지 않는지 다시 체크
                i = -1;
            }
        }

        return targetArea;

    }


}
