package org.moara.yido.role;

import java.util.HashSet;

public class ConnectiveRole implements RoleManager{


    private static ConnectiveRole connectiveRole = new ConnectiveRole();

    private ConnectiveRole() {

    }

    @Override
    public void initRole() {

    }

    @Override
    public HashSet<String> getRole() {
        return null;
    }


    public static RoleManager getInstance() { return ConnectiveRole.connectiveRole; }
}
