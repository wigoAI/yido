package org.moara.splitter.manager;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetaTest {

    /**
     * Meta rule read test (json)
     */
    @Test
    public void testMetaRule() {
        String data = "[x]d'x'd`x`d‘x’d“x”d{x}d<x>d(x)d\"x\"d";
        Pattern pattern = ExceptionRuleManager.getDifferentSideBracketPattern();


        Matcher matcher = pattern.matcher(data);

        int findCount = 0;
        while (matcher.find()) {
            Assert.assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        Assert.assertEquals(6, findCount);


        pattern = ExceptionRuleManager.getSameSideBracketPattern();
        matcher = pattern.matcher(data);

        findCount = 0;
        while (matcher.find()) {
            Assert.assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        Assert.assertEquals(2, findCount);
    }
}
