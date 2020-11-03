package org.moara.yido.role;


import org.junit.Test;

public class RoleManagerTest {

    @Test
    public void getBasicRoleManagerInstanceTest() {
        RoleManager roleManager = BasicRoleManager.getRoleManager();

        for(String str : roleManager.getConnective()) {
            System.out.println(str);
        }

        for (String str : roleManager.getException()) {
            System.out.println(str);
        }

        for (String str : roleManager.getConnective()) {
            System.out.println(str);
        }
    }

    @Test
    public void getNewsRoleManagerInstanceTest() {
        RoleManager roleManager = NewsRoleManager.getRoleManager();

        for(String str : roleManager.getConnective()) {
            System.out.println(str);
        }

        for (String str : roleManager.getException()) {
            System.out.println(str);
        }

        for (String str : roleManager.getConnective()) {
            System.out.println(str);
        }
    }






}