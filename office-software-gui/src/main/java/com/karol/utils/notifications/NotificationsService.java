package com.karol.utils.notifications;

import com.karol.presentation.layout.control.LayoutService;
import com.karol.utils.Bundles;
import com.karol.utils.functions.VoidFunction;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.inject.Inject;

public class NotificationsService {

    @Inject
    private LayoutService layoutService;

    public void showInformation(String text) {
        Notifications.create()
                .owner(layoutService.getApplicationStage())
                .text(text)
                .hideAfter(Duration.seconds(2))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }

    public void showError(String text) {
        showError(text, 2);
    }

    public void showError(String text, int duration) {
        Notifications.create()
                .owner(layoutService.getApplicationStage())
                .text(text)
                .hideAfter(Duration.seconds(duration))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }

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
