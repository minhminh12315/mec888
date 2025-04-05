package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class PatientAddController {
    public TextField first_name;
    public TextField last_name;
    public TextField patient_email;
    public TextField patient_phone;
    public DatePicker date_of_birth;
    public ComboBox<String> gender;
    public TextField address;
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label fn_error, ln_error, email_error, dob_error, gender_error, address_error,
            contact_error, medical_error, phone_error;
    @FXML
    public Button clearButton, saveButton, backButton;

    private static final String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final Random random = new Random();


    public void handleSave() {
        try {
            String username = randomUsername();
            String password = hashPassword(randomPassword());
            String email = patient_email.getText();
            String phone = patient_phone.getText();

            UserDao userDao = new UserDao();
            User user = new User(username, password, email, phone, 4);

            if (userDao.getUserByEmail(email) != null) {
                email_error.setText("Email already exist. Please enter a new one");
                return;
            }
            userDao.saveUser(user);

            User retrievedUser = userDao.getUserByUsername(username);

            PatientDao patientDao = new PatientDao();
            Patient patient = new Patient(retrievedUser.getId(), first_name.getText(), last_name.getText(),
                    Date.valueOf(date_of_birth.getValue()), gender.getValue(), address.getText(),
                    emergency_contact.getText(), medical_history.getText());
            patientDao.savePatient(patient);

            showAlert("Success", "Save patient successfully", Alert.AlertType.INFORMATION);
            handleClear();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Failed to save patient", Alert.AlertType.ERROR);
        }
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
        Object source = event.getSource();

        if (source == first_name) {
            String patientFirstName = first_name.getText();
            if (patientFirstName.length() > 255) {
                fn_error.setText("First Name cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else if (patientFirstName.isEmpty()) {
                fn_error.setText("First Name cannot be empty.");
                saveButton.setDisable(true);
            } else {
                fn_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == last_name) {
            String patientLastName = last_name.getText();
            if (patientLastName.length() > 255) {
                ln_error.setText("Last Name cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else if (patientLastName.isEmpty()) {
                ln_error.setText("Last Name cannot be empty.");
                saveButton.setDisable(true);
            } else {
                ln_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == patient_email) {
            String patientEmail = patient_email.getText();
            if (!patientEmail.matches("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$")) {
                email_error.setText("Invalid email format.");
                saveButton.setDisable(true);
            } else if (patientEmail.isEmpty()) {
                email_error.setText("Email cannot be empty.");
                saveButton.setDisable(true);
            } else {
                email_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == date_of_birth) {
            LocalDate patientDOB = date_of_birth.getValue();
            if (patientDOB == null) {
                dob_error.setText("Please select a date of birth.");
                saveButton.setDisable(true);
            } else if (!patientDOB.isBefore(LocalDate.now())) {
                dob_error.setText("Date of birth must be in the past.");
                saveButton.setDisable(true);
            } else {
                dob_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == patient_phone) {
            String patientPhone = patient_phone.getText();
            if (!patientPhone.matches("^[0-9]{10}$")) {
                phone_error.setText("Invalid phone number format. Must be 10 digits.");
                saveButton.setDisable(true);
            } else if (patientPhone.isEmpty()) {
                phone_error.setText("Phone number cannot be empty.");
                saveButton.setDisable(true);
            } else {
                phone_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == address) {
            String patientAddress = address.getText();
            if (patientAddress.length() > 255) {
                address_error.setText("Address cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else {
                address_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == emergency_contact) {
            String patientEmergencyContact = emergency_contact.getText();
            if (patientEmergencyContact.length() > 255) {
                contact_error.setText("Emergency Contact cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else {
                contact_error.setText("");
                saveButton.setDisable(false);
            }
        }
    }

    @FXML
    public void validateGender() {
        String selectedGender = gender.getValue();
        if (selectedGender == null || selectedGender.isEmpty()) {
            gender_error.setText("Please select a gender.");
            saveButton.setDisable(true);
        } else {
            gender_error.setText("");
            saveButton.setDisable(false);
        }
    }

    public void handleClear() {
        first_name.clear();
        last_name.clear();
        date_of_birth.setValue(null);
        gender.getSelectionModel().clearSelection();
        address.clear();
        emergency_contact.clear();
        medical_history.clear();

        fn_error.setText("");
        ln_error.setText("");
        dob_error.setText("");
        phone_error.setText("");
        address_error.setText("");
        contact_error.setText("");
        email_error.setText("");
        gender_error.setText("");
        medical_error.setText("");

        saveButton.setDisable(true);
    }

    public void handleBack(ActionEvent actionEvent) {
        returnToPatientManagement(actionEvent);
    }

    public void returnToPatientManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    public String randomUsername() {
        StringBuilder username = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(word.length());
            username.append(word.charAt(index));
        }
        return username.toString();
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

    private String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
