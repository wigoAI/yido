package org.moara.yido.role;


import org.junit.Test;

public class RoleManagerTest {

    /**
     * TODO 1. terminator role을 가져올 시 exception과 regx role 을 가져올 수 없다
     */
    @Test
    public void testGetBasicRoleManagerInstance() {
        RoleManager roleManager = BasicRoleManager.getRoleManager();

        System.out.println(roleManager.getRole("connective").size());
        for(String str : roleManager.getRole("connective")) {
            System.out.println(str);
        }
        System.out.println(roleManager.getRole("terminator").size());
        for (String str : roleManager.getRole("terminator")) {
            System.out.println(str);
        }
        System.out.println(roleManager.getRole("exception").size());
        for (String str : roleManager.getRole("exception")) {
            System.out.println(str);
        }
        System.out.println(roleManager.getRole("regx").size());
        for (String str : roleManager.getRole("regx")) {
            System.out.println(str);
        }
    }

    @Test
    public void testGetNewsRoleManagerInstance() {
        RoleManager roleManager = NewsRoleManager.getRoleManager();

        for(String str : roleManager.getRole("connective")) {
            System.out.println(str);
        }

        for (String str : roleManager.getRole("exception")) {
            System.out.println(str);
        }

        for (String str : roleManager.getRole("terminator")) {
            System.out.println(str);
        }
        for (String str : roleManager.getRole("regx")) {
            System.out.println(str);
        }
    }






}
