package org.moara.yido.role;

public class PublicRoleManager extends RoleManager {

    private static final PublicRoleManager PUBLIC_ROLE_MANAGER = new PublicRoleManager("");

    private PublicRoleManager(String roleManagerName) {
        super(roleManagerName);
    }

    public static RoleManager getRoleManager() {
        return PUBLIC_ROLE_MANAGER;
    }
}
