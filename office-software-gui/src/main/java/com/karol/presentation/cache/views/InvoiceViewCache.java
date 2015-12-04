package com.karol.presentation.cache.views;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.presentation.forms.invoices.invoice.InvoicePresenter;
import com.karol.presentation.forms.invoices.invoice.InvoiceView;
import com.karol.presentation.navigation.Action;
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
        return invoiceView;
    }

    public Parent getView(Contract contract) {
        invoicePresenter.setInvoiceCreationDate();
        invoicePresenter.setAction(Action.NEW);
        invoicePresenter.setContract(contract);
        invoicePresenter.cleanForm();
        return invoiceView;
    }

    public Parent getView(Contract contract, Invoice invoice) {
        invoicePresenter.setInvoice(invoice, contract);
        invoicePresenter.setAction(Action.EDIT);
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
