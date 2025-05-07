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

public class SpecializationUpdateController {
    @FXML
    public TextField nameField;

    @FXML
    public Label nameErrorLabel;

    @FXML
    public Button saveButton, clearButton, backButton;

    private SpecializationDao specializationDao;
    private Specialization specialization;

    @FXML
    private void initialize() {
        specializationDao = new SpecializationDao();
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String name = nameField.getText().trim();

        boolean valid = true;

        // Validate field
        if (name.isEmpty()) {
            nameErrorLabel.setText("Specialization name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else if (name.length() > 255) {
            nameErrorLabel.setText("Specialization name cannot exceed 255 characters.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else {
            // Check if the name already exists for a different specialization
            List<Specialization> existingSpecializations = specializationDao.getAllSpecializations();
            boolean nameExists = existingSpecializations.stream()
                    .anyMatch(s -> s.getName().equalsIgnoreCase(name) && !s.getId().equals(specialization.getId()));

            if (nameExists) {
                nameErrorLabel.setText("Specialization name already exists.");
                nameErrorLabel.setVisible(true);
                valid = false;
            } else {
                nameErrorLabel.setVisible(false);
            }
        }

        if (!valid) {
            return; // If there are errors, don't update
        }

        specialization.setName(name);

        specializationDao.updateSpecialization(specialization);
        showAlert("Successful", "Updated specialization successfully", Alert.AlertType.INFORMATION);
        returnToSpecializationManagement(actionEvent);
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;

        if (specialization != null) {
            nameField.setText(specialization.getName());
        }
    }

    public void handleClear(ActionEvent actionEvent) {
        if (specialization != null) {
            nameField.setText(specialization.getName());
        }

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