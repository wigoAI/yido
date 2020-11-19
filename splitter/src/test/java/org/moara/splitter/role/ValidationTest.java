package org.moara.splitter.role;

import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {

    @Test
    public void testCreateValidationObject() {
        Validation validation = new Validation("면", 'N', 'B');
        Assert.assertEquals("면", validation.getValue());
        Assert.assertEquals('N', validation.getMatchFlag());
        Assert.assertEquals('B', validation.getComparePosition());
    }
}
