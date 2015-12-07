package com.karol.repository;

import com.karol.repository.access.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;

public abstract class MockDatabase {

    protected static EntityManager entityManager;

    @BeforeClass
    public static void prepare() {
        boolean deleted = new File("database.db").delete();
        entityManager = new EntityManager("test_lite_unit");
    }

    @AfterClass
    public static void clean() {
        entityManager.close();
        entityManager = null;
        boolean deleted = new File("database.db").delete();
    }

    @Before
    public void startTransaction() {
        entityManager.getTransaction().begin();
    }

    @After
    public void cleanDatabase() {
        entityManager.getTransaction().rollback();
    }
}
