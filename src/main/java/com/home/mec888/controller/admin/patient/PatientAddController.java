package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class PatientAddController {
    @FXML
    public ComboBox<User> user_id;
    @FXML
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error, user_id_error;
    @FXML
    public Button clearButton, saveButton, backButton, addUserButton;
    @FXML
    public TextField firstNameField, lastNameField, phoneField, emailField;
    @FXML
    public ComboBox<String> genderComboBox;
    @FXML
    public Label first_name_error, last_name_error, phone_error, email_error, gender_error, username_error;

    UserDao userDao = new UserDao();
    private User user;
    private static final String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final Random random = new Random();

    @FXML
    private void initialize() {
        try {
            genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try {
            String email = emailField.getText();
            String phone = phoneField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();

            String username = firstName + " " + lastName;

            if (userDao.isUsernameExists(username)){
                username_error.setText("Username already exists!");
            } else {
                username_error.setText("");
            }

            String password = randomPassword();

            try {
                // Create a new User object
                User user = new User();
                user.setUsername(username);
                user.setPassword(hashPassword(password));
                user.setEmail(email);
                user.setPhone(phone);
                user.setRoleId(4);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setGender(gender);
                user.setDateOfBirth(null);
                user.setAddress(null);

                // Save the user to the database
                userDao.saveUser(user);
//                showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);
                showAlert("Patient" + username, "Random password!"+password, Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                showAlert("Error", "Error adding user: " + e.getMessage(), Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to save user", Alert.AlertType.ERROR);
        }


        try {
            Long last_user_id = getLastUserId();

            // Create patient object with user_id, emergency_contact, and medical_history
            PatientDao patientDao = new PatientDao();
            Patient patient = new Patient();
            patient.setUser(userDao.getUserById(last_user_id));
            patient.setEmergency_contact(emergency_contact.getText());
            patient.setMedical_history(String.valueOf(medical_history));
            patientDao.savePatient(patient);

            // Show success message
            showAlert("Success", "Patient added successfully", Alert.AlertType.INFORMATION);

            SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
        } catch (Exception e) {
            showAlert("Error", "Failed to save patient", Alert.AlertType.ERROR);
        }
    }

    private Long getLastUserId() {
        UserDao userDao = new UserDao();
        ObservableList<User> userList = FXCollections.observableArrayList(userDao.getAllUsers());
        if (userList.isEmpty()) {
            return null;
        }
        User lastUser = userList.getFirst();
        return lastUser.getId();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void validateInput(KeyEvent event) {
        validateForm();
    }

    private void validateForm() {
        boolean valid = true;

        // First Name
        String firstName = firstNameField.getText();
        if (firstName == null || firstName.trim().isEmpty()) {
            first_name_error.setText("First Name cannot be empty.");
            valid = false;
        } else {
            first_name_error.setText("");
        }

        // Last Name
        String lastName = lastNameField.getText();
        if (lastName == null || lastName.trim().isEmpty()) {
            last_name_error.setText("Last Name cannot be empty.");
            valid = false;
        } else {
            last_name_error.setText("");
        }

        // Phone
        String phone = phoneField.getText();
        if (phone == null || phone.trim().isEmpty()) {
            phone_error.setText("Phone cannot be empty.");
            valid = false;
        } else if (!isValidPhone(phone)) {
            phone_error.setText("Invalid phone format!");
            valid = false;
        } else {
            phone_error.setText("");
        }

        // Email
        String email = emailField.getText();
        if (email == null || email.trim().isEmpty()) {
            email_error.setText("Email cannot be empty.");
            valid = false;
        } else if (!isValidEmail(email)) {
            email_error.setText("Invalid email format!");
            valid = false;
        } else if (userDao.isEmailExists(email)) {
            email_error.setText("Email already exists!");
            valid = false;
        } else {
            email_error.setText("");
        }

        // Gender
        String gender = genderComboBox.getValue();
        if (gender == null || gender.trim().isEmpty()) {
            gender_error.setText("Gender cannot be empty.");
            valid = false;
        } else {
            gender_error.setText("");
        }

        // Emergency Contact
        String contact = emergency_contact.getText();
        if (contact == null || contact.trim().isEmpty()) {
            contact_error.setText("Emergency Contact cannot be empty.");
            valid = false;
        } else if (contact.length() > 255) {
            contact_error.setText("Emergency Contact cannot exceed 255 characters.");
            valid = false;
        } else {
            contact_error.setText("");
        }

        // Medical History
        String history = medical_history.getText();
        if (history != null && history.length() > 500) {
            medical_error.setText("Medical History cannot exceed 500 characters.");
            valid = false;
        } else {
            medical_error.setText("");
        }

        saveButton.setDisable(!valid);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d+";
        return phone.matches(phoneRegex);
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
    public void handleClear() {
        emergency_contact.clear();
        medical_history.clear();
        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();
//        genderComboBox.setConverter(null);

        contact_error.setText("");
        medical_error.setText("");
        first_name_error.setText("");
        last_name_error.setText("");
        phone_error.setText("");
        email_error.setText("");
        gender_error.setText("");

        saveButton.setDisable(true);
    }

    public void handleBack(ActionEvent actionEvent) {
        returnToPatientManagement(actionEvent);
    }

    public void returnToPatientManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    public String randomPassword() {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(word.length());
            password.append(word.charAt(index));
        }

        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(digits.length());
            password.append(digits.charAt(index));
        }

        return password.toString();
    }

}
