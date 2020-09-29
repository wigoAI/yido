package org.moara.yido.area.processor;

import org.moara.yido.area.Area;
import org.moara.yido.role.ConnectiveRole;
import org.moara.yido.role.TerminatorRole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class TerminatorAreaProcessor implements AreaProcessor {
    private HashSet<String> terminatorRole;
    private HashSet<String> connectiveRole;
    private List<Area> terminatorAreaList = new ArrayList<>();

    public TerminatorAreaProcessor() {
        this.terminatorRole = TerminatorRole.getInstance().getRole();
        this.connectiveRole = ConnectiveRole.getInstance().getRole();
    }

    @Override
    public Area avoid(Area area) {
        return null;
    }

    @Override
    public List<Area> find(String text) {
        this.terminatorAreaList.clear();

        for(int i = 0 ; i < text.length() ; i++) {
            for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {
                Area targetArea = new Area(i, i + targetLength);
                String targetString = text.substring(targetArea.getStart(), targetArea.getEnd());

                if(this.terminatorRole.contains(targetString) && !isConnective(targetArea.getStart(), text)) {
                    int additionalSignLength = getAdditionalSignLength(targetArea.getEnd(), text);
                    int targetSplitPoint = targetArea.getEnd() + additionalSignLength;

                    this.terminatorAreaList.add(new Area(targetArea.getStart(), targetSplitPoint));

                    i = targetSplitPoint - 1;
                    break;
                }
            }
        }
        return this.terminatorAreaList;
    }

    private boolean isConnective(int startIndex, String text) {

        int connectiveCheckLength = (startIndex + 5 > text.length()) ? (startIndex + 5 - text.length()) : 5;
        String nextStr = text.substring(startIndex, startIndex +  connectiveCheckLength);

        for(int i = 0 ; i < nextStr.length() ; i++) {
            String targetString = nextStr.substring(0, nextStr.length() - i);

            if(connectiveRole.contains(targetString)) {
                return true;
            }
        }

        return false;
    }

    private int getAdditionalSignLength(int startIndex, String text) {
        int additionalSignLength = 0;
        String regular = "[ㄱ-ㅎㅏ-ㅣ\\.\\?\\!\\~\\;\\^]";
        Pattern pattern = Pattern.compile(regular);

        for(int i = 0 ; i + startIndex < text.length() ; i++ ) {
            String targetStr = text.substring(startIndex + i, startIndex + i + 1);

            if(!pattern.matcher(targetStr).matches()) {
                break;
            } else {
                additionalSignLength++;
            }
        }

        return additionalSignLength;
    }

    public List<Area> getTerminatorAreaList() { return terminatorAreaList; }
}
