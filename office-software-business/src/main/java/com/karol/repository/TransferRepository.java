package com.karol.repository;

import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.repository.access.LogEvent;
import com.karol.repository.access.Transactional;

import javax.inject.Inject;

public class TransferRepository {

    @Inject private InvoiceRepository invoiceRepository;
    @Inject private CrudRepository<Transfer> transferCrudRepository;

    @LogEvent
    @Transactional
    public Transfer persist(Invoice invoice, Transfer transfer) {
        transfer.setInvoice(invoice);
        transfer = transferCrudRepository.persist(transfer);
        invoice.getTransferList().add(transfer);
        invoice = invoiceRepository.update(invoice);
        return transfer;
    }

    @LogEvent
    @Transactional
    public Transfer update(Transfer transfer) {
        return transferCrudRepository.update(transfer);
    }

    @LogEvent
    @Transactional
    public void delete(Invoice invoice, Transfer transfer) {
        invoice.getTransferList().remove(transfer);
        transferCrudRepository.delete(transfer);
        invoiceRepository.update(invoice);
    }
}
