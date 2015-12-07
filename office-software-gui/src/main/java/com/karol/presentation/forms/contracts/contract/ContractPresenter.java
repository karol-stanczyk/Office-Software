package com.karol.presentation.forms.contracts.contract;

import com.karol.model.Contract;
import com.karol.model.Contractor;
import com.karol.model.Period;
import com.karol.presentation.forms.AbstractComboBoxEnum;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormModeRunner;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.ContractRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.DateFormatter;
import com.karol.utils.KeyBinding;
import com.karol.utils.notifications.NotificationsService;
import com.karol.utils.validation.Validators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.inject.Inject;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.karol.utils.validation.Validators.dateValidator;
import static com.karol.utils.validation.Validators.notEmptyValidator;

public class ContractPresenter extends Validator implements Initializable, Cleanable {

    @FXML private Label formHeaderText;
    @FXML private TextField contractNumber;
    @FXML private ComboBox<AbstractComboBoxEnum<Period>> contractPeriod;
    @FXML private DatePicker validityPeriod;
    @FXML private DatePicker paymentDate;
    @FXML private Parent root;

    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;
    @Inject private NotificationsService notificationsService;
    @Inject private RepositoryProducer repositoryProducer;
    private ContractRepository contractRepository;

    private ResourceBundle bundle;
    private Contractor contractor;
    // Action variables
    private Property<Action> formMode;
    private Contract contract;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        this.bundle = resourceBundle;
        initializeValues();
        initializeRepositories();
        initializePeriodList();
        KeyBinding.registerActionForAllChildren(KeyCode.ENTER, root, this::saveContract);
    }

    @Override
    protected void registerValidators() {
        validation.registerValidator(contractNumber, notEmptyValidator());
        validation.registerValidator(contractPeriod, notEmptyValidator());
        validation.registerValidator(validityPeriod, dateValidator());
        validation.registerValidator(paymentDate, notEmptyValidator());
    }

    @Override
    public void cleanForm() {
        contractNumber.setText("");
        contractPeriod.getSelectionModel().select(null);
        validityPeriod.setValue(null);
        paymentDate.setValue(null);
    }

    @Override
    public boolean validate() {
        return !validation.isInvalid();
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    @FXML
    public void saveContract() {
        if (validate()) {
            Contract contract = createContract();
            try {
                FormModeRunner.actions()
                        .inNewMode(() -> {
                            contractRepository.persist(contract, contractor);
                            cleanForm();
                        })
                        .inEditMode(() -> contractRepository.update(contract))
                        .run(formMode.getValue());
                notificationsService.showInformation(bundle.getString("notifications.contract.saved.properly"));
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            Validators.showValidationResult(validation);
            notificationsService.showError(Bundles.get("form.contains.errors"));
        }
    }

    private Contract createContract() {
        Contract contract;
        if (formMode.getValue() == Action.NEW) {
            contract = new Contract();
        } else {
            contract = this.contract;
        }
        contract.setContractor(contractor);
        contract.setNumber(contractNumber.getText());
        contract.setPeriod(contractPeriod.getValue().getValue());
        contract.setPaymentDate(DateFormatter.fromLocalDate(paymentDate.getValue()));
        contract.setValidityPeriod(DateFormatter.fromLocalDate(validityPeriod.getValue()));
        return contract;
    }

    public void setFormMode(Action action) {
        this.formMode.setValue(action);
        applyFormMode();
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
        contractNumber.setText(contract.getNumber());
        contractPeriod.getSelectionModel().select(new AbstractComboBoxEnum<>(contract.getPeriod()));
        validityPeriod.setValue(DateFormatter.toLocalDate(contract.getValidityPeriod()));
        paymentDate.setValue(DateFormatter.toLocalDate(contract.getPaymentDate()));
    }

    private void initializePeriodList() {
        List<AbstractComboBoxEnum<Period>> list = Arrays.asList(
                new AbstractComboBoxEnum<>(Period.MONTHLY),
                new AbstractComboBoxEnum<>(Period.QUARTERLY),
                new AbstractComboBoxEnum<>(Period.ANNUAL));
        contractPeriod.setItems(FXCollections.observableList(list));
    }

    private void applyFormMode() {
        FormModeRunner.actions()
                .inNewMode(() -> formHeaderText.setText(bundle.getString("new.contract")))
                .inEditMode(() -> formHeaderText.setText(bundle.getString("new.contract.edit")))
                .run(formMode.getValue());
    }

    private void initializeValues() {
        this.formMode = new SimpleObjectProperty<>();
        this.validityPeriod.setConverter(DateFormatter.createConverter());
        this.paymentDate.setConverter(DateFormatter.createConverter());
    }

    private void initializeRepositories() {
        contractRepository = repositoryProducer.getContractRepository();
    }
}
