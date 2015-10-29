package com.karol.presentation.forms.contractors.contractorlist;

import com.karol.model.Contractor;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.ListPresenter;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.repository.ContractorRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.utils.notifications.NotificationsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContractorListPresenter extends ListPresenter implements Initializable, Cleanable {

    @FXML private TextField contractorListFilter;
    @FXML private TableView<ContractorTableRow> contractorsTable;

    @Inject private RepositoryProducer repositoryProducer;
    private ContractorRepository contractorRepository;

    @Inject private NotificationsService notificationsService;
    @Inject private LayoutService layoutService;

    private List<Contractor> contractorList;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        initializeFilter();
        initializeRepositories();
        contractorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initializeOnRowDoubleClickListener();
        refreshTable();
    }

    @Override
    public void cleanForm() {
        refreshTable();
        contractorListFilter.setText("");
    }

    @FXML
    public void refreshTable() {
        contractorList = contractorRepository.findAll();
        contractorsTable.setItems(FXCollections.observableList(contractorTableRows()));
    }

    @FXML
    public void deleteContractor() {
        NotificationsService.showConfirmation(bundle.getString("confirmation.contractors.delete"), () ->
                        actionWithRowSelected(contractorsTable, () -> {
                            contractorsTable.getSelectionModel().getSelectedItems().stream()
                                    .forEach(row -> contractorRepository.delete(row.getContractor()));
                            refreshTable();
                            notificationsService.showInformation(bundle.getString("contractor.delete.success"));
                        })
        );
    }

    @FXML
    public void editContractor() {
        actionWithRowSelected(contractorsTable, () -> {
            Contractor contractor = contractorsTable.getSelectionModel().getSelectedItem().getContractor();
            layoutService.showView(ViewsCache.contractorView().getView(contractor));
        });
    }

    @FXML
    public void showContracts() {
        actionWithRowSelected(contractorsTable, () -> {
            Contractor contractor = contractorsTable.getSelectionModel().getSelectedItem().getContractor();
            layoutService.showView(ViewsCache.contractListView().getView(contractor));
        });
    }

    private void initializeOnRowDoubleClickListener() {
        contractorsTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    showContracts();
                }
            }
        });
    }

    private List<ContractorTableRow> contractorTableRows() {
        return IntStream.range(0, contractorList.size())
                .mapToObj(index -> new ContractorTableRow(contractorList.get(index), index))
                .collect(Collectors.toList());
    }



    private void initializeFilter() {
        contractorListFilter.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                contractorsTable.setItems(
                        FXCollections.observableList(
                                contractorTableRows().stream()
                                        .filter(row -> {
                                            String r = row.toString().toUpperCase();
                                            String f = newValue.toUpperCase();
                                            return r.contains(f);
                                        })
                                        .collect(Collectors.toList()))
                );
            }
        });
    }

    private void initializeRepositories() {
        contractorRepository = repositoryProducer.getContractorRepository();
    }
}
