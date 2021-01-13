package org.moara.ner.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moara.ner.person.PersonEntity;

public class EntityTest {

    @Test
    public void testEquals() {
        PersonEntity reporterEntity = new PersonEntity("김경민","REPORTER" , 4, 7);
        PersonEntity equalsEntity = new PersonEntity("김경민","REPORTER", 4, 7);
        PersonEntity notEqualsEntity1 = new PersonEntity("김민경","REPORTER", 4, 7);
        PersonEntity notEqualsEntity2 = new PersonEntity("김경민","REPORTER", 5, 7);
        PersonEntity notEqualsEntity3 = new PersonEntity("김경민","REPORTER", 4, 8);


        Assertions.assertTrue(reporterEntity.equals(reporterEntity));
        Assertions.assertTrue(reporterEntity.equals(equalsEntity));
        Assertions.assertEquals(equalsEntity, reporterEntity);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity1);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity2);
        Assertions.assertNotEquals(reporterEntity, notEqualsEntity3);
    }

    @Test
    public void testHashcode() {
        PersonEntity reporterEntity = new PersonEntity("김경민","REPORTER", 4, 7);
        PersonEntity equalsEntity = new PersonEntity("김경민","REPORTER", 4, 7);
        PersonEntity notEqualsEntity1 = new PersonEntity("김민경","REPORTER", 4, 7);
        PersonEntity notEqualsEntity2 = new PersonEntity("김경민","REPORTER", 5, 7);
        PersonEntity notEqualsEntity3 = new PersonEntity("김경민","REPORTER", 4, 8);

        Assertions.assertEquals(reporterEntity.hashCode(), reporterEntity.hashCode());
        Assertions.assertEquals(reporterEntity.hashCode(), equalsEntity.hashCode());
        Assertions.assertEquals(equalsEntity.hashCode(), reporterEntity.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity1.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity2.hashCode());
        Assertions.assertNotEquals(reporterEntity.hashCode(), notEqualsEntity3.hashCode());

    }


}
