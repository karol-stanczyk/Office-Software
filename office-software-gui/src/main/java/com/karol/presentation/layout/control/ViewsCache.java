package com.karol.presentation.layout.control;

import com.karol.presentation.layout.control.cache.views.ContractListViewCache;
import com.karol.presentation.layout.control.cache.views.ContractViewCache;
import com.karol.presentation.layout.control.cache.views.ContractorListViewCache;
import com.karol.presentation.layout.control.cache.views.ContractorViewCache;

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

}
