package org.moara.yido.role;


import java.util.HashSet;

public interface RoleManager {

    String dicPath = "/dic/";

    HashSet<String> getTerminator();
    HashSet<String> getException();
    HashSet<String> getConnective();

}
