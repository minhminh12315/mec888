package com.home.mec888.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // Vital signs
    @Column(name = "pulse_rate")
    private Integer pulseRate;

    @Column(name = "temperature", precision = 4, scale = 1)
    private Double temperature;

    @Column(name = "bp_systolic")
    private Integer bpSystolic;

    @Column(name = "bp_diastolic")
    private Integer bpDiastolic;

    @Column(name = "respiration_rate")
    private Integer respirationRate;

    @Column(name = "weight", precision = 5, scale = 2)
    private Double weight;

    // Treatment information
    @Column(name = "treatment_sheet_count")
    private Integer treatmentSheetCount;

    // Disease progress and examination
    @Column(name = "disease_progress")
    private String diseaseProgress;

    @Column(name = "exam_general")
    private String examGeneral;

    @Column(name = "exam_systems")
    private String examSystems;

    @Column(name = "lab_results")
    private String labResults;

    // Diagnosis
    @Column(name = "primary_diagnosis_code", length = 50)
    private String primaryDiagnosisCode;

    @Column(name = "primary_diagnosis_note")
    private String primaryDiagnosisNote;

    @Column(name = "secondary_diagnosis_code", length = 50)
    private String secondaryDiagnosisCode;

    @Column(name = "secondary_diagnosis_note")
    private String secondaryDiagnosisNote;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "notes")
    private String notes;

    // Nutrition and care
    @Column(name = "nutrition_mode", length = 100)
    private String nutritionMode;

    @Column(name = "nutrition_note")
    private String nutritionNote;

    @Column(name = "care_mode", length = 100)
    private String careMode;

    @Column(name = "care_note")
    private String careNote;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public MedicalRecord() {
    }

    public MedicalRecord(Patient patient, Doctor doctor, Appointment appointment, Integer pulseRate, Double temperature, Integer bpSystolic, Integer bpDiastolic, Integer respirationRate, Double weight, Integer treatmentSheetCount, String diseaseProgress, String examGeneral, String examSystems, String labResults, String primaryDiagnosisCode, String primaryDiagnosisNote, String secondaryDiagnosisCode, String secondaryDiagnosisNote, String diagnosis, String treatment, String notes, String nutritionMode, String nutritionNote, String careMode, String careNote) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
        this.pulseRate = pulseRate;
        this.temperature = temperature;
        this.bpSystolic = bpSystolic;
        this.bpDiastolic = bpDiastolic;
        this.respirationRate = respirationRate;
        this.weight = weight;
        this.treatmentSheetCount = treatmentSheetCount;
        this.diseaseProgress = diseaseProgress;
        this.examGeneral = examGeneral;
        this.examSystems = examSystems;
        this.labResults = labResults;
        this.primaryDiagnosisCode = primaryDiagnosisCode;
        this.primaryDiagnosisNote = primaryDiagnosisNote;
        this.secondaryDiagnosisCode = secondaryDiagnosisCode;
        this.secondaryDiagnosisNote = secondaryDiagnosisNote;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.nutritionMode = nutritionMode;
        this.nutritionNote = nutritionNote;
        this.careMode = careMode;
        this.careNote = careNote;
    }

    public MedicalRecord(Patient patient, Doctor doctor, Appointment appointment, String diagnosis, String treatment, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointment = appointment;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(Integer pulseRate) {
        this.pulseRate = pulseRate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getBpSystolic() {
        return bpSystolic;
    }

    public void setBpSystolic(Integer bpSystolic) {
        this.bpSystolic = bpSystolic;
    }

    public Integer getBpDiastolic() {
        return bpDiastolic;
    }

    public void setBpDiastolic(Integer bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }

    public Integer getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(Integer respirationRate) {
        this.respirationRate = respirationRate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getTreatmentSheetCount() {
        return treatmentSheetCount;
    }

    public void setTreatmentSheetCount(Integer treatmentSheetCount) {
        this.treatmentSheetCount = treatmentSheetCount;
    }

    public String getDiseaseProgress() {
        return diseaseProgress;
    }

    public void setDiseaseProgress(String diseaseProgress) {
        this.diseaseProgress = diseaseProgress;
    }

    public String getExamGeneral() {
        return examGeneral;
    }

    public void setExamGeneral(String examGeneral) {
        this.examGeneral = examGeneral;
    }

    public String getExamSystems() {
        return examSystems;
    }

    public void setExamSystems(String examSystems) {
        this.examSystems = examSystems;
    }

    public String getLabResults() {
        return labResults;
    }

    public void setLabResults(String labResults) {
        this.labResults = labResults;
    }

    public String getPrimaryDiagnosisCode() {
        return primaryDiagnosisCode;
    }

    public void setPrimaryDiagnosisCode(String primaryDiagnosisCode) {
        this.primaryDiagnosisCode = primaryDiagnosisCode;
    }

    public String getPrimaryDiagnosisNote() {
        return primaryDiagnosisNote;
    }

    public void setPrimaryDiagnosisNote(String primaryDiagnosisNote) {
        this.primaryDiagnosisNote = primaryDiagnosisNote;
    }

    public String getSecondaryDiagnosisCode() {
        return secondaryDiagnosisCode;
    }

    public void setSecondaryDiagnosisCode(String secondaryDiagnosisCode) {
        this.secondaryDiagnosisCode = secondaryDiagnosisCode;
    }

    public String getSecondaryDiagnosisNote() {
        return secondaryDiagnosisNote;
    }

    public void setSecondaryDiagnosisNote(String secondaryDiagnosisNote) {
        this.secondaryDiagnosisNote = secondaryDiagnosisNote;
    }

    public String getNutritionMode() {
        return nutritionMode;
    }

    public void setNutritionMode(String nutritionMode) {
        this.nutritionMode = nutritionMode;
    }

    public String getNutritionNote() {
        return nutritionNote;
    }

    public void setNutritionNote(String nutritionNote) {
        this.nutritionNote = nutritionNote;
    }

    public String getCareMode() {
        return careMode;
    }

    public void setCareMode(String careMode) {
        this.careMode = careMode;
    }

    public String getCareNote() {
        return careNote;
    }

    public void setCareNote(String careNote) {
        this.careNote = careNote;
    }
}