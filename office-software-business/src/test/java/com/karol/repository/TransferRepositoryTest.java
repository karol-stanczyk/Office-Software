package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.model.Invoice;
import com.karol.model.Transfer;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TransferRepositoryTest extends MockDatabaseTest {

    private TransferRepository transferRepository = new TransferRepository(entityManager);
    private ContractorRepository contractorRepository = new ContractorRepository(entityManager);
    private ContractRepository contractRepository = new ContractRepository(entityManager);
    private InvoiceRepository invoiceRepository = new InvoiceRepository(entityManager);

    @Test
    public void shouldAddTransferToInvoice() {
        //given
        Transfer transfer = createTransfer();
        Invoice invoice = createAndStoreInvoice();
        //when
        transferRepository.persist(transfer, invoice);
        //then
        Invoice searchResult = invoiceRepository.findById(invoice.getId()).get();
        assertNotEquals(0, searchResult.getTransferList().size());
    }

    @Test
    public void shouldUpdateTransfer() {
        //given
        Transfer transfer = createTransfer();
        Invoice invoice = createAndStoreInvoice();
        //when
        transferRepository.persist(transfer, invoice);
        transfer.setGrossValue(1000);
        //then
        Transfer searchResult = transferRepository.findById(transfer.getId()).get();
        assertEquals(1000, searchResult.getGrossValue(), 0);
    }

    @Test
    public void shouldDeleteTransfer() {
        //given
        Transfer transfer = createTransfer();
        Invoice invoice = createAndStoreInvoice();
        //when
        transferRepository.persist(transfer, invoice);
        transferRepository.delete(transfer, invoice);
        //then
        Optional<Transfer> searchResult = transferRepository.findById(transfer.getId());
        assertEquals(false, searchResult.isPresent());

    }


    private Invoice createAndStoreInvoice() {
        Contractor contractor = contractorRepository.persist(createContractor());
        Contract contract = contractRepository.persist(createContract(), contractor);
        Invoice invoice = invoiceRepository.persist(createInvoice(), contract);
        return invoice;
    }
}
