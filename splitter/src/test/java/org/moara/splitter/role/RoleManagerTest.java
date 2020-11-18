package org.moara.splitter.role;


import org.junit.Assert;
import org.junit.Test;
import org.moara.splitter.file.FileManager;
import org.moara.splitter.file.FileManagerImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleManagerTest {

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

        for (String str : roleManager.getRole("regx")) {
            System.out.println(str);
        }
        for (String str : roleManager.getRole("terminator")) {
            System.out.println(str);
        }
    }

    @Test
    public void testAddRoleInMemory() {
        String roleName = "terminator";
        RoleManager roleManager = NewsRoleManager.getRoleManager();

        int originalSize = roleManager.getRole(roleName).size();

        List<String> roles = new ArrayList<>();
        roles.add("그랬다.");
        roleManager.addRolesToMemory(roleName, roles);
        Assert.assertEquals(originalSize + 1, roleManager.getRole(roleName).size());


        originalSize = roleManager.getRole(roleName).size();
        roles.add("그랬다.");
        roleManager.addRolesToMemory(roleName, roles);
        Assert.assertNotEquals(originalSize + 1, roleManager.getRole(roleName).size());

        roleManager.initRole(roleName);
    }

    @Test
    public void testAddRoleInLocal() {
        String roleName = "terminator";
        try {
            RoleManager roleManager = NewsRoleManager.getRoleManager();

            FileManager fileManager = new FileManagerImpl();
            int originalSize = roleManager.getRole(roleName).size();

            List<String> roles = new ArrayList<>();
            roles.add("그러하다.");

            roleManager.addRolesToLocal(roleName, roles);
            roleManager.initRole(roleName);

            Assert.assertEquals((originalSize + 1), roleManager.getRole(roleName).size());


            Collection<String> localRole = fileManager.readFile("/role/news/terminator.role");
            Assert.assertEquals(localRole.size() , originalSize + 1);
        } catch (AssertionError e) {
            resetRole(roleName);
            throw new AssertionError(e);
        }
        resetRole(roleName);
    }

    @Test
    public void testRemoveRoleInMemory() {
        String roleName = "terminator";
        RoleManager roleManager = NewsRoleManager.getRoleManager();
        resetRole(roleName);
        int originalSize = roleManager.getRole(roleName).size();

        List<String> roles = new ArrayList<>();
        roles.add("다.");
        roleManager.removeRolesInMemory(roleName, roles);
        Assert.assertEquals(originalSize - 1, roleManager.getRole(roleName).size());


        roleManager.initRole(roleName);
    }

    @Test
    public void testRemoveRoleInLocal() {
        String roleName = "terminator";
        RoleManager roleManager = NewsRoleManager.getRoleManager();
        try{
            FileManager fileManager = new FileManagerImpl();
            int originalSize = roleManager.getRole(roleName).size();
            List<String> roles = new ArrayList<>();
            roles.add("한다");
            roleManager.removeRolesInLocal(roleName, roles);

            Assert.assertEquals(originalSize - 1, roleManager.getRole(roleName).size());


            Collection<String> localRole = fileManager.readFile("/role/news/terminator.role");
            Assert.assertEquals(localRole.size() , originalSize - 1);

        } catch (AssertionError e) {
            resetRole(roleName);
            throw new AssertionError(e);
        }
        resetRole(roleName);

        roleManager.initRole(roleName);
    }


    private void resetRole(String roleName) {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("다.");
        roles.add("한다");
        new FileManagerImpl().writeFile("/role/news/" + roleName + ".role", roles);
    }

}
