package com.home.mec888.controller.admin.department;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.entity.Department;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DepartmentUpdateController {
    @FXML
    public TextField nameField;
    @FXML
    public TextArea descriptionField;

    private DepartmentDao departmentDao = new DepartmentDao();
    private Long departmentID;

    public void setDepartment(Department department) {
        departmentID = department.getId();
        nameField.setText(department.getName());
        descriptionField.setText(department.getDescription());
    }

    public void handleClear(ActionEvent event) {
        nameField.clear();
        descriptionField.clear();
    }

    public void handleSave(ActionEvent event) {
        String departmentName = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        if (departmentName.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill in all required fields!", Alert.AlertType.ERROR);
            return;
        }

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
