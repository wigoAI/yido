package org.moara.ner.test.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.entity.NamedEntity;

public class EntityTest {

    @Test
    public void testEquals() {
        NamedEntity reporterEntity = new NamedEntity("김경민","ps_reporter" , 4, 7);
        NamedEntity equalsEntity = new NamedEntity("김경민","ps_reporter", 4, 7);
        NamedEntity notEqualsEntity1 = new NamedEntity("김민경","ps_reporter", 4, 7);
        NamedEntity notEqualsEntity2 = new NamedEntity("김경민","ps_reporter", 5, 7);
        NamedEntity notEqualsEntity3 = new NamedEntity("김경민","ps_reporter", 4, 8);


        Assertions.assertTrue(reporterEntity.equals(reporterEntity));
        Assertions.assertTrue(reporterEntity.equals(equalsEntity));
        Assertions.assertEquals(equalsEntity, reporterEntity);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity1);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity2);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity3);
    }

    @Test
    public void testHashcode() {
        NamedEntity reporterEntity = new NamedEntity("김경민","ps_reporter", 4, 7);
        NamedEntity equalsEntity = new NamedEntity("김경민","ps_reporter", 4, 7);
        NamedEntity notEqualsEntity1 = new NamedEntity("김민경","ps_reporter", 4, 7);
        NamedEntity notEqualsEntity2 = new NamedEntity("김경민","ps_reporter", 5, 7);
        NamedEntity notEqualsEntity3 = new NamedEntity("김경민","ps_reporter", 4, 8);

        Assertions.assertEquals(reporterEntity.hashCode(), reporterEntity.hashCode());
        Assertions.assertEquals(reporterEntity.hashCode(), equalsEntity.hashCode());
        Assertions.assertEquals(equalsEntity.hashCode(), reporterEntity.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity1.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity2.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity3.hashCode());

    }


}
