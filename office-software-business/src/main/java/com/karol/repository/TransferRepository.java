package com.karol.repository;

import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.repository.access.EntityManager;
import com.karol.repository.access.LogEvent;
import com.karol.repository.access.Transactional;

import javax.inject.Inject;
import java.util.Optional;

public class TransferRepository {

    @Inject private InvoiceRepository invoiceRepository;
    @Inject private CrudRepository<Transfer> transferCrudRepository;


    public TransferRepository() {
    }

    public TransferRepository(EntityManager entityManager) {
        invoiceRepository = new InvoiceRepository(entityManager);
        transferCrudRepository = new CrudRepository<>(entityManager);
    }

    public Optional<Transfer> findById(long id) {
        return Optional.ofNullable(transferCrudRepository.find(Transfer.class, id));
    }

    @LogEvent
    @Transactional
    public Transfer persist(Transfer transfer, Invoice invoice) {
        transfer.setInvoice(invoice);
        transfer = transferCrudRepository.persist(transfer);
        invoice.getTransferList().add(transfer);
        return transfer;
    }

    @LogEvent
    @Transactional
    public Transfer update(Transfer transfer) {
        return transferCrudRepository.update(transfer);
    }

    @LogEvent
    @Transactional
    public void delete(Transfer transfer, Invoice invoice) {
        invoice.getTransferList().remove(transfer);
        transferCrudRepository.delete(transfer);
        invoiceRepository.update(invoice);
    }
}
