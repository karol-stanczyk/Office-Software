package com.karol.presentation.cache;

import com.karol.presentation.cache.views.*;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;
import com.karol.presentation.forms.invoices.invoicelist.InvoiceListPresenter;

import java.util.HashMap;
import java.util.Map;

public class ViewsCache {

    private static Map<Class, ViewCache> viewsCached;

    public static void init() {
        viewsCached = new HashMap<>();
        viewsCached.put(ContractorPresenter.class, new ContractorViewCache());
        viewsCached.put(ContractorListPresenter.class, new ContractorListViewCache());
        viewsCached.put(ContractPresenter.class, new ContractViewCache());
        viewsCached.put(ContractListPresenter.class, new ContractListViewCache());
        viewsCached.put(InvoiceListPresenter.class, new InvoiceListViewCache());
    }

    public static ViewCache getView(Class presenter) {
        return viewsCached.get(presenter);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getViewCache(final T clazz) {
        return (T) viewsCached.values().stream().filter(c -> c.presenter().getClass().equals(clazz.getClass())).findFirst().get();
    }
}
