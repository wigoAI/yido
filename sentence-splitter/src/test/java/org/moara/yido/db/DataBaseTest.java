package org.moara.yido.db;

import org.junit.Test;
import org.moara.yido.db.entity.TbAraSenGroupString;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DataBaseTest {

    /**
     * Connection test with mariaDB
     * */
    @Test
    public void mariadbConnectionTest() throws Exception {
        DataBase db = new DataBase("mariadb");
    }


    /**
    * Get Roles in WIGO Database
    * */
    @Test
    public void getRolesTest() throws Exception {
        DataBase db = new DataBase();

        String sentenceStringDataTable = "TB_ARA_SEN_GROUP_STRING";
        String sentenceStringDataColumn = "VAL_STRING";
        String connectiveDataGroup = "CD_GROUP='S2'";
        String terminatorDataGroup = "CD_GROUP='S4'";
        for(String str : db.doQueryAndGetList(sentenceStringDataColumn, sentenceStringDataTable, terminatorDataGroup)){
            System.out.println(str);

        }

    }

    @Test
    public void hibernateTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("yido");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();  // 4. 트랜잭션 시작
            // 5. 비지니스 로직 수행

            System.out.println(entityManager.find(TbAraSenGroupString.class, "싶다").getValString());



            entityTransaction.commit(); // 6. 트랜잭션 커밋
        } catch (Exception e) {
            entityTransaction.rollback();   // 6. 트랜잭션 롤백
        } finally {
            entityManager.close();      // 7. 엔티티 매니저 종료
        }
        entityManagerFactory.close();
    }
}
