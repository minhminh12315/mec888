package com.home.mec888.controller.admin.specialization;

import com.home.mec888.dao.SpecializationDao;
import com.home.mec888.entity.Specialization;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class SpecializationAddController {
    @FXML
    public TextField nameField;

    @FXML
    public Label nameErrorLabel;

    @FXML
    public Button saveButton, clearButton, backButton;

    private SpecializationDao specializationDao = new SpecializationDao();

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        String specializationName = nameField.getText().trim();

        boolean valid = true;

        // Validate field
        if (specializationName.isEmpty()) {
            nameErrorLabel.setText("Specialization name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else if (specializationName.length() > 255) {
            nameErrorLabel.setText("Specialization name cannot exceed 255 characters.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else {
            // Check if a specialization with the same name already exists
            List<Specialization> existingSpecializations = specializationDao.getAllSpecializations();
            boolean nameExists = existingSpecializations.stream()
                    .anyMatch(s -> s.getName().equalsIgnoreCase(specializationName));

            if (nameExists) {
                nameErrorLabel.setText("Specialization name already exists.");
                nameErrorLabel.setVisible(true);
                valid = false;
            } else {
                nameErrorLabel.setVisible(false);
            }
        }

        if (!valid) {
            return; // If there are errors, don't save
        }

        Specialization specialization = new Specialization();
        specialization.setName(specializationName);

        specializationDao.saveSpecialization(specialization);
        showAlert("Success", "Specialization added successfully!", Alert.AlertType.INFORMATION);

        handleClear();
        returnToSpecializationManagement(actionEvent);
    }

    @FXML
    private void handleClear() {
        nameField.clear();
        clearErrorLabels();
    }

    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        returnToSpecializationManagement(actionEvent);
    }

    private void returnToSpecializationManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/specialization/specialization-management.fxml", actionEvent);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}