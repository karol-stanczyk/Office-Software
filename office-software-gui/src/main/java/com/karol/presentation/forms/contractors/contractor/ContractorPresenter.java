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
import com.karol.utils.validation.FieldsValidator;
import com.karol.utils.validation.TextFieldsValidator;
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

public class ContractorPresenter implements Initializable, Cleanable, Validator {

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
        initializeRepositories();
        initializeGoBackButton();
        this.bundle = resourceBundle;
        KeyBinding.registerAction(KeyCode.ENTER, root, this::saveContractor);
    }

    @Override
    public void cleanForm() {
        editContractor = null;
        contractorName.setText("");
        contractorLastName.setText("");
        contractorAddress.setText("");
        contractorPesel.setText("");
        contractorNip.setText("");
        FieldsValidator.removeErrorStyleClasses(contractorName, contractorLastName, contractorAddress, contractorPesel, contractorNip);
    }

    @Override
    public FieldsValidator validate() {
        return new TextFieldsValidator()
                .forField(contractorName).notEmpty().onlyLetters().validate()
                .forField(contractorLastName).notEmpty().onlyLetters().validate()
                .forField(contractorAddress).notEmpty().validate()
                .forField(contractorPesel).onlyNumbers().validate()
                .forField(contractorNip).onlyNumbers().validate();
    }

    @FXML
    public void saveContractor() {
        FieldsValidator validator = validate();
        if (validator.valid()) {
            Contractor contractor = createContractor();
            try {
                FormModeRunner.runWithException(
                        () -> contractorRepository.persist(contractor),
                        () -> contractorRepository.update(contractor),
                        action.getValue());
                notificationsService.showInformation(bundle.getString("notifications.contractor.saved.properly"));
                cleanForm();
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            notificationsService.showError(validator.getErrorString(), 5);
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
        FormModeRunner.run(
                () -> formHeaderText.setText(bundle.getString("new.contractor")),
                () -> formHeaderText.setText(bundle.getString("new.contractor.edit")),
                action.getValue());
    }

    private void initializeGoBackButton() {
        this.action = new SimpleObjectProperty<>();
        this.action.addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                FormModeRunner.run(
                        () -> goBackButton.setVisible(false),
                        () -> goBackButton.setVisible(true),
                        action.getValue());
            }
        });
        this.action.setValue(Action.NEW);
    }

    private void initializeRepositories() {
        contractorRepository = repositoryProducer.getContractorRepository();
    }
}