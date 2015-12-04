package com.karol.presentation.cache.views;

import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.presentation.forms.invoices.payment.TransferPresenter;
import com.karol.presentation.forms.invoices.payment.TransferView;
import com.karol.presentation.navigation.Action;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class TransferViewCache implements ViewCache {

    private TransferPresenter transferPresenter;
    private Parent transferView;

    public TransferViewCache() {
        initTransferView();
    }

    private void initTransferView() {
        TransferView transferFXMLView = new TransferView();
        transferView = transferFXMLView.getView();
        transferPresenter = (TransferPresenter) transferFXMLView.getPresenter();
    }

    @Override
    public Parent getView() {
        transferPresenter.cleanForm();
        transferPresenter.setAction(Action.NEW);
        return transferView;
    }

    public Parent getView(Invoice invoice) {
        transferPresenter.setAction(Action.NEW);
        transferPresenter.setInvoice(invoice);
        transferPresenter.cleanForm();
        return transferView;
    }

    public Parent getView(Invoice invoice, Transfer transfer) {
        transferPresenter.setAction(Action.EDIT);
        transferPresenter.setInvoice(invoice, transfer);
        return transferView;
    }

    @Override
    public void refresh() {
        transferPresenter.cleanForm();
    }

    @Override
    public Initializable presenter() {
        return transferPresenter;
    }
}
