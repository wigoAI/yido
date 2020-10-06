package org.moara.yido.area.processor;

import org.moara.yido.area.Area;
import org.moara.yido.role.RoleManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class TerminatorAreaProcessor {
    private final HashSet<String> terminatorRole;
    private final HashSet<String> connectiveRole;

    public TerminatorAreaProcessor(RoleManager roleManager) {
        this.terminatorRole = roleManager.getTerminator();
        this.connectiveRole = roleManager.getConnective();
    }


    public TreeSet<Integer> find(String text, ExceptionAreaProcessor exceptionAreaProcessor) {
        TreeSet<Integer> terminatorList = new TreeSet<>();
        for(int i = 0 ; i < text.length() - 5 ; i++) {
            for(int targetLength = 3 ; targetLength >= 2 ; targetLength--) {

                Area targetArea = exceptionAreaProcessor.avoid(new Area(i, i + targetLength));
                String targetString = text.substring(targetArea.getStart(), targetArea.getEnd());


                if(this.terminatorRole.contains(targetString)) {

                    if(!isConnective(targetArea.getEnd(), text)) {

                        int additionalSignLength = getAdditionalSignLength(targetArea.getEnd(), text);
                        terminatorList.add(targetArea.getEnd() + additionalSignLength);


                        i = targetArea.getStart() + additionalSignLength;

                        break;
                    }
                }


                i = targetArea.getStart();
            }
        }
        return terminatorList;
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

}
