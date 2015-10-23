package com.karol.presentation.cache;

import com.karol.presentation.cache.views.ContractListViewCache;
import com.karol.presentation.cache.views.ContractViewCache;
import com.karol.presentation.cache.views.ContractorListViewCache;
import com.karol.presentation.cache.views.ContractorViewCache;

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
