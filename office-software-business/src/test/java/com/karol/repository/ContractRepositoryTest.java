package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.model.Period;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ContractRepositoryTest extends MockDatabaseTest {

    private ContractRepository contractRepository = new ContractRepository(entityManager);
    private ContractorRepository contractorRepository = new ContractorRepository(entityManager);

    @Test
    public void shouldAddContractToContractor() {
        //given
        Contractor contractor = contractorRepository.persist(createContractor());
        Contract contract = createContract();
        //when
        contractRepository.persist(contract, contractor);
        //then
        Contractor searchResult = contractorRepository.findById(contractor.getId()).get();
        assertEquals(1, searchResult.getContractList().size());
    }

    @Test
    public void shouldDeleteContractFromContractor() {
        //given
        Contractor contractor = contractorRepository.persist(createContractor());
        Contract contract = createContract();
        //when
        contractRepository.persist(contract, contractor);
        contractRepository.delete(contract, contractor);
        //then
        Contractor searchResult = contractorRepository.findById(contractor.getId()).get();
        assertEquals(0, searchResult.getContractList().size());
    }

    @Test
    public void shouldUpdateContractData() {
        //given
        Contractor contractor = contractorRepository.persist(createContractor());
        Contract contract = contractRepository.persist(createContract(), contractor);
        //when
        contract.setPeriod(Period.QUARTERLY);
        //then
        Contract searchResult = contractorRepository.findById(contractor.getId()).get().getContractList().get(0);
        assertEquals(Period.QUARTERLY, searchResult.getPeriod());
    }
}
