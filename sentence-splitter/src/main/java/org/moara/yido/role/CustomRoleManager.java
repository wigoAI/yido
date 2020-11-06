package org.moara.yido.role;


public class CustomRoleManager extends RoleManager {
    private static final CustomRoleManager CUSTOM_ROLE_MANAGER = new CustomRoleManager("custom");

    private CustomRoleManager(String roleManagerName) {
        super(roleManagerName);
    }


    /**
     * 기본 메타데이터 관리자 인스턴스 반환
     * @return BasicRoleManager
     */
    public static RoleManager getRoleManager() { return CUSTOM_ROLE_MANAGER; }

}
