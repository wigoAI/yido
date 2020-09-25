package org.moara.yido;

public class Sentence {
    private int start;
    private int end;
    private String text;

    public Sentence(int start, int end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public int getStart() { return this.start; }
    public int getEnd() { return  this.end; }
    public String getText() { return this.text; }

}
