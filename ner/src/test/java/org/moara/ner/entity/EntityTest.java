package org.moara.ner.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.person.ReporterEntity;

public class EntityTest {

    @Test
    public void testEquals() {
        ReporterEntity reporterEntity = new ReporterEntity("김경민", 4, 7);
        ReporterEntity equalsEntity = new ReporterEntity("김경민", 4, 7);
        ReporterEntity notEqualsEntity1 = new ReporterEntity("김민경", 4, 7);
        ReporterEntity notEqualsEntity2 = new ReporterEntity("김경민", 5, 7);
        ReporterEntity notEqualsEntity3 = new ReporterEntity("김경민", 4, 8);


        Assertions.assertTrue(reporterEntity.equals(reporterEntity));
        Assertions.assertTrue(reporterEntity.equals(equalsEntity));
        Assertions.assertEquals(equalsEntity, reporterEntity);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity1);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity2);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity3);
    }


}
