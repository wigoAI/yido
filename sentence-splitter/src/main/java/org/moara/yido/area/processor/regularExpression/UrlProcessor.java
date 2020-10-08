package org.moara.yido.area.processor.regularExpression;

import org.moara.yido.area.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlProcessor implements RegularExpressionProcessor {

    Pattern urlPattern = Pattern.compile(URL_PATTERN);


    @Override
    public List<Area> find(String data) {

        Matcher urlMatcher = urlPattern.matcher(data);
        List<Area> urlArea = new ArrayList<>();

        while(urlMatcher.find()) {
            System.out.println("find!");
            urlArea.add(new Area(urlMatcher.start(), urlMatcher.end()));
        }

        return urlArea;
    }
}
