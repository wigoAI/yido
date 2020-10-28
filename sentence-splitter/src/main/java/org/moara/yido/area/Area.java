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
package org.moara.yido.area;

/**
 * 문자열 인덱싱 클래스
 *
 * TODO 1. 외부 라이브러리로 추출할 것
 *          - Central 에 배포
 * @author 조승현
 */
public class Area {

    private int start;
    private int end;


    /**
     * Constructor
     * @param startIndex int
     * @param endIndex int
     */
    public Area(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
    }

    // compareArea -> exceptionArea
    public boolean isOverlap(Area compareArea) {
        if(compareArea.getStart() > this.start && compareArea.getStart() < this.end) {
            return true;
        } else if(compareArea.getEnd() > this.start && compareArea.getEnd() < this.end) {
            return true;
        } else if(compareArea.getStart() < this.start && compareArea.getEnd() > this.end) {
            return true;
        }

        return false;
    }

    public void moveStart(int newStartIndex) {
        int length = this.end - this.start;
        this.start = newStartIndex;
        this.end = this.start + length;
    }
    public void moveEnd(int newEndIndex) {
        int length = this.end - this.start;

        this.end = newEndIndex;
        this.start = this.end - length;
    }

    public int getStart() { return this.start; }
    public int getEnd() { return this.end; }


}
