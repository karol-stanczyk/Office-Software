package com.karol.presentation.layout.topmenu;

import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.layout.control.ViewsCache;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class TopMenuPresenter implements Initializable {

    @Inject
    private LayoutService layoutService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void newContractor() {
        layoutService.showView(ViewsCache.getNewContractorView());
    }

    @FXML
    public void contractorList() {
        layoutService.showView(ViewsCache.getContractorListView(true));
    }

    @FXML
    public void exit() {
        layoutService.closeApplication();
    }
}
