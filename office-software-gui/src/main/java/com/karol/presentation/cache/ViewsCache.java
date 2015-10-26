package com.karol.presentation.cache;

import com.karol.presentation.cache.views.*;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;

public class ViewsCache {

    private static ContractorViewCache contractorView;
    private static ContractorListViewCache contractorListView;

    private static ContractViewCache contractView;
    private static ContractListViewCache contractListView;

    public static void init() {
        contractorView = new ContractorViewCache();
        contractorListView = new ContractorListViewCache();
        contractView = new ContractViewCache();
        contractListView = new ContractListViewCache();
    }

    public static ContractorViewCache contractorView() {
        return contractorView;
    }

    public static ContractorListViewCache contractorListView() {
        return contractorListView;
    }

    public static ContractViewCache contractView() {
        return contractView;
    }

    public static ContractListViewCache contractListView() {
        return contractListView;
    }

    public static ViewCache getView(Class presenter) {
        if (presenter.equals(ContractorPresenter.class)) return contractorView;
        if (presenter.equals(ContractorListPresenter.class)) return contractorListView;
        if (presenter.equals(ContractPresenter.class)) return contractView;
        if (presenter.equals(ContractListPresenter.class)) return contractListView;
        return null;
    }
}
