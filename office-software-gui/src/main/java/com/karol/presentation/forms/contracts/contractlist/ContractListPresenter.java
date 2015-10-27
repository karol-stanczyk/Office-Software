package com.karol.presentation.forms.contracts.contractlist;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.ContractRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.utils.Bundles;
import com.karol.utils.functions.VoidFunction;
import com.karol.utils.notifications.NotificationsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContractListPresenter implements Initializable, Cleanable {

    @FXML private TableView<ContractListTableRow> contractListTable;
    @FXML private Label contractorName;
    @FXML private Label contractorLastName;
    @FXML private Label contractorAddress;

    private Contractor contractor;

    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;
    @Inject private NotificationsService notificationsService;

    @Inject private RepositoryProducer repositoryProducer;
    private ContractRepository contractRepository;

    private ResourceBundle bundle;

    @Override
    public void cleanForm() {
        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
        initializeRepositories();
    }

    @FXML
    public void newContract() {
        layoutService.showView(ViewsCache.contractView().getView(contractor));
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    @FXML
    public void deleteContract() {
        NotificationsService.showConfirmation(bundle.getString("confirmation.contract.delete"), () ->
                        actionWithContractSelected(() -> {
                            contractListTable.getSelectionModel().getSelectedItems().stream()
                                    .forEach(row -> contractRepository.delete(row.getContract(), contractor));
                            refreshTable();
                            notificationsService.showInformation(bundle.getString("contract.delete.success"));
                        })
        );
    }

    public void refreshTable() {
        contractListTable.setItems(FXCollections.observableList(getContractListTableRows()));
    }

    private List<ContractListTableRow> getContractListTableRows() {
        List<Contract> contractList = contractor.getContractList();
        return IntStream.range(0, contractList.size())
                .mapToObj(index -> new ContractListTableRow(contractList.get(index), index))
                .collect(Collectors.toList());
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
        this.contractorName.setText(contractor.getName());
        this.contractorLastName.setText(contractor.getLastName());
        this.contractorAddress.setText(contractor.getAddress());
    }

    public void actionWithContractSelected(VoidFunction function) {
        if (isRowSelected()) {
            function.run();
        } else {
            notificationsService.showError(Bundles.get("no.rows.selected.exception"));
        }
    }

    private boolean isRowSelected() {
        return !contractListTable.getSelectionModel().getSelectedItems().isEmpty();
    }

    private void initializeRepositories() {
        contractRepository = repositoryProducer.getContractRepository();
    }
}
