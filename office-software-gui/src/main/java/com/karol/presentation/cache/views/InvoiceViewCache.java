package com.karol.presentation.cache.views;

import com.karol.presentation.forms.invoices.invoice.InvoicePresenter;
import com.karol.presentation.forms.invoices.invoice.InvoiceView;
import javafx.scene.Parent;

public class InvoiceViewCache implements ViewCache {

    private Parent invoiceView;
    private InvoicePresenter invoicePresenter;

    public InvoiceViewCache() {
        InvoiceView invoiceFxmlView = new InvoiceView();
        this.invoiceView = invoiceFxmlView.getView();
        this.invoicePresenter = (InvoicePresenter) invoiceFxmlView.getPresenter();
    }

    @Override
    public Parent getView() {
        invoicePresenter.setInvoiceCreationDate();
        return invoiceView;
    }

    @Override
    public void refresh() {
        invoicePresenter.cleanForm();
    }

    @Override
    public InvoicePresenter presenter() {
        return invoicePresenter;
    }
}
