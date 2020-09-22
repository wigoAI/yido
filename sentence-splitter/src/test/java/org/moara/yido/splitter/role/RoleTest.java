package org.moara.yido.splitter.role;

import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.junit.Test;
import org.moara.yido.fileIO.FileReader;
import org.moara.yido.fileIO.FileWriter;
import org.moara.yido.role.RoleManager;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleTest {


    @Test
    public void roleManagerTest() {
        System.out.println("test Start");
        RoleManager roleManager = RoleManager.getRoleManager();
        roleManager.initRoleData();
//
        for(String str : roleManager.getConnective())
            System.out.println(str);
    }

    @Test
    public void editTerminatorTest() {
        String allSpecialCharacter = "[\\!-\\/\\:-\\@\\[-\\`\\{-\\~]";
        FileReader fileReader = new FileReader("/data/terminator.txt");
        FileWriter fileWriter = new FileWriter("/data/newTerminator.txt");
        List<String> terminatorList = new ArrayList<>();
        Set<String> terminatorSet = new HashSet<>();

        terminatorList = fileReader.getSplitFileByLine();
        for(String str : terminatorList) {
            str = str.replaceAll(allSpecialCharacter, "");
            if(str.length() < 6 && str.length() > 1)
                terminatorSet.add(str);
        }

        fileWriter.writeFileBySet(terminatorSet, false);


    }

    @Test
    public void createNewTerminatorTest() {
        String allSpecialCharacter = "[\\!-\\/\\:-\\@\\[-\\`\\{-\\~]";
        FileReader fileReader = new FileReader("/data/terminator.txt");
        FileWriter fileWriter = new FileWriter("/data/newTerminator.txt");
        List<String> terminatorList = new ArrayList<>();
        Set<String> terminatorSet = new HashSet<>();

        terminatorList = fileReader.getSplitFileByLine();
        for(String str : terminatorList) {
            str = str.replaceAll(allSpecialCharacter, "");
            if(str.length() < 6 && str.length() > 1)
                terminatorSet.add(str);
        }

        fileWriter.writeFileBySet(terminatorSet, false);
    }

    @Test
    public void getTerminatorFromKkoDic() {
        String ef = "\\tEF";
        String deleteHan = "[ㄱ-ㅎㅏ-ㅣ]";
        Pattern p = Pattern.compile(ef);

        FileReader fileReader = new FileReader("/data/lnpr_pos_g_morp_intra.dic");
        FileWriter fileWriter = new FileWriter("/data/koTerminator.txt");

        List<String> kkoDicList = new ArrayList<>();
        Set<String> terminatorSet = new HashSet<>();

        kkoDicList = fileReader.getSplitFileByLine();
        for(String str : kkoDicList) {
            Matcher matcher = p.matcher(str);
            if(matcher.find()) {
                String targetEF = str.split("\t")[1];
                targetEF = targetEF.replaceAll(deleteHan, "");

                if(targetEF.length() > 1)
                    terminatorSet.add(targetEF);
            }


//            System.out.println(str);
        }

        int cnt = 0;
        for(String str : terminatorSet) {
            cnt++;
            System.out.println(str);
        }

        fileWriter.writeFileBySet(terminatorSet, false);
        System.out.println(cnt);
    }


    @Test
    public void getTerminatorFromJson() {
        String efPattern = "[가-힣]+\\/[A-Z]*\\+*EF";
        Pattern p = Pattern.compile(efPattern);

        FileReader fileReader = new FileReader("/data/dialog.json");
        FileWriter fileWriter = new FileWriter("/data/talkTerminator.txt");

        Set<String> terminatorSet = new HashSet<>();

        List<String> json = fileReader.getSplitFileByLine();

        for(String str : json) {
            if(str.contains("a_morpheme")) {
                Matcher matcher = p.matcher(str);
                if(matcher.find()) {
                    String efStr = matcher.group().split("\\/")[0];
//                    System.out.println(efStr);
                    if(efStr.length() > 1 && efStr.length() < 6)
                        terminatorSet.add(efStr);
                }

            }

        }
        fileWriter.writeFileBySet(terminatorSet, false);
        int cnt = 0;
        for(String str : terminatorSet) {
            cnt++;
            System.out.println(str);
        }

        System.out.println(cnt);
    }

    @Test
    public void getTerminatorFromJsonWithConnective() {
        String efPattern = "[가-힣]*[\\/][A-Z\\+]*[가-힣]+\\/[A-Z]*\\+*EF";
        String koreanPattern = "[가-힣]";
        Pattern p = Pattern.compile(efPattern);
        Pattern koreanP = Pattern.compile(koreanPattern);

        FileReader fileReader = new FileReader("/data/dialog.json");
        FileWriter fileWriter = new FileWriter("/data/longTalkTerminator.txt");

        Set<String> terminatorSet = new HashSet<>();

        List<String> json = fileReader.getSplitFileByLine();

        for(String str : json) {
            if(str.contains("a_morpheme")) {
                Matcher matcher = p.matcher(str);
                if(matcher.find()) {
                    String efStr = matcher.group();
                    Matcher koreanMatcher= koreanP.matcher(efStr);
                    String set = "";
                    while(koreanMatcher.find()) {
                        set += koreanMatcher.group();

                    }
                    terminatorSet.add(set);
                }
            }

        }
        fileWriter.writeFileBySet(terminatorSet, false);
        int cnt = 0;
        for(String str : terminatorSet) {
            cnt++;
            System.out.println(str);
        }

        System.out.println(cnt);
    }
}
