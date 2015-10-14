package com.karol.presentation.forms.contractor.newcontractor;

import com.karol.model.Contractor;
import com.karol.presentation.forms.Cleanable;
import com.karol.presentation.forms.Validator;
import com.karol.repository.ContractorRepository;
import com.karol.repository.DatabaseException;
import com.karol.utils.Bundles;
import com.karol.utils.NotificationsService;
import com.karol.utils.validation.FieldsValidator;
import com.karol.utils.validation.TextFieldsValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class NewContractorPresenter implements Initializable, Cleanable, Validator {

    @FXML
    private TextField contractorName;
    @FXML
    private TextField contractorLastName;
    @FXML
    private TextField contractorAddress;
    @FXML
    private TextField contractorPesel;
    @FXML
    private TextField contractorNip;

    @Inject
    private NotificationsService notificationsService;
    @Inject
    private ContractorRepository contractorRepository;


    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
    }

    @FXML
    public void saveContractor() {
        FieldsValidator validator = validate();
        if (validator.valid()) {
            Contractor contractor = createContractor();
            try {
                contractorRepository.persist(contractor);
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
        Contractor contractor = new Contractor();
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
}
