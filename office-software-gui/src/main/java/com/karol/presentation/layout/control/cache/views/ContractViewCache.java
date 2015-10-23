package com.karol.presentation.layout.control.cache.views;

import com.karol.model.Contractor;
import com.karol.presentation.forms.FormMode;
import com.karol.presentation.forms.contract.contract.ContractPresenter;
import com.karol.presentation.forms.contract.contract.ContractView;
import javafx.scene.Parent;

public class ContractViewCache {

    private ContractPresenter contractPresenter;
    private Parent newContractView;

    public ContractViewCache() {
        initNewContractView();
    }

    public Parent getView(Contractor contractor) {
        contractPresenter.setFormMode(FormMode.NEW);
        contractPresenter.cleanForm();
        contractPresenter.setContractor(contractor);
        return newContractView;
    }

    private void initNewContractView() {
        ContractView contractFxmlView = new ContractView();
        contractPresenter = (ContractPresenter) contractFxmlView.getPresenter();
        newContractView = contractFxmlView.getView();
    }
}
