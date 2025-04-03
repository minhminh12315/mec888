CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT,
    appointment_id BIGINT,
    amount DECIMAL(10,2),
    payment_method VARCHAR(20) COMMENT 'cash, card, transfer,...',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) COMMENT 'paid, pending, failed',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_payments_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_payments_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
