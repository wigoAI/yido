package org.moara.yido.area.handler.exception;

import org.moara.yido.area.Area;

import java.util.List;

public interface ExceptionAreaHandler {

    List<Area> find(String data);
    Area avoid(Area area);
}
