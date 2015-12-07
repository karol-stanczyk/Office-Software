package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.repository.utils.DatabaseException;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ContractorRepositoryTest extends MockDatabase {

    private ContractorRepository contractorRepository = new ContractorRepository(entityManager);

    @Test
    public void shouldNotReturnAnyContractors() {
        Optional<Contractor> contractor = contractorRepository.findById(1);
        assertEquals(contractor.isPresent(), false);
    }

    @Test
    public void shouldAddContractor() {
        Contractor contractor = new Contractor("Karol", "Stańczyk", "Skorków 154");
        contractor = contractorRepository.persist(contractor);
        assertNotNull(contractor.getId());
    }

    @Test
    public void shouldFindContractorById() {
        Contractor contractor = new Contractor("Karol", "Stańczyk", "Skorków 154");
        long id = contractorRepository.persist(contractor).getId();
        Optional<Contractor> searchResult = contractorRepository.findById(id);
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByPesel() {
        Contractor contractor = new Contractor("", "", "");
        contractor.setPesel("123456789");
        contractorRepository.persist(contractor);
        Optional<Contractor> searchResult = contractorRepository.findByPesel("123456789");
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByNip() {
        Contractor contractor = new Contractor("", "", "");
        contractor.setNip("123456");
        contractorRepository.persist(contractor);
        Optional<Contractor> searchResult = contractorRepository.findByNip("123456");
        assertEquals(true, searchResult.isPresent());
    }

    @Test
    public void shouldFindContractorByName() {
        Contractor contractor = new Contractor("Karol", "", "");
        contractorRepository.persist(contractor);
        List<Contractor> searchResult = contractorRepository.findByName("Karol");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldFindContractorByLastName() {
        Contractor contractor = new Contractor("Karol", "Stanczyk", "");
        contractorRepository.persist(contractor);
        List<Contractor> searchResult = contractorRepository.findByLastName("Stanczyk");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldUpdateContractorData() {
        Contractor contractor = new Contractor("Karol", "Stanczyk", "Skorków");
        contractorRepository.persist(contractor);
        contractor.setName("Zenek");
        contractorRepository.update(contractor);
        List<Contractor> searchResult = contractorRepository.findByName("Zenek");
        assertEquals(true, searchResult.size() > 0);
    }

    @Test
    public void shouldDeleteContractor() {
        Contractor contractor = new Contractor("Karol", "Stanczyk", "Skorków");
        contractorRepository.persist(contractor);
        assertEquals(true, contractorRepository.findAll().size() > 0);
        contractorRepository.delete(contractor);
        assertEquals(true, contractorRepository.findAll().size() == 0);
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowExceptionCozOfSecondContractorWithSamePesel() {
        Contractor contractor = new Contractor("", "", "");
        contractor.setPesel("123456");
        Contractor contractor2 = new Contractor("", "", "");
        contractor2.setPesel("123456");
        contractorRepository.persist(contractor);
        contractorRepository.persist(contractor2);
    }

    @Test(expected = DatabaseException.class)
    public void shouldThrowExceptionCozOfSecondContractorWithSameNip() {
        Contractor contractor = new Contractor("", "", "");
        contractor.setNip("123456");
        Contractor contractor2 = new Contractor("", "", "");
        contractor2.setNip("123456");
        contractorRepository.persist(contractor);
        contractorRepository.persist(contractor2);
    }
}
