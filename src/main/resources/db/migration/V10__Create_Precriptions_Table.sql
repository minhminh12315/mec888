CREATE TABLE IF NOT EXISTS prescriptions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    record_id INT,
    issued_date DATE,
    notes TEXT,
    CONSTRAINT fk_prescriptions_record FOREIGN KEY (record_id) REFERENCES medical_records(id)
);
