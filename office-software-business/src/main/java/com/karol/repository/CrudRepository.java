package com.karol.repository;

import com.karol.repository.access.EntityManager;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class CrudRepository<T> {

    @Inject
    private EntityManager manager;

    public T persist(T object) {
        manager.persist(object);
        return object;
    }

    public T find(Class<T> clazz, long id) {
        return manager.find(clazz, id);
    }

    public T update(T object) {
        return manager.merge(object);
    }

    public void delete(T object) {
        T result = manager.merge(object);
        manager.remove(result);
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String namedQueryName) {
        return manager.createNamedQuery(namedQueryName).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findWithNamedQuery(String queryName, int resultLimit) {
        return manager.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        Query query = manager.createNamedQuery(namedQueryName);
        if (resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}
