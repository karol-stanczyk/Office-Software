package com.karol.presentation.forms;

import com.karol.utils.validation.CustomValidationDecoration;
import javafx.fxml.Initializable;
import org.controlsfx.validation.ValidationSupport;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Validator implements Initializable {

    protected ValidationSupport validation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validation = new ValidationSupport();
        validation.setValidationDecorator(new CustomValidationDecoration());
        registerValidators();
    }

    protected abstract void registerValidators();
    protected abstract boolean validate();
}
