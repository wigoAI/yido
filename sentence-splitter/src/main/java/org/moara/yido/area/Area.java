package org.moara.yido.area;

public class Area {

    private int start;
    private int end;
    private int length;


    public Area(int startIndex, int endIndex) {
        this.start = startIndex;
        this.end = endIndex;
        this.length = endIndex - startIndex;
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
        this.start = newStartIndex;
        this.end = this.start + this.length;
    }
    public void moveEnd(int newEndIndex) {
        this.end = newEndIndex;
        this.start = this.end - this.length;
    }

    public int getStart() { return this.start; }
    public int getEnd() { return this.end; }



}
