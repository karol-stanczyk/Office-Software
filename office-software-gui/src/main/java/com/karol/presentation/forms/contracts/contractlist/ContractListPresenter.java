package com.karol.presentation.forms.contracts.contractlist;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.cache.views.ContractViewCache;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.ListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.ContractRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.utils.notifications.NotificationsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContractListPresenter extends ListPresenter implements Initializable, Cleanable {

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
        initializeOnRowDoubleClickListener();
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
                        actionWithRowSelected(contractListTable, () -> {
                            contractListTable.getSelectionModel().getSelectedItems().stream()
                                    .forEach(row -> contractRepository.delete(row.getContract(), contractor));
                            refreshTable();
                            notificationsService.showInformation(bundle.getString("contract.delete.success"));
                        })
        );
    }

    @FXML
    public void editContract() {
        actionWithRowSelected(contractListTable, () -> {
            Contract contract = contractListTable.getSelectionModel().getSelectedItem().getContract();
            layoutService.showView(ViewsCache.getViewCache(ContractViewCache.class).getView(contractor, contract));
        });
    }

    @FXML
    public void invoiceList() {
        actionWithRowSelected(contractListTable, () -> {
            Contract selectedContract = contractListTable.getSelectionModel().getSelectedItem().getContract();
            layoutService.showView(ViewsCache.getInvoiceListView().getView(selectedContract));
        });
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

    private void initializeOnRowDoubleClickListener() {
        contractListTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    invoiceList();
                }
            }
        });
    }

    private void initializeRepositories() {
        contractRepository = repositoryProducer.getContractRepository();
    }
}
