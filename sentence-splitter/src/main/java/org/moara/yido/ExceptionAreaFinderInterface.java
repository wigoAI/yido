package org.moara.yido;

import java.util.List;

public interface ExceptionAreaFinderInterface {
    String URL_PATTERN = "^((https?:\\/\\/)|(www\\.))([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
    String BRACKET_PATTERN = "[\\(*\\{*\\[*][^\\)\\]\\}]*[^\\(\\[\\{]*[\\)\\]\\}]";

    List<Area> find(String data);
}
