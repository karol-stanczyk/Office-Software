package com.karol.repository;

import com.karol.repository.access.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public abstract class MockDatabase {

    protected static EntityManager entityManager;

    @BeforeClass
    public static void prepare() throws IOException {
        entityManager = new EntityManager("test_lite_unit");
    }

    @AfterClass
    public static void clean() throws IOException, InterruptedException {
        entityManager.close();
        Files.deleteIfExists(FileSystems.getDefault().getPath("database.db"));
    }

    @Before
    public void startTransaction() {
        entityManager.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
