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
import javafx.util.StringConverter;

public class PatientAddController {
    @FXML
    public ComboBox<User> user_id;
    @FXML
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error, user_id_error;
    @FXML
    public Button clearButton, saveButton, backButton, addUserButton;

    private UserDao userDao;

    Long lastUserId = null;
    @FXML
    private void initialize() {

        if (lastUserId != null){
            handleAddUser();
        } else {
            userDao = new UserDao();
            user_id.getItems().addAll(userDao.getAllUsers());

            user_id.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    setText((user == null || empty) ? "" : String.valueOf(user.getId()));
                }
            });

            user_id.setConverter(new StringConverter<>() {
                @Override
                public String toString(User user) {
                    return (user != null) ? String.valueOf(user.getId()) : "";
                }

                @Override
                public User fromString(String string) {
                    return null;
                }
            });
        }
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
            User lastUser = userDao.getUserById(lastUserId);  // Assuming you have a method to get a User by ID
            if (lastUser != null) {
                user_id.getSelectionModel().select(lastUser);  // Set the User in the ComboBox
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Failed to save id: " + e.getMessage(), Alert.AlertType.ERROR);
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
                    user_id.getValue(),
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
                Long.parseLong(String.valueOf(user_id.getValue()));
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
        user_id.setConverter(null);
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
