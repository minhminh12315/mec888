CREATE TABLE IF NOT EXISTS transfer_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT, -- Foreign key to appointments table
    from_department_id BIGINT NULL, -- Foreign key to departments table
    to_department_id BIGINT NULL,
    from_doctor_id BIGINT NULL, -- Foreign key to doctors table
    to_doctor_id BIGINT NULL,
    from_room_id BIGINT NULL, -- Foreign key to rooms table
    to_room_id BIGINT NULL,
    transfer_date DATE,
    transfer_time TIME,
    reason TEXT NULL, -- Reason for transfer
    notes TEXT NULL, -- Additional notes
    status VARCHAR(20) COMMENT 'pending, completed, canceled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_transfer_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_transfer_from_department FOREIGN KEY (from_department_id) REFERENCES departments(id),
    CONSTRAINT fk_transfer_to_department FOREIGN KEY (to_department_id) REFERENCES departments(id),
    CONSTRAINT fk_transfer_from_doctor FOREIGN KEY (from_doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_transfer_to_doctor FOREIGN KEY (to_doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_transfer_from_room FOREIGN KEY (from_room_id) REFERENCES room(id),
    CONSTRAINT fk_transfer_to_room FOREIGN KEY (to_room_id) REFERENCES room(id)
);