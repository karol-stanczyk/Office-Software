package com.karol.presentation.cache.views;

import com.karol.model.Contractor;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contract.ContractView;
import com.karol.presentation.navigation.Action;
import javafx.scene.Parent;

public class ContractViewCache implements ViewCache {

    private ContractPresenter contractPresenter;
    private Parent newContractView;

    public ContractViewCache() {
        initNewContractView();
    }

    @Override
    public Parent getView() {
        return newContractView;
    }

    @Override
    public void refresh() {
    }

    public Parent getView(Contractor contractor) {
        contractPresenter.setFormMode(Action.NEW);
        contractPresenter.cleanForm();
        contractPresenter.setContractor(contractor);
        return newContractView;
    }

    private void initNewContractView() {
        ContractView contractFxmlView = new ContractView();
        contractPresenter = (ContractPresenter) contractFxmlView.getPresenter();
        newContractView = contractFxmlView.getView();
    }

    public ContractPresenter presenter() {
        return contractPresenter;
    }
}
