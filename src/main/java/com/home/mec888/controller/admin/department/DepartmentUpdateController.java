package com.home.mec888.controller.admin.department;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.entity.Department;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DepartmentUpdateController {
    @FXML
    public TextField nameField;
    @FXML
    public TextArea descriptionField;
    @FXML
    public Label departmentNameError;
    @FXML
    public Label departmentDescriptionError;

    private DepartmentDao departmentDao = new DepartmentDao();
    private Long departmentID;

    private void resetErrorLabels() {
        departmentNameError.setText("");
        departmentDescriptionError.setText("");
    }

    public void showError(Control field, Label errorLabel, String message) {
            field.setStyle("-fx-border-color: red");
//        if (field instanceof TextField || field instanceof PasswordField) {
//        } else if (field instanceof ComboBox) {
//            field.setStyle("-fx-border-color: red");
//        }
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    public void setDepartment(Department department) {
        departmentID = department.getId();
        nameField.setText(department.getName());
        descriptionField.setText(department.getDescription());
    }

    public void handleClear(ActionEvent event) {
        nameField.clear();
        descriptionField.clear();
        resetErrorLabels();
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Kiểm tra ComboBox User
        if (nameField.getText().trim().isEmpty()) {
            showError(nameField, departmentNameError, "Please select a user.");
//            userErrorLabel.setText("Please select a user.");
            isValid = false;
        }
        // Kiểm tra ComboBox Department
        if (descriptionField.getText().trim().isEmpty()) {
            showError(descriptionField, departmentDescriptionError, "Please select a department.");
            isValid = false;
        }

        return isValid;
    }

    public void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return; // Nếu có lỗi, dừng việc lưu
        }
        String departmentName = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        try {
            Department department = new Department();
            department.setName(departmentName);
            department.setDescription(description);
            department.setId(departmentID);
            department.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            departmentDao.updateDepartment(department);
            showAlert("Success", "Showtime updated successfully.", Alert.AlertType.INFORMATION);
            returnToDepartmentManagement(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void returnToDepartmentManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/department/department-management.fxml", actionEvent);
    }

    public void handleBack(ActionEvent event) {
        returnToDepartmentManagement(event);
    }


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
