package com.karol.repository;

import com.karol.model.Contractor;
import com.karol.repository.utils.DatabaseException;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ContractorRepositoryTest extends MockDatabaseTest {

    private ContractorRepository contractorRepository = new ContractorRepository(entityManager);

    @Test
    public void shouldNotReturnAnyContractors() {
        //given
        //when
        Optional<Contractor> contractor = contractorRepository.findById(1);
        //then
        assertEquals(contractor.isPresent(), false);
    }

    @Test
    public void shouldAddContractor() {
        //given
        //when
        Contractor contractor = contractorRepository.persist(createContractor());
        //then
        assertNotNull(contractor.getId());
    }

    @Test
    public void shouldFindContractorById() {
        //given
        long id = contractorRepository.persist(createContractor()).getId();
        //when
        //then
        Optional<Contractor> searchResult = contractorRepository.findById(id);
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByPesel() {
        //given
        Contractor contractor = createContractor();
        contractor.setPesel("123456789");
        //when
        contractorRepository.persist(contractor);
        //then
        Optional<Contractor> searchResult = contractorRepository.findByPesel("123456789");
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByNip() {
        //given
        Contractor contractor = createContractor();
        contractor.setNip("123456");
        //when
        contractorRepository.persist(contractor);
        //then
        Optional<Contractor> searchResult = contractorRepository.findByNip("123456");
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByName() {
        //given
        Contractor contractor = new Contractor("Karol", "", "");
        //when
        contractorRepository.persist(contractor);
        //then
        List<Contractor> searchResult = contractorRepository.findByName("Karol");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldFindContractorByLastName() {
        //given
        Contractor contractor = new Contractor("", "Stanczyk", "");
        //when
        contractorRepository.persist(contractor);
        //then
        List<Contractor> searchResult = contractorRepository.findByLastName("Stanczyk");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldUpdateContractorData() {
        //given
        Contractor contractor = contractorRepository.persist(createContractor());
        //when
        contractor.setName("Zenek");
        //then
        List<Contractor> searchResult = contractorRepository.findByName("Zenek");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldDeleteContractor() {
        //given
        Contractor contractor = contractorRepository.persist(createContractor());
        assertEquals(true, contractorRepository.findAll().size() > 0);
        //when
        contractorRepository.delete(contractor);
        //then
        assertEquals(true, contractorRepository.findAll().size() == 0);
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowExceptionCozOfSecondContractorWithSamePesel() {
        //given
        Contractor contractor = createContractor();
        contractor.setPesel("123456");
        Contractor contractor2 = createContractor();
        contractor2.setPesel("123456");
        //when
        contractorRepository.persist(contractor);
        contractorRepository.persist(contractor2);
        //then throw Exception
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowExceptionCozOfSecondContractorWithSameNip() {
        //given
        Contractor contractor = createContractor();
        contractor.setNip("123456");
        Contractor contractor2 = createContractor();
        contractor2.setNip("123456");
        //when
        contractorRepository.persist(contractor);
        contractorRepository.persist(contractor2);
        //then throw Exception
    }
}
