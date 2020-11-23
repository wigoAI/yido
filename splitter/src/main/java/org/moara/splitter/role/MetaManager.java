package org.moara.splitter.role;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.moara.splitter.utils.file.FileManager;

import java.util.regex.Pattern;

public class MetaManager {
    protected static final String rolePath = "/string_group/meta/";
    private static final String differentSideBracketRoleName = "M_different_side_bracket.json";
    private static final String sameSideBracketRoleName = "M_same_side_bracket.json";

    public static Pattern getDifferentSideBracketPattern() {
        JsonObject metaJson = FileManager.getJsonObjectByFile(rolePath + differentSideBracketRoleName);
        JsonArray dataArray = metaJson.get("data").getAsJsonArray();

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder centerLeft = new StringBuilder("[^]*");
        StringBuilder centerRight = new StringBuilder("[^]*");
        StringBuilder right = new StringBuilder("[]+");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject data  = dataArray.get(i).getAsJsonObject();
            String front = data.get("front").getAsString();
            String back = data.get("back").getAsString();


            left.insert(1, "\\" +front);
            centerRight.insert(2, "\\" + front);

            right.insert(1, "\\" + back);
            centerLeft.insert(2, "\\" + back);

        }

        String pattern = left.append(centerLeft).append(centerRight).append(right).toString();
        return  Pattern.compile(pattern);
    }

    public static Pattern getSameSideBracketPattern() {
        JsonObject metaJson = FileManager.getJsonObjectByFile(rolePath + sameSideBracketRoleName);
        JsonArray dataArray = metaJson.get("data").getAsJsonArray();

        StringBuilder left = new StringBuilder("[]+");
        StringBuilder center = new StringBuilder("[^]+");
        StringBuilder right = new StringBuilder("[]");

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject data  = dataArray.get(i).getAsJsonObject();
            String bracket = data.get("bracket").getAsString();

            left.insert(1, "\\" +bracket);
            center.insert(2, "\\" + bracket);
            right.insert(1, "\\" + bracket);

        }

        String pattern = left.append(center).append(right).toString();
        return  Pattern.compile(pattern);
    }

}
