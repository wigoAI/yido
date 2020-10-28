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

import java.util.List;

/**
 * 파일 관리자
 *
 * 파일 관리 기능을 상속받는다.
 * @author 조승현
 */
public interface FileManager extends FileWriter, FileReader{

    /**
     * 파일 메모리 업로드
     * @return {@code List<String>}
     */
    List<String> getFile();
}
