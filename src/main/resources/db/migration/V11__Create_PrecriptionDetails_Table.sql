CREATE TABLE IF NOT EXISTS prescription_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prescription_id BIGINT,
    medicine_id BIGINT,
    dosage VARCHAR(255),
    frequency VARCHAR(255),
    duration VARCHAR(255),
    instructions TEXT,
    amount DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_details_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
    CONSTRAINT fk_prescription_details_medicine FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);