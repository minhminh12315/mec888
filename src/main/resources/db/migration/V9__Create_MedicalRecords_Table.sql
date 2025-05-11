CREATE TABLE IF NOT EXISTS medical_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT      NOT NULL,
    doctor_id BIGINT       NOT NULL,
    appointment_id BIGINT  NOT NULL,

    -- Dấu hiệu sinh tồn
    pulse_rate INT                         COMMENT 'Mạch (lần/phút)',
    temperature DECIMAL(4,1)               COMMENT 'Nhiệt độ (°C)',
    bp_systolic INT                        COMMENT 'Huyết áp tâm thu (mmHg)',
    bp_diastolic INT                       COMMENT 'Huyết áp tâm trương (mmHg)',
    respiration_rate INT                   COMMENT 'Nhịp thở (lần/phút)',
    weight DECIMAL(5,2)                    COMMENT 'Cân nặng (kg)',

    -- Thông tin điều trị
    treatment_sheet_count INT              COMMENT 'Số tờ điều trị',

    -- Diễn biến & khám
    disease_progress TEXT                  COMMENT 'Diễn biến bệnh',
    exam_general TEXT                      COMMENT 'Khám toàn thân',
    exam_systems TEXT                      COMMENT 'Khám bộ phận',
    lab_results TEXT                       COMMENT 'Kết quả CLS',

    -- Chẩn đoán chính & phụ
    primary_diagnosis_code VARCHAR(50)     COMMENT 'Mã chẩn đoán chính',
    primary_diagnosis_note TEXT            COMMENT 'Ghi chú chẩn đoán chính',
    secondary_diagnosis_code VARCHAR(50)   COMMENT 'Mã chẩn đoán phụ',
    secondary_diagnosis_note TEXT          COMMENT 'Ghi chú chẩn đoán phụ',

    -- Chẩn đoán tổng quát, xử lý, ghi chú
    diagnosis TEXT                         COMMENT 'Chẩn đoán',
    treatment TEXT                         COMMENT 'Xử lý / Phác đồ',
    notes TEXT                             COMMENT 'Ghi chú chung',

    -- Chế độ dinh dưỡng & chăm sóc
    nutrition_mode VARCHAR(100)            COMMENT 'Chế độ dinh dưỡng',
    nutrition_note TEXT                    COMMENT 'Ghi chú dinh dưỡng',
    care_mode VARCHAR(100)                 COMMENT 'Chế độ chăm sóc',
    care_note TEXT                         COMMENT 'Ghi chú chăm sóc',

    created_at TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_medrec_patient     FOREIGN KEY (patient_id)    REFERENCES patients(id),
    CONSTRAINT fk_medrec_doctor      FOREIGN KEY (doctor_id)     REFERENCES doctors(id),
    CONSTRAINT fk_medrec_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);