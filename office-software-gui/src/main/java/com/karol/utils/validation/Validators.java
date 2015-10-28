package com.karol.utils.validation;

import com.karol.utils.Bundles;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.ValidationDecoration;

public class Validators {

    static String LETTER_REGEX = "[a-zA-z\\u0105\\u0119\\u0107\\u017c\\u017a\\u0144\\u0142\\u00f3\\u015b\\u0104\\u0106\\u0118\\u0141\\u0143\\u00d3\\u015a\\u0179\\u017b ]+";
    static String NUMBER_REGEX = "[0-9]+";

    private static Validator notEmptyValidator;
    private static Validator onlyLettersValidator;
    private static Validator onlyNumbersValidator;


    public static void createValidators() {
        notEmptyValidator = Validator.createEmptyValidator(Bundles.get("field.not.empty"));
        onlyLettersValidator = Validator.createRegexValidator(
                Bundles.get("field.can.contains.only.letters"),
                LETTER_REGEX, Severity.ERROR
        );
        onlyNumbersValidator = Validator.createRegexValidator(
                Bundles.get("field.can.contains.only.numbers"),
                NUMBER_REGEX, Severity.ERROR
        );
    }

    public static Validator notEmptyValidator() {
        return notEmptyValidator;
    }

    public static Validator onlyLettersValidator() {
        return onlyLettersValidator;
    }

    public static Validator onlyNumbersValidator() {
        return onlyNumbersValidator;
    }

    public static Validator combine(Validator... validators) {
        return Validator.combine(validators);
    }

    public static void showValidationResult(ValidationSupport support) {
        ValidationDecoration decoration = support.getValidationDecorator();
        support.getValidationResult().getErrors().stream().forEach(decoration::applyValidationDecoration);
        support.getValidationResult().getWarnings().stream().forEach(decoration::applyValidationDecoration);
    }
}
