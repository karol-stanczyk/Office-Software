package com.karol.presentation.cache.views;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contract.ContractView;
import com.karol.presentation.navigation.Action;
import javafx.scene.Parent;

public class ContractViewCache implements ViewCache {

    private ContractPresenter contractPresenter;
    private Parent contractView;

    public ContractViewCache() {
        initNewContractView();
    }

    @Override
    public Parent getView() {
        return contractView;
    }

    @Override
    public void refresh() {
    }

    public Parent getView(Contractor contractor) {
        contractPresenter.setFormMode(Action.NEW);
        contractPresenter.cleanForm();
        contractPresenter.setContractor(contractor);
        return contractView;
    }

    public Parent getView(Contractor contractor, Contract contract) {
        contractPresenter.setFormMode(Action.EDIT);
        contractPresenter.cleanForm();
        contractPresenter.setContractor(contractor);
        contractPresenter.setContract(contract);
        return contractView;
    }

    private void initNewContractView() {
        ContractView contractFxmlView = new ContractView();
        contractPresenter = (ContractPresenter) contractFxmlView.getPresenter();
        contractView = contractFxmlView.getView();
    }

    public ContractPresenter presenter() {
        return contractPresenter;
    }
}
