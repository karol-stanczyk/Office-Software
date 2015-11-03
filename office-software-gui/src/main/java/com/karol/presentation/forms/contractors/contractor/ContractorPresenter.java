package com.karol.presentation.forms.contractors.contractor;

import com.karol.model.Contractor;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormModeRunner;
import com.karol.presentation.forms.Validator;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.navigation.Action;
import com.karol.presentation.navigation.GoBackNavigator;
import com.karol.repository.ContractorRepository;
import com.karol.repository.access.RepositoryProducer;
import com.karol.repository.utils.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.KeyBinding;
import com.karol.utils.notifications.NotificationsService;
import com.karol.utils.validation.Validators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static com.karol.utils.validation.Validators.notEmptyValidator;
import static com.karol.utils.validation.Validators.onlyLettersValidator;

public class ContractorPresenter extends Validator implements Initializable, Cleanable {

    @FXML private TextField contractorName;
    @FXML private TextField contractorLastName;
    @FXML private TextField contractorAddress;
    @FXML private TextField contractorPesel;
    @FXML private TextField contractorNip;
    @FXML private Label formHeaderText;
    @FXML private Button goBackButton;
    @FXML private Parent root;

    @Inject private RepositoryProducer repositoryProducer;
    private ContractorRepository contractorRepository;

    @Inject private NotificationsService notificationsService;
    @Inject private LayoutService layoutService;
    @Inject private GoBackNavigator goBackNavigator;

    // Action variables
    private Property<Action> action;
    private Contractor editContractor;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initializeRepositories();
        initializeGoBackButton();
        this.bundle = resourceBundle;
        KeyBinding.registerActionForAllChildren(KeyCode.ENTER, root, this::saveContractor);
    }

    @Override
    protected void registerValidators() {
        validation.registerValidator(contractorName, onlyLettersValidator());
        validation.registerValidator(contractorLastName, onlyLettersValidator());
        validation.registerValidator(contractorAddress, notEmptyValidator());
    }

    @Override
    public void cleanForm() {
        editContractor = null;
        contractorName.setText("");
        contractorLastName.setText("");
        contractorAddress.setText("");
        contractorPesel.setText("");
        contractorNip.setText("");
    }

    @Override
    public boolean validate() {
        return !validation.isInvalid();
    }

    @FXML
    public void saveContractor() {
        if (validate()) {
            Contractor contractor = createContractor();
            try {
                FormModeRunner.actions()
                        .inNewMode(() -> {
                            contractorRepository.persist(contractor);
                            cleanForm();
                        })
                        .inEditMode(() -> contractorRepository.update(contractor))
                        .run(action.getValue());
                notificationsService.showInformation(bundle.getString("notifications.contractor.saved.properly"));
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            notificationsService.showError(Bundles.get("form.contains.errors"));
            Validators.showValidationResult(validation);
        }
    }

    @FXML
    public void goBack() {
        layoutService.showView(goBackNavigator.getGoBackView(this));
    }

    public void setAction(Action action) {
        this.action.setValue(action);
        applyFormMode();
    }

    public void setEditContractor(Contractor contractor) {
        this.editContractor = contractor;
        contractorName.setText(contractor.getName());
        contractorLastName.setText(contractor.getLastName());
        contractorAddress.setText(contractor.getAddress());
        contractorPesel.setText(contractor.getPesel());
        contractorNip.setText(contractor.getNip());
    }

    private Contractor createContractor() {
        Contractor contractor;
        if (action.getValue().equals(Action.EDIT)) {
            contractor = editContractor;
        } else {
            contractor = new Contractor();
        }
        contractor.setName(contractorName.getText());
        contractor.setLastName(contractorLastName.getText());
        contractor.setAddress(contractorAddress.getText());
        contractor.setPesel(contractorPesel.getText());
        contractor.setNip(contractorNip.getText());
        return contractor;
    }

    private void applyFormMode() {
        FormModeRunner.actions()
                .inNewMode(() -> formHeaderText.setText(bundle.getString("new.contractor")))
                .inEditMode(() -> formHeaderText.setText(bundle.getString("new.contractor.edit")))
                .run(action.getValue());
    }

    private void initializeGoBackButton() {
        this.action = new SimpleObjectProperty<>();
        this.action.addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                FormModeRunner.actions()
                        .inNewMode(() -> goBackButton.setVisible(false))
                        .inEditMode(() -> goBackButton.setVisible(true))
                        .run(action.getValue());
            }
        });
        this.action.setValue(Action.NEW);
    }

    private void initializeRepositories() {
        contractorRepository = repositoryProducer.getContractorRepository();
    }
}
