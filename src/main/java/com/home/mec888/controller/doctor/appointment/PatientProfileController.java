package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.entity.Patient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PatientProfileController {
    @FXML
    private Text fullname;
    @FXML
    private Text dob;
    @FXML
    private Text gender;
    @FXML
    private Text email;
    @FXML
    private Text phone;
    @FXML
    private Text address;
    @FXML
    private Text emergencyContact;
    @FXML
    private Text medicalHistory;
    @FXML
    private Text career;
    @FXML
    private Text ethnictiy; // Lưu ý: từ này viết sai, đúng là "ethnicity"
    @FXML
    private Text nationality;
    @FXML
    private Text placeOfOrigin;


    @FXML
    public void initialize() {
        setPatientInfo();
    }

    public void setPatientInfo() {
        Patient patientInfo = SeeADoctorContainerController.currentPatient;

        fullname.setText(patientInfo.getUser().getFirstName() + patientInfo.getUser().getLastName());
        dob.setText(patientInfo.getUser().getDateOfBirth().toString());
        gender.setText(patientInfo.getUser().getGender());
        email.setText(patientInfo.getUser().getEmail());
        phone.setText(patientInfo.getUser().getPhone());
        address.setText(patientInfo.getUser().getAddress());
        emergencyContact.setText(patientInfo.getEmergency_contact());
        medicalHistory.setText(patientInfo.getMedical_history());
        career.setText(patientInfo.getUser().getCareer());
        ethnictiy.setText(patientInfo.getUser().getEthnicity());
        nationality.setText(patientInfo.getUser().getNationality());
        placeOfOrigin.setText(patientInfo.getUser().getPlace_of_origin());
    }
}
