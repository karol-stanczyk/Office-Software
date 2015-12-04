package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.repository.access.Transactional;

import javax.inject.Inject;

public class InvoiceRepository {

    @Inject private CrudRepository<Invoice> invoiceRepository;
    @Inject private ContractRepository contractRepository;

    @Transactional
    public Invoice persist(Invoice invoice, Contract contract) {
        invoice.setContract(contract);
        invoice = invoiceRepository.persist(invoice);
        contract.getInvoiceList().add(invoice);
        contractRepository.update(contract);
        return invoice;
    }

    @Transactional
    public Invoice update(Invoice invoice) {
        return invoiceRepository.update(invoice);
    }

    @Transactional
    public void delete(Invoice invoice, Contract contract) {
        contract.getInvoiceList().remove(invoice);
        invoiceRepository.delete(invoice);
    }
}
