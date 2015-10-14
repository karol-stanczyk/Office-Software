package com.karol.utils;

import com.karol.presentation.layout.control.LayoutService;
import javafx.geometry.Pos;
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

}
