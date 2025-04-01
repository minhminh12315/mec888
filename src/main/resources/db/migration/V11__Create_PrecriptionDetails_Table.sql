CREATE TABLE IF NOT EXISTS prescription_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prescription_id INT,
    medicine_id INT,
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    duration VARCHAR(50),
    instructions TEXT,
    CONSTRAINT fk_prescription_details_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
    CONSTRAINT fk_prescription_details_medicine FOREIGN KEY (medicine_id) REFERENCES medicines(id)
);