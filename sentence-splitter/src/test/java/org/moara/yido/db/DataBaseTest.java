package org.moara.yido.db;

import org.junit.Test;

public class DataBaseTest {

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
}
