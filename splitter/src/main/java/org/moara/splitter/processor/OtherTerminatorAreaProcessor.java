package org.moara.splitter.processor;

import org.moara.splitter.role.SplitCondition;
import org.moara.splitter.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * TODO 1. 위치에 따른 validation 처리
 *          - F
 *          - B
 *      2. 일치 여부에 따른 validation 처리
 *          - Y
 *          - N
 *      3. public validation 처리
 *          - Y
 *          - N
 *      4. 구분 위치에 따른 문장 구분점 처리
 *          - F
 *          - B
 */
public class OtherTerminatorAreaProcessor {

    private final List<SplitCondition> splitConditions;
    private final Config config;

    public OtherTerminatorAreaProcessor(List<SplitCondition> splitConditions, Config config) {
        this.splitConditions = splitConditions;
        this.config = config;
    }

    public TreeSet<Integer> find(String text) {
        TreeSet<Integer> splitPointSet = new TreeSet<>();
        text = text.trim();

        for (SplitCondition splitCondition : splitConditions) {

            int splitPoint = -1;
            while (true) {
                splitPoint = text.indexOf(splitCondition.getValue(), splitPoint);
                if (splitPoint == -1) {
                    break;
                }
                splitPoint += splitCondition.getValue().length();
                splitPointSet.add(splitPoint );
            }
        }
        removeInvalidItem(splitPointSet, text.length());
        System.out.println(splitPointSet.toString());
        return splitPointSet;
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
