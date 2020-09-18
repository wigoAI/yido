package org.yido.db;

import org.junit.Assert;
import org.junit.Test;
import org.yido.fileIO.FileWriter;

public class DataBaseTest {



    /**
    * Connection test with oracle
    * *
    @Test
    public void dbConnectionTest() throws Exception {
        DataBase db = new DataBase();


    }


    /**
     * Connection test with mariaDB
     * */
    @Test
    public void mariadbConnectionTest() throws Exception {
        DataBase db = new DataBase("mariadb");
    }


    /**
    * Get Roles in WIGO Database
    * */
    @Test
    public void getRolesTest() throws Exception {
        DataBase db = new DataBase();

        String sentenceStringDataTable = "TB_ARA_SEN_GROUP_STRING";
        String sentenceStringDataColumn = "VAL_STRING";
        String connectiveDataGroup = "CD_GROUP='S2'";
        String terminatorDataGroup = "CD_GROUP='S4'";
        for(String str : db.doQueryAndGetList(sentenceStringDataColumn, sentenceStringDataTable, terminatorDataGroup)){
            System.out.println(str);

        }



    }

    @Test
    public void getRoleToFile() throws Exception {
        DataBase db = new DataBase();
        FileWriter fileWriter = new FileWriter("/data/role.txt");
//        fileWriter.writeFileByList(db.getRoles(), false);
    }
}
