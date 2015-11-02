package com.karol.presentation.forms.invoices.invoice;

import com.karol.model.Contract;
import com.karol.model.Invoice;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormModeRunner;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;
import com.karol.utils.KeyBinding;
import com.karol.utils.NumberFormatter;
import com.karol.utils.notifications.NotificationsService;
import com.karol.utils.validation.Validators;
import javafx.beans.property.Property;
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

    private ResourceBundle bundle;

    //Action variables
    private Invoice invoice;
    private Contract contract;
    private Property<Action> action;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        this.bundle = resourceBundle;
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
    }

    @Override
    protected void registerValidators() {
        validation.registerValidator(invoiceNumber, notEmptyValidator());
        validation.registerValidator(invoiceNetValue, cashValidator());
        validation.registerValidator(invoiceGrossValue, cashValidator());
        validation.registerValidator(invoiceVat, onlyNumbersValidator());
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
                FormModeRunner.runWithException(
                        () -> {
                            //TODO(Karol S.) save invoice
                            cleanForm();
                        },
                        () -> System.out.println("TODO (Karol S.) update invoice"),
                        action.getValue());
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
        invoice.setNetValue(NumberFormatter.toDouble(invoiceNetValue.getText()));
        invoice.setGrossValue(NumberFormatter.toDouble(invoiceGrossValue.getText()));
        invoice.setVAT(NumberFormatter.toDouble(invoiceVat.getText()));
        invoice.setPaymentDate(DateFormatter.fromLocalDate(invoicePaymentDate.getValue()));
        invoice.setCreationDate(DateFormatter.fromLocalDate(invoiceCreationDate.getValue()));
        return invoice;
    }

    public void setInvoice(Invoice invoice, Contract contract) {
        this.invoice = invoice;
        this.contract = contract;
    }

    public void setAction(Action action) {
        this.action.setValue(action);
        applyFormMode();
    }

    private void applyFormMode() {
        FormModeRunner.run(
                () -> formHeaderText.setText(bundle.getString("new.contractor")),
                () -> formHeaderText.setText(bundle.getString("new.contractor.edit")),
                action.getValue());
    }
}
