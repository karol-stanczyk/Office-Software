package com.karol.presentation.forms;

import com.karol.utils.Bundles;
import com.karol.utils.functions.VoidFunction;
import com.karol.utils.notifications.NotificationsService;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public abstract class ListPresenter {

    @Inject private NotificationsService notificationsService;

    protected void actionWithRowSelected(TableView tableView, VoidFunction function) {
        if (isRowSelected(tableView)) {
            function.run();
        } else {
            notificationsService.showError(Bundles.get("no.rows.selected.exception"));
        }
    }

    protected void actionWithRowSelected(TableView tableView, TableView tableView2, VoidFunction function) {
        if(isRowSelected(tableView) || isRowSelected(tableView2)) {
            function.run();
        } else {
            notificationsService.showError(Bundles.get("no.rows.selected.exception"));
        }
    }

    private boolean isRowSelected(TableView tableView) {
        return !tableView.getSelectionModel().getSelectedItems().isEmpty();
    }
}
