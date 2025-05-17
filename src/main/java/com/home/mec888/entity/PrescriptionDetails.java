package com.home.mec888.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "prescription_details")
public class PrescriptionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "duration")
    private String duration;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    private boolean saved;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }


    public PrescriptionDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "PrescriptionDetails{" +
                "id=" + id +
                ", prescription=" + prescription +
                ", medicine=" + medicine +
                ", dosage='" + dosage + '\'' +
                ", amount=" + amount +
                ", duration='" + duration + '\'' +
                ", frequency='" + frequency + '\'' +
                ", instructions='" + instructions + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
