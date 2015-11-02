package com.karol.presentation.cache;

import com.karol.presentation.cache.views.*;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;
import com.karol.presentation.forms.invoices.invoicelist.InvoiceListPresenter;
import javafx.fxml.Initializable;

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

    public static ViewCache getViewCache(final Initializable presenter) {
        return viewsCached.values().stream().filter(c -> c.presenter().getClass().equals(presenter.getClass())).findFirst().get();
    }

    public static ContractorViewCache contractorView() {
        return (ContractorViewCache) viewsCached.get(ContractorPresenter.class);
    }

    public static ContractorListViewCache contractorListView() {
        return (ContractorListViewCache) viewsCached.get(ContractorListPresenter.class);
    }

    public static ContractViewCache contractView() {
        return (ContractViewCache) viewsCached.get(ContractPresenter.class);
    }

    public static ContractListViewCache contractListView() {
        return (ContractListViewCache) viewsCached.get(ContractListPresenter.class);
    }

    public static InvoiceListViewCache invoiceListView() {
        return (InvoiceListViewCache) viewsCached.get(InvoiceListPresenter.class);
    }
}
