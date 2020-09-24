package org.moara.yido.role;

import org.moara.yido.fileIO.FileReader;

import java.util.HashSet;


/**
 * 룰 관리 클래스
 *
 */
public class RoleManagerTemp {
    private static RoleManagerTemp roleManagerTemp = new RoleManagerTemp();
    private HashSet<String> connective = new HashSet<>();
    private HashSet<String> terminator = new HashSet<>();

    private RoleManagerTemp() {
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
    public static RoleManagerTemp getRoleManager() { return roleManagerTemp; }

}
