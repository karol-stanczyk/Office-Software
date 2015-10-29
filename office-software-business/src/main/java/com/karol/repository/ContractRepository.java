package com.karol.repository;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.repository.access.Transactional;
import com.karol.repository.utils.DatabaseException;

import javax.inject.Inject;

public class ContractRepository {

    @Inject private CrudRepository<Contract> contractRepository;
    @Inject private ContractorRepository contractorRepository;

    @Transactional
    public Contract persist(Contract contract, Contractor contractor) {
        contract = contractRepository.persist(contract);
        contractor.getContractList().add(contract);
        try {
            contractorRepository.update(contractor);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return contract;
    }

    @Transactional
    public void delete(Contract contract, Contractor contractor) {
        contractRepository.delete(contract);
        contractor.getContractList().remove(contract);
    }

    @Transactional
    public Contract update(Contract contract) {
        return contractRepository.update(contract);
    }
}
