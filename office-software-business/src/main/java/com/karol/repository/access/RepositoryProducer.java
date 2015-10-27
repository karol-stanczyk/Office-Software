package com.karol.repository.access;

import com.karol.repository.ContractRepository;
import com.karol.repository.ContractorRepository;
import com.karol.repository.utils.DatabaseException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

@Singleton
public class RepositoryProducer {

    private static final Logger log = Logger.getLogger(RepositoryProducer.class);

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
                    if (hasAnnotation(method, Transactional.class)) {
                        try {
                            log.debug("Transaction for method" + getMethodName(method) + "start.");
                            entityManager.getTransaction().begin();
                            Object result = method.invoke(object, args);
                            entityManager.getTransaction().commit();
                            log.debug("Transaction for method" + getMethodName(method) + "commit.");
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

    private String getMethodName(Method method) {
        return " '" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "' ";
    }

    private boolean hasAnnotation(Executable executable, Class c) {
        return executable.getAnnotation(c) != null;
    }
}
