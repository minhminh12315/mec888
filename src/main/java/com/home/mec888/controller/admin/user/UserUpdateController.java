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

    private UserDao userDao;
    private RoleDao roleDao;
    private User user;

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

        if (username.isEmpty() || email.isEmpty() || selectedRole == null) {
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
            // Check if username already exists (excluding current user)
            if (!username.equals(user.getUsername()) && userDao.isUsernameExists(username)) {
                showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
                return;
            }

            // Check if email already exists (excluding current user)
            if (!email.equals(user.getEmail()) && userDao.isEmailExists(email)) {
                showAlert("Error", "Email already exists!", Alert.AlertType.ERROR);
                return;
            }

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


}