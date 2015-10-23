package com.karol.presentation.cache.views;

import com.karol.model.Contractor;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListView;
import javafx.scene.Parent;

public class ContractListViewCache {

    private ContractListPresenter contractListPresenter;
    private Parent contractListView;

    public ContractListViewCache() {
        initContractListView();
    }

    public Parent getView(Contractor contractor) {
        contractListPresenter.setContractor(contractor);
        return contractListView;
    }

    private void initContractListView() {
        ContractListView contractListFxmlView = new ContractListView();
        contractListPresenter = (ContractListPresenter) contractListFxmlView.getPresenter();
        contractListView = contractListFxmlView.getView();
    }

    public ContractListPresenter presenter() {
        return contractListPresenter;
    }
}
