package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PatientUpdateController {

    @FXML
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error;
    @FXML
    public Button clearButton, saveButton, backButton;

    private Patient patient;
    private PatientDao patientDao;

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            emergency_contact.setText(patient.getEmergency_contact());
            medical_history.setText(patient.getMedical_history());
        }
    }

    @FXML
    private void initialize() {
        patientDao = new PatientDao();
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String emergencyContact = emergency_contact.getText();
        String medicalHistory = medical_history.getText();

        patient.setEmergency_contact(emergencyContact);
        patient.setMedical_history(medicalHistory);

        patientDao.updatePatient(patient);

        // After updating, navigate back to the Patient Management view
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    public void handleClear() {
        emergency_contact.clear();
        medical_history.clear();

        contact_error.setText("");
        medical_error.setText("");

        saveButton.setDisable(true);
    }

    public void handleBack(ActionEvent actionEvent) {
        returnToPatientManagement(actionEvent);
    }

    public void returnToPatientManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    @FXML
    public void validateUpdate(KeyEvent event) {
        Object source = event.getSource();

        if (source == emergency_contact) {
            String contact = emergency_contact.getText();
            if (contact.length() > 255) {
                contact_error.setText("Emergency Contact cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else if (contact.isEmpty()) {
                contact_error.setText("Emergency Contact cannot be empty.");
                saveButton.setDisable(true);
            } else {
                contact_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == medical_history) {
            String history = medical_history.getText();
            if (history.length() > 500) {
                medical_error.setText("Medical History cannot exceed 500 characters.");
                saveButton.setDisable(true);
            } else {
                medical_error.setText("");
                saveButton.setDisable(false);
            }
        }
    }
}