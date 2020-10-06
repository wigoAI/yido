package org.moara.yido.role;

import org.moara.yido.file.FileManager;
import org.moara.yido.file.FileManagerImpl;

import java.util.HashSet;


/**
 * 룰 관리 클래스
 *
 */
public class BasicRoleManager implements RoleManager {
    private static final BasicRoleManager BASIC_ROLE_MANAGER = new BasicRoleManager();
    private final HashSet<String> basicConnective = new HashSet<>();
    private final HashSet<String> basicTerminator = new HashSet<>();
    private final HashSet<String> basicException = new HashSet<>();

    private final FileManager fileManager = new FileManagerImpl();

    private BasicRoleManager() { }
    public static BasicRoleManager getRoleManager() { return BASIC_ROLE_MANAGER; }


    @Override
    public HashSet<String> getTerminator() {
        initBasicTerminator();
        return basicTerminator;

    }

    @Override
    public HashSet<String> getException() {
        initBasicException();
        return basicException;

    }

    @Override
    public HashSet<String> getConnective() {

        initBasicConnective();
        return basicConnective;

    }

    private void initBasicConnective() {
        if(basicConnective.size() == 0) {
            fileManager.readFile(dicPath + "connective.dic");
            basicConnective.addAll(fileManager.getFile());
        }
    }
    private void initBasicTerminator() {
        if(basicConnective.size() == 0) {
            fileManager.readFile(dicPath + "terminator.dic");
            basicTerminator.addAll(fileManager.getFile());
        }
    }

    private void initBasicException() {
        if(basicConnective.size() == 0) {
            fileManager.readFile(dicPath + "exception.dic");
            basicException.addAll(fileManager.getFile());
        }
    }



}
