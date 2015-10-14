package com.karol.presentation.layout;

import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.layout.control.ViewsCache;
import com.karol.presentation.layout.topmenu.TopMenuView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutPresenter implements Initializable {

    @FXML
    private BorderPane content;

    @Inject
    private LayoutService layoutService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        content.setTop(new TopMenuView().getView());
        layoutService.setLayoutPresenter(this);
    }

    public void setContent(Parent parent) {
        content.setCenter(parent);
    }
}
