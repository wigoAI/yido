package org.yido.role;

import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.junit.Test;
import org.yido.dataInput.FileReader;

public class RoleTest {

    @Test
    public void createRoleTest() {
        FileReader filereader = new FileReader("/data/test2.txt");

        for(String str : filereader.getSplitFile("|")) {
            System.out.println(str);
            for (Eojeol eojeol: Analyzer.parseEojeolJava(str)) {
                System.out.println(eojeol.surface());

            }
        }

    }
}
