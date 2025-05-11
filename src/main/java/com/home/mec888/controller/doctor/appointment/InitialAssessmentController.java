package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.MedicalRecordDao;
import com.home.mec888.entity.MedicalRecord;
import com.home.mec888.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InitialAssessmentController {


    @FXML
    private TextField pulseRateField, temperatureField, bpSystolicField, bpDiastolicField, respiratoryRateField, weightField,
            treatmentSheetCountField, diseaseProgressField, examGeneralField, examSystemsField, labResultsField,
            primaryDiagnosisCodeField, primaryDiagnosisNoteField, secondaryDiagnosisCodeField, secondaryDiagnosisNoteField,
            diagnosisField, treatmentField, notesField, nutritionModeField, nutritionNoteField, careModeField, careNoteField;

    @FXML
    private Label pulse_rate_error, temperature_error, bp_systolic_error, bp_diastolic_error, respiratory_rate_error,
            weight_error, treatment_sheet_count_error, disease_progress_error, exam_general_error, exam_systems_error,
            lab_results_error, primary_diagnosis_code_error, primary_diagnosis_note_error, secondary_diagnosis_code_error,
            secondary_diagnosis_note_error, diagnosis_error, treatment_error, notes_error, nutrition_mode_error,
            nutrition_note_error, care_mode_error, care_note_error;

    @FXML
    public void handleSave(ActionEvent event) {
        // Clear previous error messages
        clearErrorMessages();

        try {
            // Validate fields
            Integer pulseRate = validateIntegerField(pulseRateField, pulse_rate_error, "Pulse Rate");
            Double temperature = validateDoubleField(temperatureField, temperature_error, "Temperature");
            Integer bpSystolic = validateIntegerField(bpSystolicField, bp_systolic_error, "BP Systolic");
            Integer bpDiastolic = validateIntegerField(bpDiastolicField, bp_diastolic_error, "BP Diastolic");
            Integer respiratoryRate = validateIntegerField(respiratoryRateField, respiratory_rate_error, "Respiratory Rate");
            Double weight = validateDoubleField(weightField, weight_error, "Weight");
            Integer treatmentSheetCount = validateIntegerField(treatmentSheetCountField, treatment_sheet_count_error, "Treatment Sheet Count");
            String diseaseProgress = validateTextField(diseaseProgressField, disease_progress_error, "Disease Progress");
            String examGeneral = validateTextField(examGeneralField, exam_general_error, "General Examination");
            String examSystems = validateTextField(examSystemsField, exam_systems_error, "Examination Systems");
            String labResults = validateTextField(labResultsField, lab_results_error, "Lab Results");
            String primaryDiagnosisCode = validateTextField(primaryDiagnosisCodeField, primary_diagnosis_code_error, "Primary Diagnosis Code");
            String primaryDiagnosisNote = validateTextField(primaryDiagnosisNoteField, primary_diagnosis_note_error, "Primary Diagnosis Note");
            String secondaryDiagnosisCode = validateTextField(secondaryDiagnosisCodeField, secondary_diagnosis_code_error, "Secondary Diagnosis Code");
            String secondaryDiagnosisNote = validateTextField(secondaryDiagnosisNoteField, secondary_diagnosis_note_error, "Secondary Diagnosis Note");
            String diagnosis = validateTextField(diagnosisField, diagnosis_error, "Diagnosis");
            String treatment = validateTextField(treatmentField, treatment_error, "Treatment");
            String notes = validateTextField(notesField, notes_error, "Notes");
            String nutritionMode = validateTextField(nutritionModeField, nutrition_mode_error, "Nutrition Mode");
            String nutritionNote = validateTextField(nutritionNoteField, nutrition_note_error, "Nutrition Note");
            String careMode = validateTextField(careModeField, care_mode_error, "Care Mode");
            String careNote = validateTextField(careNoteField, care_note_error, "Care Note");

            // Create MedicalRecordDao instance
            MedicalRecordDao medicalRecordDao = new MedicalRecordDao();

            // Check if a MedicalRecord already exists for the current appointment
            MedicalRecord existingRecord = medicalRecordDao.getMedicalRecordByAppointment(SeeADoctorContainerController.currentAppointment);

            if (existingRecord != null) {
                // Update the existing record
                existingRecord.setPulseRate(pulseRate);
                existingRecord.setTemperature(temperature);
                existingRecord.setBpSystolic(bpSystolic);
                existingRecord.setBpDiastolic(bpDiastolic);
                existingRecord.setRespirationRate(respiratoryRate);
                existingRecord.setWeight(weight);
                existingRecord.setTreatmentSheetCount(treatmentSheetCount);
                existingRecord.setDiseaseProgress(diseaseProgress);
                existingRecord.setExamGeneral(examGeneral);
                existingRecord.setExamSystems(examSystems);
                existingRecord.setLabResults(labResults);
                existingRecord.setPrimaryDiagnosisCode(primaryDiagnosisCode);
                existingRecord.setPrimaryDiagnosisNote(primaryDiagnosisNote);
                existingRecord.setSecondaryDiagnosisCode(secondaryDiagnosisCode);
                existingRecord.setSecondaryDiagnosisNote(secondaryDiagnosisNote);
                existingRecord.setDiagnosis(diagnosis);
                existingRecord.setTreatment(treatment);
                existingRecord.setNotes(notes);
                existingRecord.setNutritionMode(nutritionMode);
                existingRecord.setNutritionNote(nutritionNote);
                existingRecord.setCareMode(careMode);
                existingRecord.setCareNote(careNote);

                medicalRecordDao.updateMedicalRecord(existingRecord);
                System.out.println("Medical record updated successfully!");
            } else {
                // Create a new record
                MedicalRecord medicalRecord = new MedicalRecord(
                        SeeADoctorContainerController.currentPatient,
                        SeeADoctorContainerController.currentAppointment.getDoctor(),
                        SeeADoctorContainerController.currentAppointment,
                        pulseRate, temperature, bpSystolic, bpDiastolic, respiratoryRate, weight, treatmentSheetCount,
                        diseaseProgress, examGeneral, examSystems, labResults, primaryDiagnosisCode, primaryDiagnosisNote,
                        secondaryDiagnosisCode, secondaryDiagnosisNote, diagnosis, treatment, notes, nutritionMode,
                        nutritionNote, careMode, careNote
                );

                medicalRecordDao.saveMedicalRecord(medicalRecord);
                System.out.println("Medical record saved successfully!");
            }

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            System.err.println("Validation failed: " + e.getMessage());
        }
    }

    private void clearErrorMessages() {
        pulse_rate_error.setText("");
        temperature_error.setText("");
        bp_systolic_error.setText("");
        bp_diastolic_error.setText("");
        respiratory_rate_error.setText("");
        weight_error.setText("");
        treatment_sheet_count_error.setText("");
        disease_progress_error.setText("");
        exam_general_error.setText("");
        exam_systems_error.setText("");
        lab_results_error.setText("");
        primary_diagnosis_code_error.setText("");
        primary_diagnosis_note_error.setText("");
        secondary_diagnosis_code_error.setText("");
        secondary_diagnosis_note_error.setText("");
        diagnosis_error.setText("");
        treatment_error.setText("");
        notes_error.setText("");
        nutrition_mode_error.setText("");
        nutrition_note_error.setText("");
        care_mode_error.setText("");
        care_note_error.setText("");
    }

    private Integer validateIntegerField(TextField field, Label errorLabel, String fieldName) {
        try {
            String value = field.getText().trim();
            if (value.isEmpty()) return null; // Allow empty field
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            errorLabel.setText(fieldName + " must be a valid number.");
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    private Double validateDoubleField(TextField field, Label errorLabel, String fieldName) {
        try {
            String value = field.getText().trim();
            if (value.isEmpty()) return null; // Allow empty field
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            errorLabel.setText(fieldName + " must be a valid decimal number.");
            throw new IllegalArgumentException(fieldName + " must be a valid decimal number.");
        }
    }

    private String validateTextField(TextField field, Label errorLabel, String fieldName) {
        String value = field.getText().trim();
        if (value.isEmpty()) return null; // Allow empty field
        return value;
    }

    @FXML
    public void initialize() {
        loadExistingMedicalRecord();
    }

    private void loadExistingMedicalRecord() {
        MedicalRecordDao medicalRecordDao = new MedicalRecordDao();
        MedicalRecord existingRecord = medicalRecordDao.getMedicalRecordByAppointment(SeeADoctorContainerController.currentAppointment);

        if (existingRecord != null) {
            pulseRateField.setText(existingRecord.getPulseRate() != null ? existingRecord.getPulseRate().toString() : "");
            temperatureField.setText(existingRecord.getTemperature() != null ? existingRecord.getTemperature().toString() : "");
            bpSystolicField.setText(existingRecord.getBpSystolic() != null ? existingRecord.getBpSystolic().toString() : "");
            bpDiastolicField.setText(existingRecord.getBpDiastolic() != null ? existingRecord.getBpDiastolic().toString() : "");
            respiratoryRateField.setText(existingRecord.getRespirationRate() != null ? existingRecord.getRespirationRate().toString() : "");
            weightField.setText(existingRecord.getWeight() != null ? existingRecord.getWeight().toString() : "");
            treatmentSheetCountField.setText(existingRecord.getTreatmentSheetCount() != null ? existingRecord.getTreatmentSheetCount().toString() : "");
            diseaseProgressField.setText(existingRecord.getDiseaseProgress() != null ? existingRecord.getDiseaseProgress() : "");
            examGeneralField.setText(existingRecord.getExamGeneral() != null ? existingRecord.getExamGeneral() : "");
            examSystemsField.setText(existingRecord.getExamSystems() != null ? existingRecord.getExamSystems() : "");
            labResultsField.setText(existingRecord.getLabResults() != null ? existingRecord.getLabResults() : "");
            primaryDiagnosisCodeField.setText(existingRecord.getPrimaryDiagnosisCode() != null ? existingRecord.getPrimaryDiagnosisCode() : "");
            primaryDiagnosisNoteField.setText(existingRecord.getPrimaryDiagnosisNote() != null ? existingRecord.getPrimaryDiagnosisNote() : "");
            secondaryDiagnosisCodeField.setText(existingRecord.getSecondaryDiagnosisCode() != null ? existingRecord.getSecondaryDiagnosisCode() : "");
            secondaryDiagnosisNoteField.setText(existingRecord.getSecondaryDiagnosisNote() != null ? existingRecord.getSecondaryDiagnosisNote() : "");
            diagnosisField.setText(existingRecord.getDiagnosis() != null ? existingRecord.getDiagnosis() : "");
            treatmentField.setText(existingRecord.getTreatment() != null ? existingRecord.getTreatment() : "");
            notesField.setText(existingRecord.getNotes() != null ? existingRecord.getNotes() : "");
            nutritionModeField.setText(existingRecord.getNutritionMode() != null ? existingRecord.getNutritionMode() : "");
            nutritionNoteField.setText(existingRecord.getNutritionNote() != null ? existingRecord.getNutritionNote() : "");
            careModeField.setText(existingRecord.getCareMode() != null ? existingRecord.getCareMode() : "");
            careNoteField.setText(existingRecord.getCareNote() != null ? existingRecord.getCareNote() : "");
        }
    }

    public void getListServices() {

    }

    public void handleClear(ActionEvent event) {

    }

}
