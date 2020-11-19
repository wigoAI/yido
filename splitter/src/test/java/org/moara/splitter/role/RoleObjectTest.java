package org.moara.splitter.role;

import org.junit.Assert;
import org.junit.Test;

public class RoleObjectTest {

    @Test
    public void testCreateRoleObject() {
        // terminator
        Role role1 = new Role("다.", false, 'B');
        Assert.assertEquals("다.", role1.getValue());
        Assert.assertEquals(false, role1.isUsePublicValidation());
        Assert.assertEquals('B', role1.getSplitPosition());


        // number
        Role role2 = new Role("1.", true, 'F');
        Assert.assertEquals("1.", role2.getValue());
        Assert.assertEquals(true, role2.isUsePublicValidation());
        Assert.assertEquals('F', role2.getSplitPosition());
    }
}
