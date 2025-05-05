package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Patient;
import com.home.mec888.util.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class SeeADoctorContainerController {
    @FXML
    public Button patientProfileButton;
    @FXML
    public Button initialAssessmentButton;
    @FXML
    public Button prescriptionButton;
    @FXML
    public Button diagnosticTestButton;
    @FXML
    public Button treatmentPlanButton;
    @FXML
    public Text generalInformation;
    @FXML
    public Text addressInformation;

    private Button currentActiveButton;

    public static Appointment currentAppointment;

    @FXML
    public void initialize() {
        currentActiveButton = patientProfileButton;
        Platform.runLater(() -> {
            handleMoveToPatientProfile(new ActionEvent(patientProfileButton, null));
        });
    }

    public void setAppointmentHeader() {
        if (currentAppointment != null) {
            Patient currentPatient = currentAppointment.getPatient();
            String patientName = currentPatient.getUser().getFirstName();
            String patientAge = currentPatient.getUser().getDateOfBirth().getYear() + "(" + (LocalDate.now().getYear() - currentPatient.getUser().getDateOfBirth().getYear()) + " years old)";
            String patientGender = currentPatient.getUser().getGender();
            String patientAddress = currentPatient.getUser().getAddress();
            generalInformation.setText(patientName + patientAge + patientGender);
            addressInformation.setText(patientAddress);
        }
    }

    public void setAppointment(Appointment appointment) {
        try {
            currentAppointment = appointment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void highlightActiveButton(Button button) {
        if (currentActiveButton != null) {
            // Xóa class "active-button" khỏi nút đang được chọn trước đó
            currentActiveButton.getStyleClass().remove("buttonSeeDoctorActive");
            currentActiveButton.getStyleClass().add("buttonSeeDoctor");
        }

        // Thêm class "buttonSeeDoctorActive" cho nút hiện tại
        button.getStyleClass().remove("buttonSeeDoctor");
        button.getStyleClass().add("buttonSeeDoctorActive");

        // Cập nhật nút hiện tại
        currentActiveButton = button;
    }

    public void handleMoveToPatientProfile(ActionEvent event) {
        highlightActiveButton(patientProfileButton);
        SceneSwitcher.loadViewSeeDoctor("patient-profile.fxml", event);
    }

    public void handleMoveToInitialAssessment(ActionEvent event) {
        highlightActiveButton(initialAssessmentButton);

    }

    public void handleMoveToPrescription(ActionEvent event) {
        highlightActiveButton(prescriptionButton);
        SceneSwitcher.loadViewSeeDoctor("prescription.fxml", event);


    }

    public void handleMoveToDiagnosticTest(ActionEvent event) {
        highlightActiveButton(diagnosticTestButton);

    }

    public void handleMoveToTreatmentPlan(ActionEvent event) {
        highlightActiveButton(treatmentPlanButton);

    }
}
