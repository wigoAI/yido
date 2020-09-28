package org.moara.yido.role;

import org.moara.yido.file.FileManagerImpl;

import java.io.IOException;
import java.util.HashSet;

public class TerminatorRole implements RoleManager{

    private FileManagerImpl fileManager = new FileManagerImpl();
    private HashSet<String> role = new HashSet<>();
    private static TerminatorRole terminatorRole = new TerminatorRole();


    private TerminatorRole() {
        initRole();
    }

    @Override
    public void initRole() {

        try {
            fileManager.readFile("/data/terminator.txt");
            role.addAll(fileManager.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<String> getRole() {
        return role;
    }

    public static RoleManager getInstance() {

        return TerminatorRole.terminatorRole;
    }
}
