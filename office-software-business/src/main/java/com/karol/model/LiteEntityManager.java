package com.karol.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LiteEntityManager {

    private static final String PERSISTENCE_UNIT_NAME = "lite_unit";

    private EntityManager entityManager;

    public LiteEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
