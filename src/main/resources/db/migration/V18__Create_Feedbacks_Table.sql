CREATE TABLE IF NOT EXISTS feedbacks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    appointment_id BIGINT,
    rating INT COMMENT 'điểm đánh giá, vd: 1-5',
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_feedbacks_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_feedbacks_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);