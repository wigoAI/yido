package org.moara.yido.role;


import java.util.HashSet;

/**
 * TODO 1. Role 추가기능 만들기
 */
public interface RoleManager {

    String dicPath = "/dic/";

    HashSet<String> getTerminator();
    HashSet<String> getException();
    HashSet<String> getConnective();

}
