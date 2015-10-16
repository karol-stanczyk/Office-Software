package com.karol.presentation.forms.contractor.newcontractor;

import com.karol.model.Contractor;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.FormMode;
import com.karol.presentation.forms.Validator;
import com.karol.repository.ContractorRepository;
import com.karol.repository.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.NotificationsService;
import com.karol.utils.validation.FieldsValidator;
import com.karol.utils.validation.TextFieldsValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewContractorPresenter implements Initializable, Cleanable, Validator {

    @FXML private TextField contractorName;
    @FXML private TextField contractorLastName;
    @FXML private TextField contractorAddress;
    @FXML private TextField contractorPesel;
    @FXML private TextField contractorNip;
    @FXML private Label formHeaderText;

    @Inject
    private NotificationsService notificationsService;
    @Inject
    private ContractorRepository contractorRepository;

    // FormMode variables
    private FormMode formMode;
    private Contractor editContractor;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formMode = FormMode.NEW;
        this.bundle = resourceBundle;
    }

    @FXML
    public void saveContractor() {
        FieldsValidator validator = validate();
        if (validator.valid()) {
            Contractor contractor = createContractor();
            try {
                if(formMode.equals(FormMode.EDIT)) {
                    contractorRepository.update(contractor);
                } else {
                    contractorRepository.persist(contractor);
                }
                notificationsService.showInformation(bundle.getString("notifications.contractor.saved.properly"));
                cleanForm();
            } catch (DatabaseException e) {
                notificationsService.showError(Bundles.get(e.getMessage()));
            }
        } else {
            notificationsService.showError(validator.getErrorString(), 5);
        }
    }

    private Contractor createContractor() {
        Contractor contractor;
        if(formMode.equals(FormMode.EDIT)) {
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

    @Override
    public void cleanForm() {
        contractorName.setText("");
        contractorLastName.setText("");
        contractorAddress.setText("");
        contractorPesel.setText("");
        contractorNip.setText("");
    }

    @Override
    public FieldsValidator validate() {
        return new TextFieldsValidator()
                .forField(contractorName).notEmpty().onlyLetters().validate()
                .forField(contractorLastName).notEmpty().onlyLetters().validate()
                .forField(contractorAddress).notEmpty().validate()
                .forField(contractorPesel).notEmpty().onlyNumbers().validate()
                .forField(contractorNip).notEmpty().onlyNumbers().validate();
    }

    public void setFormMode(FormMode formMode) {
        this.formMode = formMode;
        applyFormMode();
    }

    public void setEditContractor(Optional<Contractor> editContractor) {
        editContractor.ifPresent(contractor -> {
            this.editContractor = contractor;
            contractorName.setText(contractor.getName());
            contractorLastName.setText(contractor.getLastName());
            contractorAddress.setText(contractor.getAddress());
            contractorPesel.setText(contractor.getPesel());
            contractorNip.setText(contractor.getNip());
        });
    }

    private void applyFormMode() {
        switch (formMode) {
            case NEW: {
                formHeaderText.setText(bundle.getString("new.contractor"));
            }
            break;
            case EDIT: {
                formHeaderText.setText(bundle.getString("new.contractor.edit"));
            }
            break;
        }
    }
}
