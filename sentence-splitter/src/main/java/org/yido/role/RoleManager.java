package org.yido.role;

import org.yido.db.DataBase;
import org.yido.fileIO.FileReader;
import org.yido.fileIO.FileWriter;

import java.util.List;


/**
 * 룰 관리 클래스
 *
 * TODO 1. history 파일을 생성하여 관리할 것
 *      2. role data 생성 후 바로 안읽혀지는 문제 해결
 *          - 실행이 끝난 뒤 파일이 생성된다.
 *
 *
 *
 */
public class RoleManager {
    private static RoleManager roleManager = new RoleManager();
    List<String> connective;
    List<String> terminator;

    private RoleManager() { RoleManager.roleUpdate(); }



    public static void roleUpdate() {

        DataBase db1 = new DataBase();
        DataBase db2 = new DataBase();
        FileWriter connectiveData = new FileWriter("/data/connective.txt");
        FileWriter terminatorData = new FileWriter("/data/terminator.txt");

        String sentenceStringDataTable = "TB_ARA_SEN_GROUP_STRING";
        String sentenceStringDataColumn = "VAL_STRING";
        String connectiveDataGroup = "CD_GROUP='S2'";
        String terminatorDataGroup = "CD_GROUP='S4'";

        connectiveData.writeFileByList(db1.doQueryAndGetList(
                sentenceStringDataColumn, sentenceStringDataTable, connectiveDataGroup), false);

        terminatorData.writeFileByList(db2.doQueryAndGetList(
                sentenceStringDataColumn, sentenceStringDataTable, terminatorDataGroup), false);



    }

    public void initRoleData() {

        FileReader connectiveFileReader = new FileReader("/data/connective.txt");
        FileReader terminatorFileReader = new FileReader("/data/terminator.txt");

        this.connective = connectiveFileReader.getSplitFileByLine();
        this.terminator = terminatorFileReader.getSplitFileByLine();

    }

    public List<String> getConnective() { return this.connective; }
    public List<String> getTerminator() { return this.terminator; }
    public static RoleManager getRoleManager() { return roleManager; }

}
