package org.moara.yido.role;

import java.util.HashSet;

public class TerminatorRole implements RoleManager{


    private static TerminatorRole terminatorRole = new TerminatorRole();

    private TerminatorRole() {

    }

    @Override
    public HashSet<String> getRole() {
        return null;
    }

    public static RoleManager getInstance() {

        return TerminatorRole.terminatorRole;
    }
}
