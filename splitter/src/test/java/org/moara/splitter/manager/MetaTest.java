package org.moara.splitter.manager;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetaTest {

    /**
     * Meta role read test (json)
     */
    @Test
    public void testMetaRole() {
        String data = "[x]d'x'd`x`d‘x’d“x”d{x}d<x>d(x)d\"x\"d";
        Pattern pattern = ExceptionRoleManager.getDifferentSideBracketPattern();


        Matcher matcher = pattern.matcher(data);

        int findCount = 0;
        while (matcher.find()) {
            Assert.assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        Assert.assertEquals(6, findCount);


        pattern = ExceptionRoleManager.getSameSideBracketPattern();
        matcher = pattern.matcher(data);

        findCount = 0;
        while (matcher.find()) {
            Assert.assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        Assert.assertEquals(2, findCount);
    }
}
