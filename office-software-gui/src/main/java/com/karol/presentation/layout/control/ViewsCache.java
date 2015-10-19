package com.karol.presentation.layout.control;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.presentation.forms.FormMode;
import com.karol.presentation.forms.contract.contractlist.ContractListPresenter;
import com.karol.presentation.forms.contract.contractlist.ContractListView;
import com.karol.presentation.forms.contractor.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contractor.contractorlist.ContractorListView;
import com.karol.presentation.forms.contractor.newcontractor.NewContractorPresenter;
import com.karol.presentation.forms.contractor.newcontractor.NewContractorView;
import javafx.scene.Parent;

import java.util.Optional;

public class ViewsCache {

    private static NewContractorPresenter newContractorPresenter;
    private static Parent newContractorView;
    private static ContractorListPresenter contractorListPresenter;
    private static Parent contractorListView;
    private static ContractListPresenter contractListPresenter;
    private static Parent contractListView;

    public static void init() {
        initNewContractorView();
        initContractorListView();
        initContractListView();
    }

    private static void initNewContractorView() {
        NewContractorView contractorFxmlView = new NewContractorView();
        newContractorPresenter = (NewContractorPresenter) contractorFxmlView.getPresenter();
        newContractorView = contractorFxmlView.getView();
    }

    private static void initContractorListView() {
        ContractorListView contractorListFxmlView = new ContractorListView();
        contractorListPresenter = (ContractorListPresenter) contractorListFxmlView.getPresenter();
        contractorListView = contractorListFxmlView.getView();
    }

    private static void initContractListView() {
        ContractListView contractListFxmlView = new ContractListView();
        contractListPresenter = (ContractListPresenter) contractListFxmlView.getPresenter();
        contractListView = contractListFxmlView.getView();
    }

    public static Parent getNewContractorView() {
        return getNewContractorView(FormMode.NEW, Optional.empty());
    }

    public static Parent getNewContractorView(Contractor contractor) {
        return getNewContractorView(FormMode.EDIT, Optional.of(contractor));
    }

    private static Parent getNewContractorView(FormMode mode, Optional<Contractor> contractor) {
        newContractorPresenter.setFormMode(mode);
        newContractorPresenter.cleanForm();
        if (contractor.isPresent()) {
            newContractorPresenter.setEditContractor(contractor.get());
        }
        return newContractorView;
    }

    public static Parent getContractorListView(boolean cleanForm) {
        if (cleanForm) {
            contractorListPresenter.cleanForm();
        }
        return contractorListView;
    }

    public static Parent getContractListView(Contractor contractor) {
        contractListPresenter.setContractor(contractor);
        return contractListView;
    }
}
