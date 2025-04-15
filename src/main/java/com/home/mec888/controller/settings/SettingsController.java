package com.home.mec888.controller.settings;

import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SettingsController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField gender;
    @FXML private TextField dobPicker;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField roleField;
    @FXML private Label labelError;

    private User currentUser;
    private final UserDao userDao = new UserDao(); // ✅ dùng DAO thay vì Service

    public void setUser(User user) {
        this.currentUser = user;
        populateUserData();
    }

    private void populateUserData() {
        if (currentUser == null) return;

        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        gender.setText(currentUser.getGender());

        if (currentUser.getDateOfBirth() != null) {
            LocalDate dob = currentUser.getDateOfBirth().toLocalDate();
            dobPicker.setText(dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        addressField.setText(currentUser.getAddress());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhone());
        roleField.setText(roleNameFromId(currentUser.getRoleId()));
    }

    private String roleNameFromId(Integer roleId) {
        return switch (roleId) {
            case 1 -> "Admin";
            case 2 -> "Bác sĩ";
            case 3 -> "Nhân viên";
            case 4 -> "Bệnh nhân";
            default -> "Không xác định";
        };
    }

    @FXML
    public void handleSave(ActionEvent event) {
        labelError.setText("");
        labelError.setStyle("-fx-text-fill: red;");

        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (!isValidEmail(email)) {
            labelError.setText("Email không hợp lệ!");
            return;
        }

        if (!isValidPhone(phone)) {
            labelError.setText("Số điện thoại không hợp lệ! (9–11 chữ số)");
            return;
        }

        if (address == null || address.trim().isEmpty()) {
            labelError.setText("Địa chỉ không được để trống!");
            return;
        }

        // ✅ Nếu hợp lệ → cập nhật thông tin cho đối tượng
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);

        // ✅ Gọi DAO để lưu lại DB
        userDao.updateUser(currentUser);

        labelError.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        labelError.setText("Cập nhật thành công!");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^\\S+@\\S+\\.\\S+$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{9,11}");
    }
}
