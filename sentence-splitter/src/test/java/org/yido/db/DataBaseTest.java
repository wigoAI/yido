package org.yido.db;

import org.junit.Assert;
import org.junit.Test;

public class DataBaseTest {



    /*
    * Connection test with oracle
    * */
    @Test
    public void dbConnectionTest() throws Exception {
        DataBase db = new DataBase();


    }


    /*
     * Connection test with mariaDB
     * */
    @Test
    public void mariadbConnectionTest() throws Exception {
        DataBase db = new DataBase("mariadb");
    }


    /*
    * Get Roles in WIGO Database
    * */
    @Test
    public void getRolesTest() throws Exception {
        DataBase db = new DataBase();
        int cnt = 0;
        for(String str : db.getRoles()){
            System.out.println(str);
            cnt++;
        }

        System.out.println(cnt);


        Assert.assertEquals(1643, cnt);


    }
}
