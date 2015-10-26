package com.karol.presentation.cache.views;

import com.karol.model.Contractor;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractor.ContractorView;
import javafx.scene.Parent;

import java.util.Optional;

public class ContractorViewCache implements ViewCache {

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

    @Override
    public Parent getView() {
        return getView(Action.NEW, Optional.empty());
    }

    @Override
    public void refresh() {
    }

    public Parent getView(Contractor contractor) {
        return getView(Action.EDIT, Optional.of(contractor));
    }

    private Parent getView(Action mode, Optional<Contractor> contractor) {
        contractorPresenter.setAction(mode);
        contractorPresenter.cleanForm();
        if (contractor.isPresent()) {
            contractorPresenter.setEditContractor(contractor.get());
        }
        return contractorView;
    }

    public ContractorPresenter presenter(){
        return contractorPresenter;
    }
}
