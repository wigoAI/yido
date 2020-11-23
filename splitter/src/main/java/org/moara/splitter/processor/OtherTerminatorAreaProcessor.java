package org.moara.splitter.processor;

import org.moara.splitter.role.SplitCondition;
import org.moara.splitter.role.Validation;
import org.moara.splitter.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * 구분점 처리기
 *
 *
 * @author wjrmffldrhrl
 */
public class OtherTerminatorAreaProcessor {

    private final List<SplitCondition> splitConditions;
    private final Config config;

    public OtherTerminatorAreaProcessor(List<SplitCondition> splitConditions, Config config) {
        this.splitConditions = splitConditions;
        this.config = config;

    }

    /**
     * 구분점 찾기
     * @param text 구분점을 찾을 데이터
     * @return 구분점 TreeSet
     */
    public TreeSet<Integer> find(String text) {
        TreeSet<Integer> splitPointSet = new TreeSet<>();
        text = text.trim();

        for (SplitCondition splitCondition : splitConditions) {

            int splitPoint = -1;
            while (true) {
                splitPoint = text.indexOf(splitCondition.getValue(), splitPoint);
                if (splitPoint < config.MIN_RESULT_LENGTH || splitPoint > text.length() - config.MIN_RESULT_LENGTH ||
                        splitPoint == -1) { break; } // 구분 조건 x

                if (!isValid(text, splitCondition, splitPoint)) {
                    splitPoint += splitCondition.getValue().length();
                    continue;
                }

                if (splitCondition.getSplitPosition() == 'B') { // 문장 구분점 뒤
                    splitPoint += splitCondition.getValue().length();
                    splitPoint += getAdditionalSignLength(splitPoint, text);
                    splitPointSet.add(splitPoint);
                } else { // 앞
                    splitPointSet.add(splitPoint);
                    splitPoint += splitCondition.getValue().length();
                }
            }
        }

        removeInvalidItem(splitPointSet, text.length());
        return splitPointSet;
    }

    private int getAdditionalSignLength(int startIndex, String text) {
        int additionalSignLength = 0;
        String regular = "[ㄱ-ㅎㅏ-ㅣ\\.\\?\\!\\~\\;\\^]";
        Pattern pattern = Pattern.compile(regular);

        for (int i = 0; i + startIndex < text.length(); i++) {
            String targetStr = text.substring(startIndex + i, startIndex + i + 1);

            if (!pattern.matcher(targetStr).matches()) {
                break;
            } else {
                additionalSignLength++;
            }
        }

        return additionalSignLength;
    }

        private boolean isValid(String text, SplitCondition splitCondition, int splitPoint) {
        boolean isValid = true;

        for (Validation validation : splitCondition.getValidations()) {
            int compareIndexStart = splitPoint;
            if (validation.getComparePosition() == 'B') {
                compareIndexStart += splitCondition.getValue().length();
            } else {
                compareIndexStart -= validation.getValue().length();
            }

            String compareText = text.substring(compareIndexStart, compareIndexStart + validation.getValue().length());
            boolean isEquals = compareText.equals(validation.getValue());
            if ((validation.getMatchFlag() == 'N' && isEquals) ||
                    (validation.getMatchFlag() == 'Y' && !isEquals)) {
                isValid = false;
                break;
            }

        }
        return isValid;
    }

    private void removeInvalidItem(TreeSet<Integer> splitPoints, int textLength) {
        splitPoints.removeIf(item -> item < config.MIN_RESULT_LENGTH ||
                item > textLength - config.MIN_RESULT_LENGTH);

        List<Integer> removeItems = new ArrayList<>();
        int previousItem = 0;
        for (int item : splitPoints) {
            if (item - previousItem < config.MIN_RESULT_LENGTH) {
                removeItems.add(previousItem);
            }

            previousItem = item;
        }
        splitPoints.removeAll(removeItems);
    }
}
