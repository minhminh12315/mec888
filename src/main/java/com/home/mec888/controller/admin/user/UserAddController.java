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
import java.sql.Date;
import java.time.LocalDate;

public class UserAddController {

    @FXML
    private TextField usernameField, firstNameField, lastNameField, emailField, phoneField, addressField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<Role> roleComboBox;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private Label usernameErrorLabel, passwordErrorLabel, emailErrorLabel, phoneErrorLabel, roleErrorLabel,
            firstNameErrorLabel, lastNameErrorLabel, genderErrorLabel, dateOfBirthErrorLabel, addressErrorLabel;

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @FXML
    private void initialize() {
        // Load roles into combo box
        ObservableList<Role> roles = FXCollections.observableArrayList(roleDao.getAllRoles());
        roleComboBox.setItems(roles);

        // Gender options
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Date of Birth - Set initial value to null if you want.
        dateOfBirthPicker.setValue(null);

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
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderComboBox.getValue();
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        String address = addressField.getText();
        // Validate fields with compact checks
        boolean isValid = true;

        // Validate fields
        if (username.isEmpty()) {
            showError(usernameField, usernameErrorLabel, "Username cannot be empty.");
            isValid = false;
        } else if (userDao.isUsernameExists(username)) {
            showError(usernameField, usernameErrorLabel, "Username already exists!");
            isValid = false;
        } else {
            clearError(usernameField, usernameErrorLabel);
        }

        if (firstName.isEmpty()) {
            showError(firstNameField, firstNameErrorLabel, "First Name cannot be empty.");
            isValid = false;
        } else {
            clearError(firstNameField, firstNameErrorLabel);
        }

        if (lastName.isEmpty()) {
            showError(lastNameField, lastNameErrorLabel, "Last Name cannot be empty.");
            isValid = false;
        } else {
            clearError(lastNameField, lastNameErrorLabel);
        }

        if (gender == null) {
            showError(genderComboBox, genderErrorLabel, "Gender cannot be empty.");
            isValid = false;
        } else {
            clearError(genderComboBox, genderErrorLabel);
        }

        if (dateOfBirth == null) {
            showError(dateOfBirthPicker, dateOfBirthErrorLabel, "Date of Birth cannot be empty.");
            isValid = false;
        } else {
            clearError(dateOfBirthPicker, dateOfBirthErrorLabel);
        }

        if (address.isEmpty()) {
            showError(addressField, addressErrorLabel, "Address cannot be empty.");
            isValid = false;
        } else {
            clearError(addressField, addressErrorLabel);
        }

        if (password.isEmpty()) {
            showError(passwordField, passwordErrorLabel, "Password cannot be empty.");
            isValid = false;
        } else {
            clearError(passwordField, passwordErrorLabel);
        }

        if (email.isEmpty()) {
            showError(emailField, emailErrorLabel, "Email cannot be empty.");
            isValid = false;
        } else if (!isValidEmail(email)) {
            showError(emailField, emailErrorLabel, "Invalid email format!");
            isValid = false;
        } else if (userDao.isEmailExists(email)) {
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
            try {
                // Create a new User object
                User user = new User();
                user.setUsername(username);
                user.setPassword(hashPassword(password));
                user.setEmail(email);
                user.setPhone(phone);
                user.setRoleId(Math.toIntExact(selectedRole.getId()));
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setGender(gender);
                user.setDateOfBirth(Date.valueOf(dateOfBirth));
                user.setAddress(address);

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
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        phoneField.clear();
        roleComboBox.setValue(null);
        firstNameField.clear();
        lastNameField.clear();
        genderComboBox.setValue(null);
        dateOfBirthPicker.setValue(null);
        addressField.clear();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d+";
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

    private void clearError(Control field, Label errorLabel){
        field.setStyle("-fx-border-color: #111827");
        errorLabel.setText("");
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