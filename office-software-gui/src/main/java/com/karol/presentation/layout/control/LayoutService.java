package com.karol.presentation.layout.control;

import com.karol.utils.Bundles;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class LayoutService {

    private Stage applicationStage;

    public Stage getApplicationStage() {
        return applicationStage;
    }

    public void setApplicationStage(Stage applicationStage) {
        this.applicationStage = applicationStage;
    }

    public void closeApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(Bundles.get("application.close.title"));
        alert.setHeaderText(null);
        alert.setContentText(Bundles.get("application.close.contentText"));
        alert.showAndWait()
                .filter(result -> result == ButtonType.YES)
                .ifPresent(result -> applicationStage.close());
    }
}
