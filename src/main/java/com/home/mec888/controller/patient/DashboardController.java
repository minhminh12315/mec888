package com.home.mec888.controller.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;
    @FXML private Label dobLabel;
    @FXML private Label addressLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label nationalityLabel;
    @FXML private Label emergencyContactLabel;
    @FXML private Label medicalHistoryLabel;
    @FXML private Label labelError;

    private PatientDao patientDao;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientDao = new PatientDao();
        User user = UserSession.getInstance().getUser();
        if (user == null) {
            labelError.setText("Phiên người dùng không hợp lệ.");
            return;
        }

        Patient patient = patientDao.findPatientByUserId(user.getId());
        if (patient == null) {
            labelError.setText("Không tìm thấy dữ liệu bệnh nhân.");
            return;
        }

        // Fill user labels
        firstNameLabel.setText(safe(user.getFirstName()));
        lastNameLabel.setText(safe(user.getLastName()));
        genderLabel.setText(safe(user.getGender()));
        dobLabel.setText(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "N/A");
        addressLabel.setText(safe(user.getAddress()));
        emailLabel.setText(safe(user.getEmail()));
        phoneLabel.setText(safe(user.getPhone()));
        nationalityLabel.setText(safe(user.getNationality()));

        // Fill patient labels
        emergencyContactLabel.setText(safe(patient.getEmergency_contact()));
        medicalHistoryLabel.setText(safe(patient.getMedical_history()));
    }

    private String safe(String value) {
        return value != null ? value : "N/A";
    }
}