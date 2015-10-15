package com.karol.presentation.forms.contractor.contractorlist;

import com.karol.model.Contractor;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.Validator;
import com.karol.repository.ContractorRepository;
import com.karol.utils.Bundles;
import com.karol.utils.NotificationsService;
import com.karol.utils.validation.FieldsValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContractorListPresenter implements Initializable, Cleanable, Validator {

    @FXML private TextField contractorListFilter;
    @FXML private TableView<ContractorTableRow> contractorsTable;

    @Inject
    private ContractorRepository contractorRepository;
    @Inject
    private NotificationsService notificationsService;

    private List<Contractor> contractorList;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        initializeFilter();
        contractorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refreshTable();
    }

    private List<ContractorTableRow> contractorTableRows() {
        List<ContractorTableRow> result = new ArrayList<>();
        IntStream.range(0, contractorList.size())
                .forEach(i -> result.add(new ContractorTableRow(contractorList.get(i), i)));
        return result;
    }

    @FXML
    public void refreshTable() {
        contractorList = contractorRepository.findAll();
        contractorsTable.setItems(FXCollections.observableList(contractorTableRows()));
    }

    @FXML
    public void deleteContractor() {
        if (isRowSelected()) {
            contractorsTable.getSelectionModel().getSelectedItems().stream()
                    .forEach(row -> contractorRepository.delete(row.getContractor()));
            refreshTable();
            notificationsService.showInformation(bundle.getString("contractor.delete.success"));
        } else {
            notificationsService.showError(Bundles.get("no.rows.selected.exception"));
        }
    }

    @Override
    public void cleanForm() {
        refreshTable();
        contractorListFilter.setText("");
    }

    @Override
    public FieldsValidator validate() {
        return null;
    }

    private boolean isRowSelected() {
        return !contractorsTable.getSelectionModel().getSelectedItems().isEmpty();
    }

    private void initializeFilter() {
        contractorListFilter.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                contractorsTable.setItems(
                        FXCollections.observableList(
                                contractorTableRows().stream()
                                        .filter(row -> row.toString().contains(newValue))
                                        .collect(Collectors.toList()))
                );
            }
        });
    }
}
