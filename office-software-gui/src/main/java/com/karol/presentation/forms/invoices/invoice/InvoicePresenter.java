package com.karol.presentation.forms.invoices.invoice;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormModeRunner;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.InvoiceRepository;
import com.karol.repository.managment.RepositoryProducer;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;
import com.karol.utils.KeyBinding;
import com.karol.utils.NumberFormatter;
import com.karol.utils.notifications.NotificationsService;
import com.karol.utils.validation.Validators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.inject.Inject;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.karol.utils.validation.Validators.*;

public class InvoicePresenter extends Validator implements Initializable, Cleanable {

    @FXML private Parent root;
    @FXML private Label formHeaderText;
    @FXML private TextField invoiceNumber;
    @FXML private TextField invoiceNetValue;
    @FXML private TextField invoiceGrossValue;
    @FXML private TextField invoiceVat;
    @FXML private DatePicker invoiceCreationDate;
    @FXML private DatePicker invoicePaymentDate;

    @Inject private GoBackNavigator goBackNavigator;
    @Inject private LayoutService layoutService;
    @Inject private NotificationsService notificationsService;
    @Inject private RepositoryProducer repositoryProducer;
    private InvoiceRepository invoiceRepository;

    private ResourceBundle bundle;

    //Action variables
    private Invoice invoice;
    private Contract contract;
    private Property<Action> action;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        this.bundle = resourceBundle;
        this.action = new SimpleObjectProperty<>();
        this.invoiceRepository = repositoryProducer.getInvoiceRepository();
        KeyBinding.registerActionForAllChildren(KeyCode.ENTER, root, this::saveInvoice);
    }

    @Override
    public void cleanForm() {
        invoiceNumber.setText("");
        invoiceNetValue.setText("");
        invoiceGrossValue.setText("");
        invoiceVat.setText("");
        invoicePaymentDate.setValue(null);
        setInvoiceCreationDate();
        clearValidation();
    }

    @Override
    protected void registerValidators() {
        validation.registerValidator(invoiceNumber, notEmptyValidator());
        validation.registerValidator(invoiceNetValue, cashValidator());
        validation.registerValidator(invoiceGrossValue, cashValidator());
        validation.registerValidator(invoiceVat, cashValidator());
        validation.registerValidator(invoiceCreationDate, dateValidator());
        validation.registerValidator(invoicePaymentDate, dateValidator());
    }

    @Override
    protected boolean validate() {
        return !validation.isInvalid();
    }

    @FXML
    public void saveInvoice() {
        if (validate()) {
            Invoice invoice = createInvoice();
            try {
                FormModeRunner.actions()
                        .inNewMode(() -> invoiceRepository.persist(invoice, contract))
                        .inEditMode(() -> invoiceRepository.update(invoice))
                        .run(action.getValue());
                notificationsService.showInformation(bundle.getString("notifications.contractor.saved.properly"));
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            notificationsService.showError(Bundles.get("form.contains.errors"));
            Validators.showValidationResult(validation);
        }
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    public void setInvoiceCreationDate() {
        invoiceCreationDate.setValue(DateFormatter.toLocalDate(new Date()));
    }

    private Invoice createInvoice() {
        Invoice invoice;
        if (action.getValue().equals(Action.NEW)) {
            invoice = new Invoice();
        } else {
            invoice = this.invoice;
        }
        invoice.setNumber(invoiceNumber.getText());
        invoice.setNetValue(NumberFormatter.fromStringCash(invoiceNetValue.getText()));
        invoice.setGrossValue(NumberFormatter.fromStringCash(invoiceGrossValue.getText()));
        invoice.setVAT(NumberFormatter.fromStringCash(invoiceVat.getText()));
        invoice.setPaymentDate(DateFormatter.fromLocalDate(invoicePaymentDate.getValue()));
        invoice.setCreationDate(DateFormatter.fromLocalDate(invoiceCreationDate.getValue()));
        return invoice;
    }

    public void setInvoice(Invoice invoice, Contract contract) {
        this.invoice = invoice;
        this.contract = contract;
        invoiceNumber.setText(invoice.getNumber());
        invoiceNetValue.setText(NumberFormatter.toStringCash(invoice.getNetValue()));
        invoiceGrossValue.setText(NumberFormatter.toStringCash(invoice.getGrossValue()));
        invoiceVat.setText(NumberFormatter.toStringCash(invoice.getVAT()));
        invoicePaymentDate.setValue(DateFormatter.toLocalDate(invoice.getPaymentDate()));
        invoiceCreationDate.setValue(DateFormatter.toLocalDate(invoice.getCreationDate()));
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setAction(Action action) {
        this.action.setValue(action);
        applyFormMode();
    }

    private void applyFormMode() {
        FormModeRunner.actions()
                .inNewMode(() -> formHeaderText.setText(bundle.getString("invoice.header.new")))
                .inEditMode(() -> formHeaderText.setText(bundle.getString("invoice.header.edit")))
                .run(action.getValue());
    }
}
