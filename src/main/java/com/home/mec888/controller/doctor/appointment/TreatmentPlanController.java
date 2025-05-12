package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.*;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.MedicalRecord;
import com.home.mec888.util.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Date;

public class TreatmentPlanController {
    @FXML
    public VBox treatmentPlanVBoxContainer;
    @FXML
    public TextField primary_diagnosis;
    @FXML
    public TextField secondary_diagnosis;
    @FXML
    public ComboBox<String> priority;
    @FXML
    public TextArea plan_details;
    @FXML
    public TextArea treatment_method;
    @FXML
    public TextArea patient_health_status;
    @FXML
    public TextField expected_duration;
    @FXML
    public DatePicker follow_up_date;
    @FXML
    public TextArea note;

    ServiceDao serviceDao;
    TreatmentStepServiceDao treatmentStepServiceDao;
    TreatmentStepDao treatmentStepDao;
    MedicalRecordDao medicalRecordDao;
    AppointmentDao appointmentDao;
    @FXML
    public void initialize() {
        serviceDao = new ServiceDao();
        treatmentStepServiceDao = new TreatmentStepServiceDao();
        treatmentStepDao = new TreatmentStepDao();
        medicalRecordDao = new MedicalRecordDao();
        appointmentDao = new AppointmentDao();
        Platform.runLater(() -> {
            if (SeeADoctorContainerController.currentMedicalRecord == null) {
                treatmentPlanVBoxContainer.setDisable(true);
            } else {
                treatmentPlanVBoxContainer.setDisable(false);
            }
        });
    }

    public void handleClearTreatmentPlan() {
        primary_diagnosis.clear();
        secondary_diagnosis.clear();
        priority.setValue(null);
        plan_details.clear();
        treatment_method.clear();
        patient_health_status.clear();
        expected_duration.clear();
        follow_up_date.setValue(null);
        note.clear();
    }

    public void handleSaveTreatmentPlan(ActionEvent event) {
        String primaryDiagnosis = primary_diagnosis.getText();
        String secondaryDiagnosis = secondary_diagnosis.getText();
        String priorityValue = priority.getSelectionModel().getSelectedItem();
        String planDetails = plan_details.getText();
        String treatmentMethod = treatment_method.getText();
        String patientHealthStatus = patient_health_status.getText();
        String expectedDuration = expected_duration.getText();
        String followUpDate = follow_up_date.getValue() != null ? follow_up_date.getValue().toString() : null;
        String noteValue = note.getText();

        //sout all field
//        System.out.println("Primary Diagnosis: " + primaryDiagnosis);
//        System.out.println("Secondary Diagnosis: " + secondaryDiagnosis);
//        System.out.println("Priority: " + priorityValue);
//        System.out.println("Plan Details: " + planDetails);
//        System.out.println("Treatment Method: " + treatmentMethod);
//        System.out.println("Patient Health Status: " + patientHealthStatus);
//        System.out.println("Expected Duration: " + expectedDuration);
//        System.out.println("Follow Up Date: " + followUpDate);
//        System.out.println("Note: " + noteValue);

        MedicalRecord currentMedicalRecord = SeeADoctorContainerController.currentMedicalRecord;
        if(primaryDiagnosis != null){
            currentMedicalRecord.setPrimaryDiagnosis(primaryDiagnosis);
        }
        if(secondaryDiagnosis != null){
            currentMedicalRecord.setSecondaryDiagnosis(secondaryDiagnosis);
        }
        if(priorityValue != null){
            currentMedicalRecord.setPriority(priorityValue);
        }
        if(planDetails != null){
            currentMedicalRecord.setPlanDetails(planDetails);
        }
        if(treatmentMethod != null){
            currentMedicalRecord.setTreatmentMethod(treatmentMethod);
        }
        if(patientHealthStatus != null){
            currentMedicalRecord.setPatientHealthStatus(patientHealthStatus);
        }
        if(expectedDuration != null){
            currentMedicalRecord.setExpectedDuration(Integer.valueOf(expectedDuration));
        }
        if(followUpDate != null){
            currentMedicalRecord.setFollowUpDate(Date.valueOf(followUpDate));
        }
        if(noteValue != null){
            currentMedicalRecord.setDoctorNotes(noteValue);
        }

        medicalRecordDao.updateMedicalRecord(currentMedicalRecord);

        Appointment currentAppointment = SeeADoctorContainerController.currentAppointment;
        if(currentAppointment != null){
            currentAppointment.setStatus("completed");
            appointmentDao.updateAppointment(currentAppointment);
        }

        SeeADoctorContainerController.currentAppointment = null;
        SeeADoctorContainerController.currentPatient = null;
        SeeADoctorContainerController.currentMedicalRecord = null;
        SeeADoctorContainerController.isMainDoctor = false;

        SceneSwitcher.loadView("doctor/appointment/list-appointment.fxml", event);
    }
}
