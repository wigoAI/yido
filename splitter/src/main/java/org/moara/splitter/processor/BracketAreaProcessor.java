package org.moara.splitter.processor;

import org.moara.splitter.manager.ExceptionRuleManager;
import org.moara.splitter.utils.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 예외영역 처리기 구현체
 * 괄호 영역에 대한 처리를 구현함
 *
 * @author wjrmffldrhrl
 */
public class BracketAreaProcessor implements ExceptionAreaProcessor {

    @Override
    public List<Area> find(String text) {
        List<Area> exceptionAreaList = new ArrayList<>();

        Matcher matcher =  ExceptionRuleManager.getDifferentSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        matcher = ExceptionRuleManager.getSameSideBracketPattern().matcher(text);
        while (matcher.find()) {
            exceptionAreaList.add(new Area(matcher));
        }

        return exceptionAreaList;
    }
}
