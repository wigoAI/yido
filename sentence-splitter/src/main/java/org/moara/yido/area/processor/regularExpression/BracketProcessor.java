package org.moara.yido.area.processor.regularExpression;

import org.moara.yido.area.Area;
import org.moara.yido.role.BasicRoleManager;
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BracketProcessor implements RegularExpressionProcessor {
//    String BRACKET_PATTERN = "[\\(\\{\\[\\“]+[^\\)\\]\\}\\”]*[^\\(\\[\\{\\“]*[\\)\\]\\}\\”]+";

    Pattern bracketPattern;

    public BracketProcessor(RoleManager roleManager) {
        HashSet<String> patternDic = roleManager.getException();
        System.out.println("BracketProcessor roleManager : "  );
        this.bracketPattern = createPattern(patternDic);
    }

    @Override
    public List<Area> find(String data) {

        Matcher bracketMatcher = bracketPattern.matcher(data);
        List<Area> bracketArea = new ArrayList<>();

        while(bracketMatcher.find()) {
            bracketArea.add(new Area(bracketMatcher.start(), bracketMatcher.end()));
        }

        return bracketArea;
    }

    private Pattern createPattern(HashSet<String> patternData) {

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder centerLeft = new StringBuilder("[^]*");
        StringBuilder centerRight = new StringBuilder("[^]*");
        StringBuilder right = new StringBuilder("[]+");

       for(String data : patternData) {

            if(data == null)
                continue;

            System.out.println(data);
           left.insert(1, "\\" + data.charAt(0));
           centerRight.insert(2, "\\" + data.charAt(0));

           right.insert(1, "\\" + data.charAt(1));
           centerLeft.insert(2, "\\" + data.charAt(1));

       }

       String pattern = left.append(centerLeft).append(centerRight).append(right).toString();

       System.out.println(pattern);

        return  Pattern.compile(pattern);
    }
}
