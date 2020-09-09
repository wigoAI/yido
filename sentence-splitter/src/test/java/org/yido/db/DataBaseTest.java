package org.yido.db;

import org.junit.Assert;
import org.junit.Test;

public class DataBaseTest {

    @Test
    public void dbConntectionTest() throws Exception {
        DataBase db = new DataBase();


    }


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
