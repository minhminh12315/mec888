package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
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

import java.sql.Date;
import java.time.LocalDate;

public class PatientUpdateController {
    public TextField first_name;
    public TextField last_name;
    public TextField patient_email;
    public TextField patient_phone;
    public DatePicker date_of_birth;
    public ComboBox<String> gender;
    public TextField address;
    public TextField emergency_contact;
    public TextField medical_history;
    @FXML
    public Label fn_error, ln_error, email_error, dob_error, gender_error, address_error,
            contact_error, medical_error, phone_error;
    @FXML
    public Button clearButton, saveButton, backButton;

    private Patient patient;
    private PatientDao patientDao;
    private UserDao userDao;
    private User user;

    public void setPatient(Patient patient, User user) {
        this.patient = patient;
        this.user = user;

        if (patient != null) {
            first_name.setText(patient.getFirst_name());
            last_name.setText(patient.getLast_name());
            patient_email.setText(user.getEmail());
            patient_phone.setText(user.getPhone());
            date_of_birth.setValue(patient.getDate_of_birth().toLocalDate());
            gender.setValue(patient.getGender());
            address.setText(patient.getAddress());
            emergency_contact.setText(patient.getEmergency_contact());
            medical_history.setText(patient.getMedical_history());
        }
    }

    @FXML
    private void initialize() {
        patientDao = new PatientDao();
        userDao = new UserDao();
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String fname = first_name.getText();
        String lname = last_name.getText();
        String email = patient_email.getText();
        String phone = patient_phone.getText();
        Date dob = Date.valueOf(date_of_birth.getValue());
        String genderSelected = gender.getValue();
        String address = this.address.getText();
        String emergencyContact = this.emergency_contact.getText();
        String medicalHistory = this.medical_history.getText();

        patient.setFirst_name(fname);
        patient.setLast_name(lname);
        patient.setAddress(address);
        patient.setDate_of_birth(dob);
        patient.setEmergency_contact(emergencyContact);
        patient.setGender(genderSelected);
        patient.setMedical_history(medicalHistory);
        user.setPhone(phone);
        user.setEmail(email);

        patientDao.updatePatient(patient);
        userDao.updateUser(user);

        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
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

    @FXML
    public void validateUpdate(KeyEvent event) {
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
}
