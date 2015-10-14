package com.karol.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String namedQueryName) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        return manager.createNamedQuery(namedQueryName).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String queryName, int resultLimit) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<T> result = manager.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
        manager.close();
        return result;
    }

    public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        Query query = manager.createNamedQuery(namedQueryName);
        if (resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List result = query.getResultList();
        manager.close();
        return result;
    }
}
