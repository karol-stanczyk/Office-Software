package com.karol.presentation.forms.invoices.invoicelist;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.ListPresenter;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.GoBackNavigator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InvoiceListPresenter extends ListPresenter implements Initializable, Cleanable {

    @FXML private TableView<InvoiceListTableRow> invoiceTable;
    @FXML private TableView<TransferListTableRow> paymentTable;
    @FXML private Label contractNumber;

    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;

    private Contract contract;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invoiceChangeListener();
        clearSelectionListener(invoiceTable, paymentTable);
        clearSelectionListener(paymentTable, invoiceTable);
    }

    @Override
    public void cleanForm() {
        refreshTable();
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    public void refreshTable() {
        invoiceTable.setItems(FXCollections.observableList(getInvoiceTable()));
    }

    public void setContract(Contract contract) {
        contractNumber.setText(contract.getNumber());
        this.contract = contract;
    }

    @SuppressWarnings("unchecked")
    private void clearSelectionListener(TableView tableView, TableView clearTable) {
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                clearTable.getSelectionModel().clearSelection();
            }
        });
    }

    private void invoiceChangeListener() {
        invoiceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Invoice invoice = newValue.getInvoice();
                paymentTable.setItems(FXCollections.observableList(getTransfersTable(invoice)));
            }
        });
    }

    private List<TransferListTableRow> getTransfersTable(Invoice invoice) {
        List<Transfer> transferList = invoice.getTransferList();
        return IntStream.range(0, transferList.size())
                .mapToObj(index -> new TransferListTableRow(transferList.get(index), index))
                .collect(Collectors.toList());
    }

    private List<InvoiceListTableRow> getInvoiceTable() {
        List<Invoice> invoiceList = contract.getInvoiceList();
        return IntStream.range(0, invoiceList.size())
                .mapToObj(index -> new InvoiceListTableRow(invoiceList.get(index), index))
                .collect(Collectors.toList());
    }
}
