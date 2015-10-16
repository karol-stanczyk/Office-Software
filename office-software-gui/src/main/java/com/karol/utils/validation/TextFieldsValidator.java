package com.karol.utils.validation;

import com.karol.utils.Bundles;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TextFieldsValidator extends FieldsValidator {

    private List<String> validationMessageList;
    private boolean validationState = true;

    public TextFieldsValidator() {
        this.validationMessageList = new ArrayList<>();
    }

    public FieldValidator forField(TextField field) {
        return new FieldValidator(field, this);
    }

    @Override
    public boolean valid() {
        return this.validationState;
    }

    @Override
    public String getErrorString() {
        StringBuilder error = new StringBuilder();
        validationMessageList.stream().forEach(message -> error.append(message).append("\n"));
        return error.toString();
    }

    private void join(boolean condition) {
        validationState = validationState && condition;
    }

    private void addValidationMessage(TextField field, String key) {
        String fieldName = field.getId();
        String errorDescription = Bundles.get(key, fieldName);
        validationMessageList.add(errorDescription);
    }

    public class FieldValidator {
        TextField field;
        TextFieldsValidator validator;

        public FieldValidator(TextField field, TextFieldsValidator validator) {
            this.field = field;
            this.validator = validator;
            removeStyles();
        }

        public FieldValidator notEmpty() {
            boolean validationResult = field.getText() != null && !field.getText().isEmpty();
            setFieldValidationStyle(validationResult, "empty.field.exception");
            join(validationResult);
            return this;
        }

        public FieldValidator onlyLetters() {
            boolean validationResult = field.getText().isEmpty() || field.getText().matches(LETTER_REGEX);
            setFieldValidationStyle(validationResult, "only.letters.field.exception");
            join(validationResult);
            return this;
        }

        public FieldValidator onlyNumbers() {
            boolean validationResult = field.getText().isEmpty() || field.getText().matches(NUMBER_REGEX);
            setFieldValidationStyle(validationResult, "only.numbers.field.exception");
            join(validationResult);
            return this;
        }

        public TextFieldsValidator validate() {
            return validator;
        }

        private void addStyle(TextField field, String style) {
            if (!field.getStyleClass().contains(style)) {
                field.getStyleClass().add(style);
            }
        }

        private void removeStyles() {
            field.getStyleClass().remove("error-text-field");
        }

        private void setFieldValidationStyle(boolean validationResult, String errorBundleKey) {
            if (!validationResult) {
                addValidationMessage(field, errorBundleKey);
                addStyle(field, FieldsValidator.ERROR_STYLE);
            }
        }
    }


}
