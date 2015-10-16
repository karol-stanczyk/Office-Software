package com.karol.presentation.layout.control;

import com.karol.presentation.layout.LayoutPresenter;
import com.karol.utils.ActionUtils;
import com.karol.utils.Bundles;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class LayoutService {

    private Stage applicationStage;
    private LayoutPresenter layoutPresenter;

    public void closeApplication() {
        ActionUtils.showConfirmation(Bundles.get("application.close.contentText"), applicationStage::close);
    }

    public void showView(Parent parent) {
        layoutPresenter.setContent(parent);
    }

    public void setLayoutPresenter(LayoutPresenter layoutPresenter) {
        this.layoutPresenter = layoutPresenter;
    }

    public Stage getApplicationStage() {
        return applicationStage;
    }

    public void setApplicationStage(Stage applicationStage) {
        this.applicationStage = applicationStage;
    }
}
