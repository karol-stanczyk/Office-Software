package com.karol.utils.validation;

import javafx.scene.control.Control;

import java.util.Arrays;

public abstract class FieldsValidator {
    String LETTER_REGEX = "[a-zA-z\\u0105\\u0119\\u0107\\u017c\\u017a\\u0144\\u0142\\u00f3\\u015b\\u0104\\u0106\\u0118\\u0141\\u0143\\u00d3\\u015a\\u0179\\u017b ]+";
    String NUMBER_REGEX = "[0-9]+";
    public static String ERROR_STYLE = "error-text-field";

    public abstract boolean valid();
    public abstract String getErrorString();

    public static void removeErrorStyleClasses(Control... control){
        Arrays.asList(control).stream().forEach(c -> c.getStyleClass().remove(ERROR_STYLE));
    }
}
