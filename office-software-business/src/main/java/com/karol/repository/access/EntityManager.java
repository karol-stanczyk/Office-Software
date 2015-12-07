package com.karol.repository.access;

import javax.inject.Singleton;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.Map;

@Singleton
public class EntityManager {

    private static final String PERSISTENCE_UNIT_NAME = "lite_unit";
    private EntityManagerFactory entityManagerFactory;
    javax.persistence.EntityManager entityManager;

    public EntityManager() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager(String persistenceUnitName) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public javax.persistence.EntityManager getEntityManager() {
        return entityManager;
    }

    public void persist(Object o) {
        entityManager.persist(o);
    }

    public <T> T merge(T t) {
        return entityManager.merge(t);
    }

    public void remove(Object o) {
        entityManager.remove(o);
    }

    public <T> T find(Class<T> aClass, Object o) {
        return entityManager.find(aClass, o);
    }

    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return entityManager.find(aClass, o, map);
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return entityManager.find(aClass, o, lockModeType);
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return entityManager.find(aClass, o, lockModeType, map);
    }

    public Query createQuery(String s) {
        return entityManager.createQuery(s);
    }

    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return entityManager.createQuery(criteriaUpdate);
    }

    public Query createQuery(CriteriaDelete criteriaDelete) {
        return entityManager.createQuery(criteriaDelete);
    }

    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return createQuery(s, aClass);
    }

    public Query createNamedQuery(String s) {
        return entityManager.createNamedQuery(s);
    }

    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return entityManager.createNamedQuery(s, aClass);
    }

    public Query createNativeQuery(String s) {
        return entityManager.createNativeQuery(s);
    }

    public Query createNativeQuery(String s, Class aClass) {
        return entityManager.createNativeQuery(s, aClass);
    }

    public Query createNativeQuery(String s, String s1) {
        return entityManager.createNativeQuery(s, s1);
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public boolean isOpen() {
        return entityManager.isOpen();
    }

    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }
}
