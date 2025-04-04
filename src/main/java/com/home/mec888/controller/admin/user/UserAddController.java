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

    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label roleErrorLabel;

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

        // Reset error labels
        usernameErrorLabel.setText("");
        passwordErrorLabel.setText("");
        emailErrorLabel.setText("");
        phoneErrorLabel.setText("");
        roleErrorLabel.setText("");

        // Validate fields
        if (username.isEmpty()) {
            showError(usernameField, usernameErrorLabel, "Username cannot be empty.");
            return;
        }
//        if (password.isEmpty()) {
//            showError(passwordField, passwordErrorLabel, "Password cannot be empty.");
//            return;
//        }
        if (email.isEmpty()) {
            showError(emailField, emailErrorLabel, "Email cannot be empty.");
            return;
        }
        if (selectedRole == null) {
            showError(roleComboBox, roleErrorLabel, "Role cannot be empty.");
            return;
        }

        if (!phone.isEmpty()) {
            showError(phoneField, phoneErrorLabel, "Phone cannot be empty.");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showError(emailField, emailErrorLabel, "Invalid email format!");
            return;
        }

        // Validate phone format if provided
        if (!isValidPhone(phone)) {
            showError(phoneField, phoneErrorLabel, "Invalid phone format!");
            return;
        }

        try {
            // Check if username already exists
            if (userDao.isUsernameExists(username)) {
                showError(usernameField, usernameErrorLabel, "Username already exists!");
                return;
            }

            // Check if email already exists
            if (userDao.isEmailExists(email)) {
                showError(emailField, emailErrorLabel, "Email already exists!");
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
            showAlert("Error", "Error adding user: " + e.getMessage(), Alert.AlertType.ERROR);
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

    private void showError(Control field, Label errorLabel, String message) {
        // Set the border color to red for fields with errors
        if (field instanceof TextField || field instanceof PasswordField) {
            field.setStyle("-fx-border-color: red");
        } else if (field instanceof ComboBox) {
            field.setStyle("-fx-border-color: red");
        }

        // Display the error message on the respective label
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red");
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