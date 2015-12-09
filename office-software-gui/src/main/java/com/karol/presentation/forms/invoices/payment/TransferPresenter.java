package com.karol.presentation.forms.invoices.payment;

import com.karol.model.Invoice;
import com.karol.model.Transfer;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormModeRunner;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.TransferRepository;
import com.karol.repository.managment.RepositoryProducer;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;
import com.karol.utils.NumberFormatter;
import com.karol.utils.notifications.NotificationsService;
import com.karol.utils.validation.Validators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static com.karol.utils.validation.Validators.cashValidator;
import static com.karol.utils.validation.Validators.dateValidator;

public class TransferPresenter extends Validator implements Initializable, Cleanable {

    @FXML private Label formHeaderText;
    @FXML private TextField transferNetValue;
    @FXML private TextField transferGrossValue;
    @FXML private TextField transferVat;
    @FXML private DatePicker transferPaymentDate;
    @FXML private Label invoiceNumber;

    @Inject private NotificationsService notificationsService;
    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;
    @Inject private RepositoryProducer repositoryProducer;
    private TransferRepository transferRepository;

    private Invoice invoice;
    private Transfer transfer;
    private ResourceBundle bundle;
    private Property<Action> action;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        this.bundle = resourceBundle;
        this.action = new SimpleObjectProperty<>();
        this.transferRepository = repositoryProducer.getTransferRepository();
    }

    @Override
    public void cleanForm() {
        transferNetValue.setText("");
        transferGrossValue.setText("");
        transferVat.setText("");
        transferPaymentDate.setValue(null);
        clearValidation();
    }

    @Override
    protected void registerValidators() {
        validation.registerValidator(transferNetValue, cashValidator());
        validation.registerValidator(transferGrossValue, cashValidator());
        validation.registerValidator(transferVat, cashValidator());
        validation.registerValidator(transferPaymentDate, dateValidator());
    }

    @Override
    protected boolean validate() {
        return !validation.isInvalid();
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    private Transfer createTransfer() {
        Transfer transfer;
        if (action.getValue().equals(Action.NEW)) {
            transfer = new Transfer();
        } else {
            transfer = this.transfer;
        }
        transfer.setNetValue(NumberFormatter.fromStringCash(transferNetValue.getText()));
        transfer.setGrossValue(NumberFormatter.fromStringCash(transferGrossValue.getText()));
        transfer.setVAT(NumberFormatter.fromStringCash(transferVat.getText()));
        transfer.setTransferDate(DateFormatter.fromLocalDate(transferPaymentDate.getValue()));
        return transfer;
    }

    @FXML
    public void saveTransfer() {
        if (validate()) {
            Transfer transfer = createTransfer();
            try {
                FormModeRunner.actions()
                        .inNewMode(() -> transferRepository.persist(transfer, invoice))
                        .inEditMode(() -> transferRepository.update(transfer))
                        .run(action.getValue());
                notificationsService.showInformation(bundle.getString("notifications.transfer.saved.properly"));
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            notificationsService.showError(Bundles.get("form.contains.errors"));
            Validators.showValidationResult(validation);
        }
    }

    public void setInvoice(Invoice invoice) {
        invoiceNumber.setText(invoice.getNumber());
        this.invoice = invoice;
    }

    public void setInvoice(Invoice invoice, Transfer transfer) {
        setInvoice(invoice);
        this.transfer = transfer;
        transferNetValue.setText(NumberFormatter.toStringCash(transfer.getNetValue()));
        transferGrossValue.setText(NumberFormatter.toStringCash(transfer.getGrossValue()));
        transferVat.setText(NumberFormatter.toStringCash(transfer.getVAT()));
        transferPaymentDate.setValue(DateFormatter.toLocalDate(transfer.getTransferDate()));
    }

    public void setAction(Action action) {
        this.action.setValue(action);
        applyFormMode();
    }

    private void applyFormMode() {
        FormModeRunner.actions()
                .inNewMode(() -> formHeaderText.setText(bundle.getString("transfer.header.new")))
                .inEditMode(() -> formHeaderText.setText(bundle.getString("transfer.header.edit")))
                .run(action.getValue());
    }
}
