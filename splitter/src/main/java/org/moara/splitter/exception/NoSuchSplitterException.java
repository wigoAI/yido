package org.moara.splitter.exception;

public class NoSuchSplitterException extends RuntimeException {
    public NoSuchSplitterException(String splitterId) {
        super("No such has a splitter : " + splitterId);
    }
}
