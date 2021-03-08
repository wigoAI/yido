package org.moara.yido.splitter.manager;


import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        assertEquals(6, findCount);


        pattern = ExceptionRuleManager.getSameSideBracketPattern();
        matcher = pattern.matcher(data);

        findCount = 0;
        while (matcher.find()) {
            assertEquals("x", matcher.group().substring(1,2));
            findCount++;
        }
        assertEquals(2, findCount);
    }
}
