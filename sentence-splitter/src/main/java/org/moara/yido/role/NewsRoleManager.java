package org.moara.yido.role;

import org.moara.yido.file.FileManager;
import org.moara.yido.file.FileManagerImpl;

import java.util.HashSet;

public class NewsRoleManager implements RoleManager {
    private static final NewsRoleManager NEWS_ROLE_MANAGER = new NewsRoleManager();
    private final HashSet<String> newsConnective = new HashSet<>();
    private final HashSet<String> newsTerminator = new HashSet<>();
    private final HashSet<String> newsException = new HashSet<>();
    private final FileManager fileManager = new FileManagerImpl();

    private NewsRoleManager() { }
    public static NewsRoleManager getRoleManager() { return NEWS_ROLE_MANAGER; }

    @Override
    public HashSet<String> getTerminator() {
        initTerminator();
        return newsTerminator;
    }

    @Override
    public HashSet<String> getException() {
        initException();
        return newsException;
    }

    @Override
    public HashSet<String> getConnective() {
        initConnective();
        return newsConnective;
    }

    private void initConnective() {
        if(newsConnective.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "connective.dic");
            newsConnective.addAll(fileManager.getFile());
        }
    }

    private void initTerminator() {
        if(newsTerminator.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "terminator.dic");
            newsTerminator.addAll(fileManager.getFile());
        }
    }

    private void initException() {
        if(newsException.size() == 0) {
            fileManager.readFile(dicPath + "news/" + "exception.dic");
            newsException.addAll(fileManager.getFile());
        }
    }
}
