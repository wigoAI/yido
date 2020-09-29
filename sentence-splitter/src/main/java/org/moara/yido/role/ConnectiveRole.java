package org.moara.yido.role;

import org.moara.yido.file.FileManagerImpl;

import java.io.IOException;
import java.util.HashSet;

public class ConnectiveRole implements RoleManager{

    private FileManagerImpl fileManager = new FileManagerImpl();
    private static ConnectiveRole connectiveRole = new ConnectiveRole();
    private HashSet<String> role = new HashSet<>();

    private ConnectiveRole() { initRole(); }

    @Override
    public void initRole() {
        try {
            fileManager.readFile("/data/connective.txt");
            role.addAll(fileManager.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public HashSet<String> getRole() { return role; }
    public static RoleManager getInstance() { return ConnectiveRole.connectiveRole; }
}
