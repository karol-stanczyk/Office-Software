package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Invoice;

import javax.inject.Inject;

public class InvoiceRepository {

    @Inject private CrudRepository<Invoice> invoiceRepository;
    @Inject private ContractRepository contractRepository;

    public Invoice persist(Invoice invoice, Contract contract) {
        invoice = invoiceRepository.persist(invoice);
        contract.getInvoiceList().add(invoice);
        contractRepository.update(contract);
        return invoice;
    }

    public Invoice update(Invoice invoice) {
        return invoiceRepository.update(invoice);
    }
}
