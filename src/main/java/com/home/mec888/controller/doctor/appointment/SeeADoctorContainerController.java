package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.entity.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    private Button currentActiveButton;

    public static Appointment currentAppointment;

    @FXML
    public void initialize() {
        currentActiveButton = patientProfileButton;
    }

    public void setAppointment(Appointment appointment) {
        try {
            System.out.println(appointment);
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

    }

    public void handleMoveToInitialAssessment(ActionEvent event) {
        highlightActiveButton(initialAssessmentButton);

    }

    public void handleMoveToPrescription(ActionEvent event) {
        highlightActiveButton(prescriptionButton);

    }

    public void handleMoveToDiagnosticTest(ActionEvent event) {
        highlightActiveButton(diagnosticTestButton);

    }

    public void handleMoveToTreatmentPlan(ActionEvent event) {
        highlightActiveButton(treatmentPlanButton);

    }
}
