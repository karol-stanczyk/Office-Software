package com.karol.repository;

import com.karol.model.Contractor;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ContractorRepository {

    @Inject
    private CrudRepository<Contractor> contractorRepository;

    public List<Contractor> findAll() {
        return contractorRepository.findWithNamedQuery(Contractor.FIND_ALL);
    }

    public Optional<Contractor> findById(long id) {
        return Optional.ofNullable(contractorRepository.find(Contractor.class, id));
    }

    public Optional<Contractor> findByPesel(String pesel) {
        return contractorRepository.findWithNamedQuery(
                Contractor.FIND_BY_PESEL, QueryParameter.with("pesel", pesel).parameters()
        ).stream().findFirst();
    }

    public Optional<Contractor> findByNip(String nip) {
        return contractorRepository.findWithNamedQuery(
                Contractor.FIND_BY_NIP, QueryParameter.with("nip", nip).parameters()
        ).stream().findFirst();
    }

    public List<Contractor> findByName(String name) {
        return contractorRepository.findWithNamedQuery(
                Contractor.FIND_BY_NAME, QueryParameter.with("name", name).parameters()
        );
    }

    public List<Contractor> findByLastName(String lastName) {
        return contractorRepository.findWithNamedQuery(
                Contractor.FIND_BY_LAST_NAME, QueryParameter.with("lastName", lastName).parameters()
        );
    }

    public Contractor persist(Contractor contractor) throws DatabaseException {
        checkPersistingInDatabase(contractor);
        return contractorRepository.persist(contractor);
    }

    private void checkPersistingInDatabase(Contractor contractor) throws DatabaseException {
        Optional<Contractor> contractorPesel = findByPesel(contractor.getPesel());
        if (!contractor.getPesel().equals("") && contractorPesel.isPresent() && !contractorPesel.get().getId().equals(contractor.getId())) {
            throw new DatabaseException("pesel.constraints.exception");
        }
        if (!contractor.getNip().equals("") && findByNip(contractor.getNip()).isPresent()) {
            throw new DatabaseException("nip.constraints.exception");
        }
    }


    public Contractor update(Contractor contractor) throws DatabaseException {
        checkPersistingInDatabase(contractor);
        return contractorRepository.update(contractor);
    }

    public void delete(Contractor contractor) {
        contractorRepository.delete(contractor);
    }
}
