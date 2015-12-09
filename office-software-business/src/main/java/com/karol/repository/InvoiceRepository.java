package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.repository.connection.EntityManager;
import com.karol.repository.managment.LogEvent;
import com.karol.repository.managment.Transactional;

import javax.inject.Inject;
import java.util.Optional;

public class InvoiceRepository {

    @Inject private CrudRepository<Invoice> invoiceRepository;
    @Inject private ContractRepository contractRepository;

    public InvoiceRepository() {
    }

    public InvoiceRepository(EntityManager entityManager) {
        invoiceRepository = new CrudRepository<>(entityManager);
        contractRepository = new ContractRepository(entityManager);
    }

    public Optional<Invoice> findById(long id) {
        return Optional.ofNullable(invoiceRepository.find(Invoice.class, id));
    }

    @LogEvent
    @Transactional
    public Invoice persist(Invoice invoice, Contract contract) {
        invoice.setContract(contract);
        invoice = invoiceRepository.persist(invoice);
        contract.getInvoiceList().add(invoice);
        return invoice;
    }

    @LogEvent
    @Transactional
    public Invoice update(Invoice invoice) {
        return invoiceRepository.update(invoice);
    }

    @LogEvent
    @Transactional
    public void delete(Invoice invoice, Contract contract) {
        contract.getInvoiceList().remove(invoice);
        invoiceRepository.delete(invoice);
    }
}
