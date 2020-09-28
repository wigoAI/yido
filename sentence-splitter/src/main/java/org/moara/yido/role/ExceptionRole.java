package org.moara.yido.role;

import java.util.HashSet;

public class ExceptionRole implements RoleManager{

    private static ExceptionRole exceptionRole = new ExceptionRole();

    private ExceptionRole() {

    }

    @Override
    public HashSet<String> getRole() {
        return null;
    }

    public static RoleManager getInstance() {
        return ExceptionRole.exceptionRole;
    }
}
