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
package org.moara.splitter.utils.file;

import org.moara.splitter.SplitterImpl;

import java.io.File;
import java.util.Collection;

/**
 * 파일 관리자 추상체
 *
 * @author wjrmffldrhrl
 */
public interface FileManager {
    String pathSeparator = File.separator;
    String ABSTRACT_PATH = SplitterImpl.class.getResource("")
            .getPath().split( "build")[0]
            + "data" + pathSeparator;

    /**
     * 경로를 포함한 파일 명으로 파일을 읽어온다.
     *
     * @param fileName String
     *
     * @return Result read file Success
     */
    Collection<String> readFile(String fileName);

    /**
     * 파일 생성
     * @param fileName String
     * @param data {@code List<String>}
     *
     * @return Write file result
     */
    boolean writeFile(String fileName, Collection<String> data);

    /**
     *
     * @param fileName
     * @param data
     *
     * @return Result add line success
     */
    boolean addLine(String fileName, Collection<String> data);

}
