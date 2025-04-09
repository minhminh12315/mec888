package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class PatientAddController {
    @FXML
    public TextField user_id, emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error, user_id_error;
    @FXML
    public Button clearButton, saveButton, backButton, addUserButton;

    Long lastUserId = null;
    @FXML
    private void initialize() {
        lastUserId = null;
        handleAddUser();
    }
    @FXML
    public void handleAddUser() {
        try {
            lastUserId = getLastUserId();

            if (lastUserId == null) {
                showAlert("Error", "No users available to assign ID", Alert.AlertType.ERROR);
                return;
            }
            System.out.println("user_id: " + lastUserId);
            user_id.setText(String.valueOf(lastUserId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            showAlert("Error", "Failed to save id: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void goToPatientAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-add-user.fxml", actionEvent);
    }

    private Long getLastUserId() {
        UserDao userDao = new UserDao();
        ObservableList<User> userList = FXCollections.observableArrayList(userDao.getAllUsers());
        if (userList.isEmpty()) {
            return null;
        }
        User lastUser = userList.getFirst();
        return lastUser.getId();
    }

    @FXML
    public void handleSave() {
        try {
            // Create patient object with user_id, emergency_contact, and medical_history
            PatientDao patientDao = new PatientDao();
            Patient patient = new Patient(
                    user_id.getText(),
                    emergency_contact.getText(),
                    medical_history.getText()
            );
            patientDao.savePatient(patient);

            // Show success message
            showAlert("Success", "Patient added successfully", Alert.AlertType.INFORMATION);
            handleClear();
        } catch (Exception e) {
            showAlert("Error", "Failed to save patient", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void validateInput(KeyEvent event) {
        Object source = event.getSource();

        if (source == user_id) {
            try {
                Long.parseLong(user_id.getText());
                user_id_error.setText("");  // Clear error if valid number
                saveButton.setDisable(false);
            } catch (NumberFormatException e) {
                user_id_error.setText("User ID must be a valid number.");
                saveButton.setDisable(true);
            }
        } else if (source == emergency_contact) {
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

    @FXML
    public void handleClear() {
        user_id.clear();
        emergency_contact.clear();
        medical_history.clear();

        user_id_error.setText("");
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
}
