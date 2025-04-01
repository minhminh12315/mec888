CREATE TABLE IF NOT EXISTS feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    appointment_id INT,
    rating INT COMMENT 'điểm đánh giá, vd: 1-5',
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_feedbacks_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_feedbacks_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);