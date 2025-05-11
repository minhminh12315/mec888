package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PatientUpdateController {

    @FXML
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error;
    @FXML
    public Button clearButton, saveButton, backButton;

    @FXML
    public TextField firstNameField, lastNameField, phoneField, emailField;
    @FXML
    public ComboBox<String> genderComboBox;
    @FXML
    public Label first_name_error, last_name_error, phone_error, email_error, gender_error;

    private Patient patient;
    private PatientDao patientDao;
    private User user;
    private UserDao userDao;

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            emergency_contact.setText(patient.getEmergency_contact());
            medical_history.setText(patient.getMedical_history());

            this.user = userDao.getUserById(Long.valueOf(patient.getUser().getId()));
            if (user != null){
                firstNameField.setText(user.getFirstName());
                lastNameField.setText(user.getLastName());
                phoneField.setText(user.getPhone());
                emailField.setText(user.getEmail());
                genderComboBox.setValue(user.getGender());
            }
        }

    }

    @FXML
    private void initialize() {
        patientDao = new PatientDao();
        userDao = new UserDao();

        try {
            genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        {
            String email = emailField.getText();
            String phone = phoneField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();

            String username = user.getUsername();
            String password = user.getPassword();

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoleId(4);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGender(gender);
            user.setDateOfBirth(null);
            user.setAddress(null);

            // Save the user to the database
            userDao.updateUser(user);
        }

        {
            String emergencyContact = emergency_contact.getText();
            String medicalHistory = medical_history.getText();

            patient.setEmergency_contact(emergencyContact);
            patient.setMedical_history(medicalHistory);

            patientDao.updatePatient(patient);

            // After updating, navigate back to the Patient Management view
            SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
        }
    }

    public void handleClear() {
        emergency_contact.clear();
        medical_history.clear();

        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();

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

    @FXML
    public void validateInput(KeyEvent event) {
        validateForm();
    }

    public void validateForm() {
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
}