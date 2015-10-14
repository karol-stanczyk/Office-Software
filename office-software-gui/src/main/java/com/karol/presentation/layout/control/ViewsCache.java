package com.karol.presentation.layout.control;


import com.karol.presentation.forms.contractor.newcontractor.NewContractorPresenter;
import com.karol.presentation.forms.contractor.newcontractor.NewContractorView;
import javafx.scene.Parent;

public class ViewsCache {

    private static NewContractorPresenter newContractorPresenter;
    private static Parent newContractorView;

    public static void init() {
        initNewContractorView();
    }

    private static void initNewContractorView() {
        NewContractorView contractorView = new NewContractorView();
        newContractorPresenter = (NewContractorPresenter) contractorView.getPresenter();
        newContractorView = contractorView.getView();
    }

    public static Parent getNewContractorView() {
        newContractorPresenter.cleanForm();
        return newContractorView;
    }
}
