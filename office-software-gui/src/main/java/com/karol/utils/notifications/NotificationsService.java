package com.karol.utils.notifications;

import com.karol.presentation.layout.control.LayoutService;
import com.karol.utils.Bundles;
import com.karol.utils.functions.VoidFunction;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.tools.Utils;

import javax.inject.Inject;

public class NotificationsService {

    public static Stage primaryStage;

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
        showConfirmation(null, message, function);
    }

    public static void showConfirmation(String title, String message, VoidFunction function) {
        Notifications.create()
                .text(message)
                .owner(primaryStage)
                .position(Pos.CENTER)
                .title(title)
                .action(new Action(Bundles.get("button.yes"), actionEvent -> {
                    function.run();
                    Utils.getWindow(actionEvent.getSource()).hide();
                }))
                .showConfirm();
    }

}
