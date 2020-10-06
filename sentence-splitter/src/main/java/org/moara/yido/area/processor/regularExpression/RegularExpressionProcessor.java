package org.moara.yido.area.processor.regularExpression;

import org.moara.yido.area.Area;

import java.util.List;

public interface RegularExpressionProcessor {
    String URL_PATTERN = "((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?";
    String BRACKET_PATTERN = "[\\(\\{\\[\\“][^\\)\\]\\}\\”]*[^\\(\\[\\{\\“]*[\\)\\]\\}\\”]";

    List<Area> find(String data);

}
