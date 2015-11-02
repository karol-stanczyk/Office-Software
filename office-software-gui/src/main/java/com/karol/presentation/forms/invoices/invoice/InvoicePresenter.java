package com.karol.presentation.forms.invoices.invoice;

import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.utils.DateFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.karol.utils.validation.Validators.*;

public class InvoicePresenter extends Validator implements Initializable, Cleanable {

    @FXML private TextField invoiceNumber;
    @FXML private TextField invoiceNetValue;
    @FXML private TextField invoiceGrossValue;
    @FXML private TextField invoiceVat;
    @FXML private DatePicker invoiceCreationDate;
    @FXML private DatePicker invoicePaymentDate;

    @Inject private GoBackNavigator goBackNavigator;
    @Inject private LayoutService layoutService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    @Override
    public void cleanForm() {

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
        return false;
    }

    @FXML
    public void saveInvoice() {

    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    public void setCurrentDate() {
        invoiceCreationDate.setValue(DateFormatter.toLocalDate(new Date()));
    }
}
