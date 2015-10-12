package com.karol.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CrudRepository<T> {
    private static final String PERSISTENCE_UNIT_NAME = "lite_unit";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public T persist(T object) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();
        manager.close();
        return object;
    }

    public T find(Class<T> clazz, long id) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        T result = manager.find(clazz, id);
        manager.close();
        return result;
    }

    public T update(T object) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        T result = manager.merge(object);
        manager.getTransaction().commit();
        manager.close();
        return result;
    }

    public void delete(T object) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        T result = manager.merge(object);
        manager.remove(result);
        manager.getTransaction().commit();
    }
}
