package com.karol.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ActionUtils {

    public static void showConfirmation(String message, VoidFunction function) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(Bundles.get("confirmation.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait()
                .filter(result -> result == ButtonType.YES)
                .ifPresent(result -> function.run());
    }
}
