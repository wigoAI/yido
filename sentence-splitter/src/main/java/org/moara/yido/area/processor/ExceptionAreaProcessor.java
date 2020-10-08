package org.moara.yido.area.processor;

import org.moara.yido.Config;
import org.moara.yido.area.Area;
import org.moara.yido.area.processor.regularExpression.BracketProcessor;
import org.moara.yido.area.processor.regularExpression.UrlProcessor;
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExceptionAreaProcessor {


    private final UrlProcessor urlProcessor = new UrlProcessor();
    private final List<Area> exceptionAreaList = new ArrayList<>();
    private final BracketProcessor bracketProcessor;
    private HashSet<String> exceptionRole;

    public ExceptionAreaProcessor(RoleManager roleManager) {
        this.exceptionRole = roleManager.getException();
        bracketProcessor = new BracketProcessor(roleManager);
    }

    /**
     * TODO 1. have to check overflow
     *
     */
    public Area avoid(Area targetArea) {

        for(int i = 0 ; i < exceptionAreaList.size() ; i++) {

            Area exceptionArea = exceptionAreaList.get(i);

            if(targetArea.isOverlap(exceptionArea)) {
                targetArea.moveStart(exceptionArea.getEnd());

                // 이동시킨 위치가 예외 영역에 포함되지 않는지 다시 체크
                i = -1;
            }
        }

        return targetArea;

    }

    public List<Area> find(String data) {
        exceptionAreaList.clear();
        this.exceptionAreaList.addAll(this.urlProcessor.find(data));
        this.exceptionAreaList.addAll(this.bracketProcessor.find(data));

        return this.exceptionAreaList;
    }


}
