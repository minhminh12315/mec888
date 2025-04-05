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
    reason TEXT null, -- Reason for transfer
    notes TEXT null, -- Additional notes
    status VARCHAR(20) COMMENT 'pending, completed, canceled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);