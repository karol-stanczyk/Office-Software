package com.karol.presentation.forms;

import javafx.fxml.Initializable;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Validator implements Initializable {

    protected ValidationSupport validation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validation = new ValidationSupport();
        validation.setValidationDecorator(new CompoundValidationDecoration(
                new GraphicValidationDecoration(),
                new StyleClassValidationDecoration("error-field","warning-field")
        ));
        validation.setErrorDecorationEnabled(false);
        registerValidators();
    }

    protected abstract void registerValidators();
    protected abstract boolean validate();
}
