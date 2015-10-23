package com.karol.presentation.layout.control.cache.views;

import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListView;
import javafx.scene.Parent;

public class ContractorListViewCache {

    private ContractorListPresenter contractorListPresenter;
    private Parent contractorListView;

    public ContractorListViewCache() {
        initContractorListView();
    }

    public Parent getView(boolean cleanForm) {
        if (cleanForm) {
            contractorListPresenter.cleanForm();
        }
        return contractorListView;
    }

    private void initContractorListView() {
        ContractorListView contractorListFxmlView = new ContractorListView();
        contractorListPresenter = (ContractorListPresenter) contractorListFxmlView.getPresenter();
        contractorListView = contractorListFxmlView.getView();
    }
}
