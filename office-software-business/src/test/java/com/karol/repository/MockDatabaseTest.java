package com.karol.repository;

import com.karol.model.*;
import com.karol.repository.access.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Date;

public abstract class MockDatabaseTest {

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

    protected Contractor createContractor() {
        return new Contractor("", "", "");
    }

    protected Contract createContract() {
        return new Contract(Period.ANNUAL, "", new Date(), new Date());
    }

    protected Invoice createInvoice() {
        return new Invoice();
    }

    protected Transfer createTransfer() {
        return new Transfer();
    }
}
