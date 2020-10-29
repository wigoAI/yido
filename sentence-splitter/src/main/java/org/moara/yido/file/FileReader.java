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
package org.moara.yido.file;

/**
 * 파일 리더
 * @author 조승현
 */
public interface FileReader {

    /**
     * 경로를 포함한 파일 명으로 파일을 읽어온다.
     * TODO 1. 파일 작성 후 바로 읽지 못하는 문제 해결하기
     * @param fileName String
     */
    void readFile(String fileName);
}
