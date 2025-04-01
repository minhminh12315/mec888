CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    appointment_id INT,
    amount DECIMAL(10,2),
    payment_method VARCHAR(20) COMMENT 'cash, card, transfer,...',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) COMMENT 'paid, pending, failed',
    CONSTRAINT fk_payments_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_payments_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
