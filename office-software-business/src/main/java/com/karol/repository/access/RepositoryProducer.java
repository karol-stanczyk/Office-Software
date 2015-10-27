package com.karol.repository.access;

import com.karol.repository.ContractRepository;
import com.karol.repository.ContractorRepository;
import com.karol.repository.utils.DatabaseException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RepositoryProducer {

    @Inject
    private EntityManager entityManager;
    @Inject
    private ContractorRepository contractorRepository;
    @Inject
    private ContractRepository contractRepository;

    public ContractorRepository getContractorRepository() {
        return proxy(contractorRepository);
    }

    public ContractRepository getContractRepository() {
        return proxy(contractRepository);
    }

    @SuppressWarnings("unchecked")
    private <T> T proxy(final T object) {
        return (T) Enhancer.create(object.getClass(),
                (InvocationHandler) (proxy, method, args) -> {
                    if (method.getAnnotation(Transactional.class) != null) {
                        try {
                            entityManager.getTransaction().begin();
                            Object result = method.invoke(object, args);
                            entityManager.getTransaction().commit();
                            return result;
                        } catch (Exception e) {
                            entityManager.getTransaction().rollback();
                            throw new DatabaseException(e.getCause().getMessage());
                        }
                    } else {
                        return method.invoke(object, args);
                    }
                });
    }
}
