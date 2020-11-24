package org.moara.splitter.processor;

import com.github.wjrmffldrhrl.Area;
import org.moara.splitter.role.MetaManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class BracketAreaProcessor implements ExceptionAreaProcessor {

    @Override
    public List<Area> find(String text) {
        List<Area> exceptionAreaList = new ArrayList<>();

        Matcher matcher =  MetaManager.getDifferentSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        matcher = MetaManager.getSameSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        return exceptionAreaList;
    }
}
