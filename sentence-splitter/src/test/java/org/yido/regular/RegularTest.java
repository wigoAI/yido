package org.yido.regular;

import org.junit.Test;
import org.yido.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {

    @Test
    public void findBracketTest() {
        Pattern pattern = Pattern.compile("[\\(*\\{*\\[*][^\\)\\]\\}]*[\\)\\]\\}]");
        Matcher matcher = pattern.matcher("안녕하세요(ㅋㅋㅋ)저는조승현(일수도)입니다.");

        while(matcher.find()){
            System.out.println(matcher.start());
            System.out.println(matcher.end());
        }


    }

    @Test
    public void deleteAreaMethodTest() {
        List<Area> exceptionArea = new ArrayList<>();

        exceptionArea.add(new Area(1,2));
        exceptionArea.add(new Area(4,5));
        exceptionArea.add(new Area(6,42));
        deleteAreaList(exceptionArea);

        for (Area area : exceptionArea) {
            System.out.println(area.getStartIndex() + " " + area.getEndIndex());
        }

    }

    private void deleteAreaList(List<Area> exceptionArea) {

        for(int i = 0 ; i < exceptionArea.size() ; i++) {
            if(i == 1) exceptionArea.remove(i);
        }
    }
}
