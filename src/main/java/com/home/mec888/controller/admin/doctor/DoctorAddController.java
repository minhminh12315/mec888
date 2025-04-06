package com.home.mec888.controller.admin.doctor;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DoctorAddController {
    @FXML
    public ComboBox<User> userComboBox;
    @FXML
    public ComboBox<Department> departmentComboBox;
    @FXML
    public TextField specializationField;
    @FXML
    public TextField licenseField;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label departmentErrorLabel;
    @FXML
    private Label specializationErrorLabel;
    @FXML
    private Label licenseErrorLabel;


    private UserDao userDao;
    private DepartmentDao departmentDao;
    private DoctorDao doctorDao;

    @FXML
    public void initialize() {
        userDao = new UserDao();
        departmentDao = new DepartmentDao();
        doctorDao = new DoctorDao();

        userComboBox.getItems().addAll(userDao.getAllUsers());
        departmentComboBox.getItems().addAll(departmentDao.getAllDepartments());

        userComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText((user == null || empty) ? "" : String.valueOf(user.getUsername()));
            }
        });

        userComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return (user != null) ? String.valueOf(user.getUsername()) : "";
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        departmentComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);
                setText((department == null || empty) ? "" : String.valueOf(department.getName()));
            }
        });

        departmentComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department department) {
                return (department != null) ? String.valueOf(department.getName()) : "";
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });

        resetErrorLabels();
    }

    private void resetErrorLabels() {
        userErrorLabel.setText("");
        departmentErrorLabel.setText("");
        specializationErrorLabel.setText("");
        licenseErrorLabel.setText("");
    }

    public void showError(Control field, Label errorLabel, String message) {
        if (field instanceof TextField || field instanceof PasswordField) {
            field.setStyle("-fx-border-color: red");
        } else if (field instanceof ComboBox) {
            field.setStyle("-fx-border-color: red");
        }
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Kiểm tra ComboBox User
        if (userComboBox.getValue() == null) {
            showError(userComboBox, userErrorLabel, "Please select a user.");
//            userErrorLabel.setText("Please select a user.");
            isValid = false;
        }
        // Kiểm tra ComboBox Department
        if (departmentComboBox.getValue() == null) {
            showError(departmentComboBox, departmentErrorLabel, "Please select a department.");
            isValid = false;
        }

        // Kiểm tra Specialization Field
        if (specializationField.getText().trim().isEmpty()) {
            showError(specializationField, specializationErrorLabel, "Please enter specialization.");
            isValid = false;
        }

        // Kiểm tra License Field
        if (licenseField.getText().trim().isEmpty()) {
            showError(licenseField, licenseErrorLabel, "Please enter a license.");
            isValid = false;
        }
        return isValid;
    }

    public void handleClear(ActionEvent event) {
// Xóa lựa chọn của các ComboBox
        userComboBox.getSelectionModel().clearSelection();
        departmentComboBox.getSelectionModel().clearSelection();

        // Xóa nội dung của các TextField
        specializationField.clear();
        licenseField.clear();

        resetErrorLabels();
    }

    public void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return; // Nếu có lỗi, dừng việc lưu
        }
        User user = userComboBox.getValue();
        Department department = departmentComboBox.getValue();
        String specialization = specializationField.getText().trim();
        String license_number = licenseField.getText().trim();
        resetErrorLabels(); // Xóa các thông báo lỗi cũ

        try {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setDepartment(department);
            doctor.setSpecialization(specialization);
            doctor.setLicense_number(license_number);

            doctorDao.saveDoctor(doctor);

            showAlert("Success", "Doctor added successfully!", Alert.AlertType.INFORMATION);
            returnToDoctorManagement(event);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    private void returnToDoctorManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/doctor/doctor-management.fxml", actionEvent);
    }

    public void handleBack(ActionEvent event) {
        returnToDoctorManagement(event);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
