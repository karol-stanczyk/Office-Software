package com.karol.presentation.layout.control.cache.views;

import com.karol.model.Contractor;
import com.karol.presentation.forms.FormMode;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractor.ContractorView;
import javafx.scene.Parent;

import java.util.Optional;

public class ContractorViewCache {

    private ContractorPresenter contractorPresenter;
    private Parent contractorView;

    public ContractorViewCache() {
        initContractorView();
    }

    private void initContractorView() {
        ContractorView contractorFxmlView = new ContractorView();
        contractorPresenter = (ContractorPresenter) contractorFxmlView.getPresenter();
        contractorView = contractorFxmlView.getView();
    }

    public Parent getView() {
        return getView(FormMode.NEW, Optional.empty());
    }

    public Parent getView(Contractor contractor) {
        return getView(FormMode.EDIT, Optional.of(contractor));
    }

    private Parent getView(FormMode mode, Optional<Contractor> contractor) {
        contractorPresenter.setFormMode(mode);
        contractorPresenter.cleanForm();
        if (contractor.isPresent()) {
            contractorPresenter.setEditContractor(contractor.get());
        }
        return contractorView;
    }
}
