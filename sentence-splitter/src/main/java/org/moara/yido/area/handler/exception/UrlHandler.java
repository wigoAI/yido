package org.moara.yido.area.handler.exception;

import org.moara.yido.area.Area;

import java.util.List;

public class UrlHandler implements ExceptionAreaHandler{
    @Override
    public List<Area> find(String data) {
        return null;
    }

    @Override
    public Area avoid(Area area) {
        return null;
    }
}