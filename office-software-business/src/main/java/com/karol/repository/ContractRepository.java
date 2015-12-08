package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.repository.access.EntityManager;
import com.karol.repository.access.LogEvent;
import com.karol.repository.access.Transactional;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Optional;

public class ContractRepository {
    private static final Logger log = Logger.getLogger(ContractRepository.class);

    @Inject private CrudRepository<Contract> contractRepository;
    @Inject private ContractorRepository contractorRepository;

    public ContractRepository() {
    }

    public ContractRepository(EntityManager entityManager) {
        contractorRepository = new ContractorRepository(entityManager);
        contractRepository = new CrudRepository<>(entityManager);
    }

    public Optional<Contract> findById(long id) {
        return Optional.ofNullable(contractRepository.find(Contract.class, id));
    }

    @LogEvent
    @Transactional
    public Contract persist(Contract contract, Contractor contractor) {
        contract.setContractor(contractor);
        contract = contractRepository.persist(contract);
        contractor.getContractList().add(contract);
        return contract;
    }

    @LogEvent
    @Transactional
    public void delete(Contract contract, Contractor contractor) {
        contractRepository.delete(contract);
        contractor.getContractList().remove(contract);
    }

    @LogEvent
    @Transactional
    public Contract update(Contract contract) {
        return contractRepository.update(contract);
    }
}
