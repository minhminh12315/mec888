package com.home.mec888.controller.admin.user;

import com.home.mec888.dao.UserDao;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.entity.User;
import com.home.mec888.entity.Role;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAddController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<Role> roleComboBox;

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @FXML
    private void initialize() {
        // Load roles into combo box
        ObservableList<Role> roles = FXCollections.observableArrayList(roleDao.getAllRoles());
        roleComboBox.setItems(roles);

        // Set the cell factory to display only the role name
        roleComboBox.setCellFactory(param -> new ListCell<Role>() {
            @Override
            protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);
                if (empty || role == null) {
                    setText(null);
                } else {
                    setText(role.getName());
                }
            }
        });

        // Set the button cell to display the selected value correctly
        roleComboBox.setButtonCell(new ListCell<Role>() {
            @Override
            protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);
                if (empty || role == null) {
                    setText(null);
                } else {
                    setText(role.getName());
                }
            }
        });
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        Role selectedRole = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || selectedRole == null) {
            showAlert("Error", "Please fill in all required fields!", Alert.AlertType.ERROR);
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showAlert("Error", "Invalid email format!", Alert.AlertType.ERROR);
            return;
        }

        // Validate phone format if provided
        if (!phone.isEmpty() && !isValidPhone(phone)) {
            showAlert("Error", "Invalid phone format!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Check if username already exists
            if (userDao.isUsernameExists(username)) {
                showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
                return;
            }

            // Check if email already exists
            if (userDao.isEmailExists(email)) {
                showAlert("Error", "Email already exists!", Alert.AlertType.ERROR);
                return;
            }

            // Create a new User object
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashPassword(password));
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoleId(Math.toIntExact(selectedRole.getId()));

            // Save the user to the database
            userDao.saveUser(user);
            showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);

            // Clear the fields after saving
            handleClear();

            returnToUserManagement(event);

        } catch (Exception e) {
            showAlert("Error", "Error saving user: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        phoneField.clear();
        roleComboBox.setValue(null);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";  // Simple 10-digit validation
        return phone.matches(phoneRegex);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = messageDigest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


    @FXML
    public void handleBack(ActionEvent actionEvent) {
        returnToUserManagement(actionEvent);
    }

    private void returnToUserManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/user/user-management.fxml", actionEvent);
    }
}