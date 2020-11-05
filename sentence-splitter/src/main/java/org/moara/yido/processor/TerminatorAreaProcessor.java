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
import org.moara.yido.Config;
import org.moara.yido.role.RoleManager;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * 구분 영역 처리기
 *
 * TODO 1. 종결어미다 다르게 적용 될 유효성 체크
 *
 * @author 조승현
 */
public class TerminatorAreaProcessor {
    private final HashSet<String> terminatorRole;
    private final HashSet<String> connectiveRole;
    private final Config TERMINATOR_CONFIG;

    /**
     * Constructor
     * @param roleManager RoleManager
     * @param config Config
     */
    public TerminatorAreaProcessor(RoleManager roleManager, Config config) {
        this.terminatorRole = roleManager.getRole("terminator");
        this.connectiveRole = roleManager.getRole("connective");
        this.TERMINATOR_CONFIG = config;
    }

    /**
     * 구분점 반환
     *
     * @param text 구분 대상 문자
     * @return TreeSet
     */
    public TreeSet<Integer> find(String text) {
        TreeSet<Integer> terminatorList = new TreeSet<>();
        text = text.trim();

        for(int i = 0; i < text.length() - TERMINATOR_CONFIG.MIN_SENTENCE_LENGTH; i++) {
            for(int processingLength = TERMINATOR_CONFIG.PROCESSING_LENGTH_MAX; processingLength >= TERMINATOR_CONFIG.PROCESSING_LENGTH_MIN; processingLength--) {

                Area targetArea = new Area(i, i + processingLength);
                String targetString = text.substring(targetArea.getStart(), targetArea.getEnd());

                if(terminatorRole.contains(targetString) && !isConnective(targetArea.getEnd(), text)) {
                    int additionalSignLength = getAdditionalSignLength(targetArea.getEnd(), text);
                    terminatorList.add(targetArea.getEnd() + additionalSignLength);

                    i = targetArea.getStart() + additionalSignLength;

                    break;
                }

                i = targetArea.getStart();
            }
        }

        return terminatorList;
    }

    private boolean isConnective(int startIndex, String text) {

        int connectiveCheckLength = (startIndex + TERMINATOR_CONFIG.MIN_SENTENCE_LENGTH > text.length()) ? (startIndex + 5 - text.length()) : 5;
        String nextStr = text.substring(startIndex, startIndex +  connectiveCheckLength);

        for(int i = 0 ; i < nextStr.length() ; i++) {
            String targetString = nextStr.substring(0, nextStr.length() - i);

            if(connectiveRole.contains(targetString)) {
                return true;
            }
        }

        return false;
    }

    private int getAdditionalSignLength(int startIndex, String text) {
        int additionalSignLength = 0;
        String regular = "[ㄱ-ㅎㅏ-ㅣ\\.\\?\\!\\~\\;\\^]";
        Pattern pattern = Pattern.compile(regular);

        for(int i = 0 ; i + startIndex < text.length() ; i++ ) {
            String targetStr = text.substring(startIndex + i, startIndex + i + 1);

            if(!pattern.matcher(targetStr).matches()) {
                break;
            } else {
                additionalSignLength++;
            }
        }

        return additionalSignLength;
    }

}
