package com.karol.presentation.forms.contract.newcontract;

import com.karol.model.Contractor;
import com.karol.model.Period;
import com.karol.presentation.forms.*;
import com.karol.utils.DateFormatter;
import com.karol.utils.validation.FieldsValidator;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewContractPresenter implements Initializable, Cleanable, Validator {

    @FXML
    private Label formHeaderText;
    @FXML
    private TextField contractNumber;
    @FXML
    private ComboBox<AbstractComboBoxEnum<Period>> contractPeriod;
    @FXML
    private DatePicker validityPeriod;
    @FXML
    private DatePicker paymentDate;

    private ResourceBundle bundle;
    private Contractor contractor;
    // FormMode variables
    private Property<FormMode> formMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        this.formMode = new SimpleObjectProperty<>();
        this.validityPeriod.setConverter(DateFormatter.createConverter());
        this.paymentDate.setConverter(DateFormatter.createConverter());
        initPeriodList();
    }

    private void initPeriodList() {
        List<AbstractComboBoxEnum<Period>> list = Arrays.asList(
                new AbstractComboBoxEnum<>(Period.MONTHLY),
                new AbstractComboBoxEnum<>(Period.QUARTERLY),
                new AbstractComboBoxEnum<>(Period.ANNUAL));
        contractPeriod.setItems(FXCollections.observableList(list));

    }

    @Override
    public void cleanForm() {
        contractNumber.setText("");
        contractPeriod.getSelectionModel().select(null);
        validityPeriod.setValue(null);
        paymentDate.setValue(null);
    }

    @Override
    public FieldsValidator validate() {
        return null;
    }

    public void setFormMode(FormMode formMode) {
        this.formMode.setValue(formMode);
        applyFormMode();
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    private void applyFormMode() {
        FormModeRunner.run(
                () -> formHeaderText.setText(bundle.getString("new.contract")),
                () -> formHeaderText.setText(bundle.getString("new.contract.edit")),
                formMode.getValue());
    }


}
