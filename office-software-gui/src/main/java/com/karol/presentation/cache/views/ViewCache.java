package com.karol.presentation.cache.views;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

public interface ViewCache {

    Parent getView();
    void refresh();
    Initializable presenter();
}
