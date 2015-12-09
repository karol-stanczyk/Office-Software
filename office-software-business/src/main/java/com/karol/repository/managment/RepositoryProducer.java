package com.karol.repository.managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karol.repository.ContractRepository;
import com.karol.repository.ContractorRepository;
import com.karol.repository.InvoiceRepository;
import com.karol.repository.TransferRepository;
import com.karol.repository.connection.EntityManager;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.functions.VoidFunction;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;

@Singleton
public class RepositoryProducer {

    private static final Logger log = Logger.getLogger(RepositoryProducer.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    private EntityManager entityManager;
    @Inject
    private ContractorRepository contractorRepository;
    @Inject
    private ContractRepository contractRepository;
    @Inject
    private InvoiceRepository invoiceRepository;
    @Inject
    private TransferRepository transferRepository;

    public ContractorRepository getContractorRepository() {
        return proxy(contractorRepository);
    }

    public ContractRepository getContractRepository() {
        return proxy(contractRepository);
    }

    public InvoiceRepository getInvoiceRepository() {
        return proxy(invoiceRepository);
    }

    public TransferRepository getTransferRepository() {
        return proxy(transferRepository);
    }

    @SuppressWarnings("unchecked")
    private <T> T proxy(final T object) {
        return (T) Enhancer.create(object.getClass(),
                (InvocationHandler) (proxy, method, args) -> {
                    if (hasAnnotation(method, LogEvent.class)) {
                        log.debug("Invoke method: " + getMethodName(method) + " with parameters " + mapper.writeValueAsString(Arrays.asList(args)));
                    }
                    if (hasAnnotation(method, Transactional.class)) {
                        Object result = null;
                        if (hasAnnotation(method, LogEvent.class)) {
                            result = logException(() -> transactionalHandling(object, method, args));
                        } else {
                            result = transactionalHandling(object, method, args);
                        }
                        return result;
                    } else {
                        return method.invoke(object, args);
                    }
                });
    }

    private Object logException(VoidFunction function) throws Exception {
        Object result = null;
        try {
            function.run();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
        return result;
    }

    private <T> Object transactionalHandling(T object, Method method, Object[] args) {
        try {
            entityManager.getTransaction().begin();
            Object result = method.invoke(object, args);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DatabaseException(e.getCause().getMessage());
        }
    }

    private String getMethodName(Method method) {
        return " '" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "' ";
    }

    private boolean hasAnnotation(Executable executable, Class c) {
        return executable.getAnnotation(c) != null;
    }
}
