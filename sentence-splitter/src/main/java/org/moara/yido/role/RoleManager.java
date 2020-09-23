package org.moara.yido.role;

import org.moara.yido.db.DataBase;
import org.moara.yido.fileIO.FileReader;
import org.moara.yido.fileIO.FileWriter;

import java.util.HashSet;
import java.util.List;


/**
 * 룰 관리 클래스
 *
 */
public class RoleManager {
    private static RoleManager roleManager = new RoleManager();
    private HashSet<String> connective = new HashSet<>();
    private HashSet<String> terminator = new HashSet<>();

    private RoleManager() {
        FileReader connectiveFileReader = new FileReader("/data/connective.txt");
        FileReader terminatorFileReader = new FileReader("/data/terminator.txt");
//        FileReader terminatorFileReadr = new FileReader("/data/talkTerminator.txt");
//        FileReader terminatorFileReadr = new FileReader("/data/newTerminator.txt");
//        FileReader terminatorFileReadr = new FileReader("/data/koTerminator.txt");

        this.connective.addAll(connectiveFileReader.getSplitFileByLine());
        this.terminator.addAll(terminatorFileReader.getSplitFileByLine());
    }


    public HashSet<String> getConnective() { return this.connective; }
    public HashSet<String> getTerminator() { return this.terminator; }
    public static RoleManager getRoleManager() { return roleManager; }

}
