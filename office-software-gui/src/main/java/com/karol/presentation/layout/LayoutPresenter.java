package com.karol.presentation.layout;

import com.karol.presentation.layout.topmenu.TopMenuView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LayoutPresenter implements Initializable {

    @FXML
    private BorderPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        content.setTop(new TopMenuView().getView());
    }
}
