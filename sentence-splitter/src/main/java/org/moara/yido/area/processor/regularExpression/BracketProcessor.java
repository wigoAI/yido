package org.moara.yido.area.processor.regularExpression;

import org.moara.yido.area.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BracketProcessor implements RegularExpressionProcessor {

    Pattern bracketPattern = Pattern.compile(BRACKET_PATTERN);


    @Override
    public List<Area> find(String data) {

        Matcher bracketMatcher = bracketPattern.matcher(data);
        List<Area> bracketArea = new ArrayList<>();

        while(bracketMatcher.find()) {
            bracketArea.add(new Area(bracketMatcher.start(), bracketMatcher.end()));
        }

        return bracketArea;
    }
}
