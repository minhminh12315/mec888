CREATE TABLE IF NOT EXISTS treatment_steps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,  -- Liên kết ca khám
    doctor_id BIGINT NOT NULL,         -- Bác sĩ phụ trách bước này
    room_id BIGINT NOT NULL,           -- Phòng thực hiện bước này
    step_description TEXT,             -- Mô tả bước điều trị (ví dụ: "Khám ban đầu", "Phẫu thuật", "Hồi phục")
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP NULL,
    outcome TEXT,                      -- Kết quả bước điều trị (ví dụ: "Thành công", "Cần theo dõi thêm")
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_treatment_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_treatment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_treatment_room FOREIGN KEY (room_id) REFERENCES room(id)

);