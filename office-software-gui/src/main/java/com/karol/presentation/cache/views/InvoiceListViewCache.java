package com.karol.presentation.cache.views;

import com.karol.model.Contract;
import com.karol.presentation.forms.invoices.invoicelist.InvoiceListPresenter;
import com.karol.presentation.forms.invoices.invoicelist.InvoiceListView;
import javafx.scene.Parent;

public class InvoiceListViewCache implements ViewCache {

    private InvoiceListPresenter invoiceListPresenter;
    private Parent invoiceListView;

    public InvoiceListViewCache() {
        initContractorListView();
    }

    private void initContractorListView() {
        InvoiceListView invoiceFXMLView = new InvoiceListView();
        invoiceListPresenter = (InvoiceListPresenter) invoiceFXMLView.getPresenter();
        invoiceListView = invoiceFXMLView.getView();
    }

    @Override
    public Parent getView() {
        return invoiceListView;
    }

    public Parent getView(Contract contract) {
        invoiceListPresenter.setContract(contract);
        return invoiceListView;
    }

    @Override
    public void refresh() {
        invoiceListPresenter.refreshTable();
    }
}
