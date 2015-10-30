package com.karol.presentation.cache.views;

import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListView;
import javafx.scene.Parent;

public class ContractorListViewCache implements ViewCache {

    private ContractorListPresenter contractorListPresenter;
    private Parent contractorListView;

    public ContractorListViewCache() {
        initContractorListView();
    }

    @Override
    public Parent getView() {
        return contractorListView;
    }

    @Override
    public void refresh() {
        contractorListPresenter.refreshTable();
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

    @Override
    public ContractorListPresenter presenter() {
        return contractorListPresenter;
    }
}
