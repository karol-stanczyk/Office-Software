package com.karol.presentation.layout.control;


import com.karol.presentation.forms.contractor.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contractor.contractorlist.ContractorListView;
import com.karol.presentation.forms.contractor.newcontractor.NewContractorPresenter;
import com.karol.presentation.forms.contractor.newcontractor.NewContractorView;
import javafx.scene.Parent;

public class ViewsCache {

    private static NewContractorPresenter newContractorPresenter;
    private static Parent newContractorView;
    private static ContractorListPresenter contractorListPresenter;
    private static Parent contractorListView;

    public static void init() {
        initNewContractorView();
        initContractorListView();
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

    public static Parent getNewContractorView(boolean cleanForm) {
        if (cleanForm) {
            newContractorPresenter.cleanForm();
        }
        return newContractorView;
    }

    public static Parent getContractorListView(boolean cleanForm) {
        if (cleanForm) {
            contractorListPresenter.cleanForm();
        }
        return contractorListView;
    }
}
