package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.MedicalRecordDao;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.MedicalRecord;
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
    private MedicalRecordDao medicalRecordDao;

    public static Appointment currentAppointment;
    public static Patient currentPatient;
    public static MedicalRecord currentMedicalRecord;
    public static boolean isMainDoctor = false;

    @FXML
    public void initialize() {
        currentActiveButton = patientProfileButton;
        medicalRecordDao = new MedicalRecordDao();
        Platform.runLater(() -> {
            handleMoveToPatientProfile(new ActionEvent(patientProfileButton, null));
            setAppointmentHeader();
            currentMedicalRecord = medicalRecordDao.getMedicalRecordByAppointment(currentAppointment);
        });
    }

    public void setAppointmentHeader() {
        if (currentAppointment != null) {
            String patientName = currentPatient.getUser().getFirstName();
            String patientAge;

            if (currentPatient.getUser().getDateOfBirth() != null) {
                patientAge = (LocalDate.now().getYear() - currentPatient.getUser().getDateOfBirth().getYear()) + " (" +
                        currentPatient.getUser().getDateOfBirth().getYear() + " years old)";
            } else {
                patientAge = "Chưa có thông tin";
            }

            String patientGender = currentPatient.getUser().getGender();
            String patientAddress = currentPatient.getUser().getAddress();
            generalInformation.setText(patientName + " | " + patientAge + " | " + patientGender);
            addressInformation.setText(patientAddress);
        }
    }

    public void setAppointment(Appointment appointment, boolean isMainDoctor) {
        try {
            currentAppointment = appointment;
            currentPatient = currentAppointment.getPatient();
            SeeADoctorContainerController.isMainDoctor = isMainDoctor;
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
        SceneSwitcher.loadViewSeeDoctor("initial_assessment.fxml", event);
    }

    public void handleMoveToPrescription(ActionEvent event) {
        highlightActiveButton(prescriptionButton);
        SceneSwitcher.loadViewSeeDoctor("prescription.fxml", event);
    }

    public void handleMoveToDiagnosticTest(ActionEvent event) {
        highlightActiveButton(diagnosticTestButton);
        SceneSwitcher.loadViewSeeDoctor("diagnostic_test.fxml", event);

    }

    public void handleMoveToTreatmentPlan(ActionEvent event) {
        highlightActiveButton(treatmentPlanButton);
        SceneSwitcher.loadViewSeeDoctor("treatment_plan.fxml", event);

    }
}
