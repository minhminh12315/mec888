CREATE TABLE IF NOT EXISTS medical_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT,
    doctor_id BIGINT,
    appointment_id BIGINT,
    diagnosis TEXT null,
    treatment TEXT null,
    notes TEXT null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_medrec_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_medrec_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_medrec_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);