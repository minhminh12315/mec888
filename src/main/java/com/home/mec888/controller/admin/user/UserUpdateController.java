package com.home.mec888.controller.admin.user;

import com.home.mec888.dao.UserDao;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.entity.User;
import com.home.mec888.entity.Role;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class UserUpdateController {

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
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label roleErrorLabel;

    private UserDao userDao;
    private RoleDao roleDao;
    private User user;
    private boolean clearError = true;

    @FXML
    private void initialize() {
        userDao = new UserDao();
        roleDao = new RoleDao();

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

    public void setUser(User user) {
        this.user = user;

        // Update UI with user data
        if(user != null) {
            usernameField.setText(user.getUsername());
            System.out.println(user);
            // Don't populate password for security reasons
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());

            // Find and select the correct role
            for (Role role : roleComboBox.getItems()) {
                if (Objects.equals(role.getId(), Long.valueOf(user.getRoleId()))) {
                    roleComboBox.setValue(role);
                    break;
                }
            }
        }
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String username = usernameField.getText();
//        String password = passwordField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        Role selectedRole = roleComboBox.getValue();

        // Validate fields with compact checks
        boolean isValid = true;

        if (username.isEmpty()) {
            showError(usernameField, usernameErrorLabel, "Username cannot be empty.");
            isValid = false;
        } else if (!username.equals(user.getUsername()) && userDao.isUsernameExists(username)) {
            showError(usernameField, usernameErrorLabel, "Username already exists!");
            isValid = false;
        } else {
            clearError(usernameField, usernameErrorLabel);
        }

//        if (password.isEmpty()) {
//            showError(passwordField, passwordErrorLabel, "Password cannot be empty.", false);
//            isValid = false;
//        } else {
//            clearError(passwordField, passwordErrorLabel);
//        }

        if (email.isEmpty()) {
            showError(emailField, emailErrorLabel, "Email cannot be empty.");
            isValid = false;
        } else if (!isValidEmail(email)) {
            showError(emailField, emailErrorLabel, "Invalid email format!");
            isValid = false;
        } else if (!email.equals(user.getEmail()) && userDao.isEmailExists(email)) {
            showError(emailField, emailErrorLabel, "Email already exists!");
            isValid = false;
        } else {
            clearError(emailField, emailErrorLabel);
        }

        if (phone.isEmpty()) {
            showError(phoneField, phoneErrorLabel, "Phone cannot be empty.");
            isValid = false;
        } else if (!isValidPhone(phone)) {
            showError(phoneField, phoneErrorLabel, "Invalid phone format!");
            isValid = false;
        } else {
            clearError(phoneField, phoneErrorLabel);
        }

        if (selectedRole == null) {
            showError(roleComboBox, roleErrorLabel, "Role cannot be empty.");
            isValid = false;
        } else {
            clearError(roleComboBox, roleErrorLabel);
        }

        if (isValid) {
            clearError = true;
            try {
                // Update user object
                user.setUsername(username);
    //            if (!password.isEmpty()) {
    //                user.setPassword(hashPassword(password));
    //            }
                user.setEmail(email);
                user.setPhone(phone);
                user.setRoleId(Math.toIntExact(selectedRole.getId()));

                // Update user in database
                userDao.updateUser(user);
                showAlert("Success", "User updated successfully!", Alert.AlertType.INFORMATION);

                // Return to user management screen
                SceneSwitcher.loadView("admin/user/user-management.fxml", actionEvent);

            } catch (Exception e) {
                showAlert("Error", "Error updating user: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/user/user-management.fxml", actionEvent);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";  // Simple 10-digit validation
        return phone.matches(phoneRegex);
    }

    private void showError(Control field, Label errorLabel, String message){
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

    private void clearError(Control field, Label errorLabel){
        field.setStyle("-fx-border-color: #111827");
        errorLabel.setText("");
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
    private void returnToUserManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/user/user-management.fxml", actionEvent);
    }

    public void handleBack(ActionEvent event) {
        returnToUserManagement(event);
    }
}