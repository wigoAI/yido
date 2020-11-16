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
package org.moara.yido.splitter;

import org.moara.yido.splitter.role.BasicRoleManager;
import org.moara.yido.splitter.role.NewsRoleManager;
import org.moara.yido.splitter.role.RoleManager;
import org.moara.yido.splitter.utils.Config;

import java.util.HashMap;

/**
 * 사용자가 원하는 문장 구분기를 제공해주는 클래스
 *
 * @author wjrmffldrhrl
 */
public class SplitterFactory {
    private static final int BASIC_SPLITTER_ID = 1;
    private static final int NEWS_SPLITTER_ID = 2;
    private static final int CUSTOM_SPLITTER_ID = 9;


    /**
     *  id  1. 기본 문장 구분기
     *      2. 뉴스 문장 구분기
     */
    private static final HashMap<Integer, Splitter> splitterHashMap = new HashMap<>();
    private static final SplitterFactory SPLITTER_FACTORY = new SplitterFactory();

    /**
     * 싱글톤으로 구성된 SplitterFactory 인스턴스 획득
     *
     * @return SplitterFactory 인스턴스 반환
     */
    public static SplitterFactory getInstance() { return SplitterFactory.SPLITTER_FACTORY; }
    private SplitterFactory() { }

    /**
     * 문장 구분기 인스턴스 획득
     * 설정값이 없으면 기본값을로 설정된 BasicSplitter를 반환한다.
     *
     * @return BasicSplitter
     */
    public Splitter getSplitter() {
        if(isKeyEmpty(BASIC_SPLITTER_ID)) { createSplitter(BASIC_SPLITTER_ID); }

        return splitterHashMap.get(BASIC_SPLITTER_ID);
    }



    /**
     * 임의 설정값이 적용된 문장 구분기 인스턴스 반환
     * 이후 CustomSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     * {@code getSplitter(9)}
     * @param config 설정값
     * @return Splitter
     */
    public Splitter getSplitter(Config config) {
        RoleManager basicRoleManager = BasicRoleManager.getRoleManager();
        splitterHashMap.put(CUSTOM_SPLITTER_ID,
                new SplitterImpl(basicRoleManager, config));
        return splitterHashMap.get(CUSTOM_SPLITTER_ID);
    }

    /**
     * 커스텀 룰 관리자가 적용된 문장 분리기 반환
     *
     * 이후 CustomSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     *  {@code getSplitter(9)}
     * @param roleManager CustomRoleManager
     * @return Splitter
     */
    public Splitter getSplitter(RoleManager roleManager) {
        splitterHashMap.put(CUSTOM_SPLITTER_ID,
                new SplitterImpl(roleManager, new Config()));
        return splitterHashMap.get(CUSTOM_SPLITTER_ID);
    }

    /**
     * 커스텀 룰 관리자와 사용자 지정 설정이 적용된 문장 분리기 반환
     *
     * 이후 CustomSplitter의 key value인 9로 인스턴스를 얻을 수 있다.
     * {@code getSplitter(9)}
     * @param roleManager CustomRoleManager
     * @param config CustomConfig
     * @return Splitter
     */
    public Splitter getSplitter(RoleManager roleManager, Config config) {
        splitterHashMap.put(CUSTOM_SPLITTER_ID,
                new SplitterImpl(roleManager, config));
        return splitterHashMap.get(CUSTOM_SPLITTER_ID);
    }


    /**
     * 특정 id로 분류한 문장 구분기 인스턴스 반환
     *
     * @param id Splitter ID
     * @return Splitter
     */
    public Splitter getSplitter(int id) {
        if(isKeyEmpty(id)) { createSplitter(id); }
        return splitterHashMap.get(id);
    }


    private void createSplitter(int id) {
        if(id == BASIC_SPLITTER_ID) {
            RoleManager basicRoleManager = BasicRoleManager.getRoleManager();
            splitterHashMap.put(BASIC_SPLITTER_ID,
                    new SplitterImpl(basicRoleManager, new Config()));
        } else if(id == NEWS_SPLITTER_ID) {
            RoleManager newsRoleManager = NewsRoleManager.getRoleManager();
            splitterHashMap.put(NEWS_SPLITTER_ID,
                    new SplitterImpl(newsRoleManager,
                            new Config(5, 3, 2, true)));
        }
    }

    private boolean isKeyEmpty(int key) { return !splitterHashMap.containsKey(key); }


}

