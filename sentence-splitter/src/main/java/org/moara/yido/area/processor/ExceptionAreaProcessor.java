package org.moara.yido.area.processor;

import org.moara.yido.area.Area;
import org.moara.yido.area.processor.regularExpression.BracketProcessor;
import org.moara.yido.area.processor.regularExpression.UrlProcessor;
import org.moara.yido.role.ExceptionRole;
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.List;

public class ExceptionAreaProcessor implements AreaProcessor {

    private RoleManager exceptionRole;
    private UrlProcessor urlProcessor = new UrlProcessor();
    private BracketProcessor bracketProcessor = new BracketProcessor();
    private List<Area> exceptionAreaList = new ArrayList<>();

    public ExceptionAreaProcessor() {
        this.exceptionRole = ExceptionRole.getInstance();
    }

    /**
     * TODO 1. have to check overflow
     *
     */
    @Override
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

    @Override
    public List<Area> find(String data) {


        this.exceptionAreaList.addAll(this.urlProcessor.find(data));
        this.exceptionAreaList.addAll(this.bracketProcessor.find(data));

        return this.exceptionAreaList;
    }

    public List<Area> getExceptionAreaList() { return this.exceptionAreaList; }


}
