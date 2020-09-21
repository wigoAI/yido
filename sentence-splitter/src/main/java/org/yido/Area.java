package org.yido;

public class Area {
    private int startIndex;
    private int endIndex;
    private int length;

    public Area(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = endIndex - startIndex;
    }

    public boolean isOverlap(Area compareArea) {
        if(compareArea.getStartIndex() > this.startIndex
                && compareArea.getStartIndex() < this.endIndex) {
            return true;
        } else if(compareArea.getEndIndex() > this.startIndex
                && compareArea.getEndIndex() < this.endIndex) {
            return true;
        }

        return false;

    }

    public int getStartIndex() { return this.startIndex; }
    public int getEndIndex() { return this.endIndex; }
    public int getLength() { return this.length; }

    public void moveStartIndex(int newStartIndex) {
        this.startIndex = newStartIndex;
        this.endIndex = this.startIndex + this.length;
    }
    public void moveEndIndex(int newEndIndex) {
        this.endIndex = newEndIndex;
        this.startIndex = this.endIndex - this.length;
    }

}
