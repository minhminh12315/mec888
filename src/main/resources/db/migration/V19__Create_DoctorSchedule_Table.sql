CREATE TABLE IF NOT EXISTS doctor_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT,
    day_of_week VARCHAR(20) COMMENT 'Monday, Tuesday,…',
    work_date DATE,
    start_time TIME,
    end_time TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_doctor_schedule_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);