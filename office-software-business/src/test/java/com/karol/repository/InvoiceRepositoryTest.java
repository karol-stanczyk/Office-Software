package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.model.Invoice;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class InvoiceRepositoryTest extends MockDatabaseTest {

    private InvoiceRepository invoiceRepository = new InvoiceRepository(entityManager);
    private ContractorRepository contractorRepository = new ContractorRepository(entityManager);
    private ContractRepository contractRepository = new ContractRepository(entityManager);

    @Test
    public void shouldAddInvoiceToContract() {
        //given
        Contract contract = createAndPersistContract();
        //when
        Invoice invoice = invoiceRepository.persist(createInvoice(), contract);
        //then
        assertNotEquals(0, invoice.getId());
    }

    @Test
    public void shouldDeleteContract() {
        //given
        Contract contract = createAndPersistContract();
        Invoice invoice = createInvoice();
        invoiceRepository.persist(invoice, contract);
        //when
        invoiceRepository.delete(invoice, contract);
        //then
        Contractor searchResult = contractorRepository.findById(contract.getContractor().getId()).get();
        assertEquals(0, searchResult.getContractList().get(0).getInvoiceList().size());
    }

    @Test
    public void shouldUpdateInvoice() {
        //given
        Contract contract = createAndPersistContract();
        Invoice invoice = invoiceRepository.persist(createInvoice(), contract);
        //when
        invoice.setGrossValue(1000);
        invoiceRepository.update(invoice);
        //then
        Invoice searchResult = invoiceRepository.findById(invoice.getId()).get();
        assertEquals(1000, searchResult.getGrossValue(), 0);
    }

    @Test
    public void shouldFindInvoiceById() {
        //given
        Contract contract = createAndPersistContract();
        Invoice invoice = invoiceRepository.persist(createInvoice(), contract);
        //when
        Optional<Invoice> searchResult = invoiceRepository.findById(invoice.getId());
        //then
        assertEquals(true, searchResult.isPresent());
    }

    private Contract createAndPersistContract() {
        Contractor contractor = contractorRepository.persist(createContractor());
        return contractRepository.persist(createContract(), contractor);
    }
}
