CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    room_id BIGINT,
    specialization_id BIGINT                        Comment 'Chuyên khoa',
    license_number VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_doctors_room FOREIGN KEY (room_id) REFERENCES room(id),
    CONSTRAINT fk_doctors_specialization FOREIGN KEY (specialization_id) REFERENCES specializations(id)
);