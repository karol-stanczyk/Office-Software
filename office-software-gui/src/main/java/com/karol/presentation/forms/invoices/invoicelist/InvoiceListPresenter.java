package com.karol.presentation.forms.invoices.invoicelist;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.ListPresenter;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.InvoiceRepository;
import com.karol.repository.TransferRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.utils.notifications.NotificationsService;
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

    @Inject private NotificationsService notificationsService;
    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;
    @Inject private RepositoryProducer repositoryProducer;
    private InvoiceRepository invoiceRepository;
    private TransferRepository transferRepository;

    private ResourceBundle bundle;

    private Contract contract;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        invoiceChangeListener();
        clearSelectionListener(invoiceTable, paymentTable);
        clearSelectionListener(paymentTable, invoiceTable);
        invoiceRepository = repositoryProducer.getInvoiceRepository();
        transferRepository = repositoryProducer.getTransferRepository();
    }

    @Override
    public void cleanForm() {
        refreshTable();
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    @FXML
    public void newInvoice() {
        layoutService.showView(ViewsCache.invoiceView().getView(contract));
    }

    @FXML
    public void newPayment() {
        actionWithRowSelected(invoiceTable, () -> {
            layoutService.showView(ViewsCache.transferView().getView(invoiceTable.getSelectionModel().getSelectedItem().getInvoice()));
        });
    }

    @FXML
    public void edit() {
        actionWithRowSelected(invoiceTable, paymentTable, () -> {
            InvoiceListTableRow selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
            TransferListTableRow selectedTransfer = paymentTable.getSelectionModel().getSelectedItem();
            if (selectedInvoice != null) {
                layoutService.showView(ViewsCache.invoiceView().getView(contract, selectedInvoice.getInvoice()));
            } else {
                layoutService.showView(ViewsCache.transferView().getView(selectedTransfer.getTransfer().getInvoice(), selectedTransfer.getTransfer()));
            }
        });
    }

    @FXML
    public void delete() {
        actionWithRowSelected(invoiceTable, paymentTable, () -> {
            InvoiceListTableRow selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
            if (selectedInvoice != null) {
                NotificationsService.showConfirmation(bundle.getString("confirmation.invoice.delete"), () -> {
                    invoiceTable.getSelectionModel().getSelectedItems().stream()
                            .forEach(item -> invoiceRepository.delete(item.getInvoice(), contract));
                    deleteSuccessful();
                });
            } else {
                NotificationsService.showConfirmation(bundle.getString("confirmation.transfer.delete"), () -> {
                    paymentTable.getSelectionModel().getSelectedItems().stream()
                            .forEach(item -> transferRepository.delete(item.getTransfer().getInvoice(), item.getTransfer()));
                    deleteSuccessful();
                });
            }
        });
    }

    private void deleteSuccessful() {
        refreshTable();
        notificationsService.showInformation(bundle.getString("invoice.delete.success"));
    }

    public void refreshTable() {
        invoiceTable.setItems(FXCollections.observableList(getInvoiceTable()));
        paymentTable.getItems().clear();
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
