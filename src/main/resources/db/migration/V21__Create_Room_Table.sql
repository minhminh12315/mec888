CREATE TABLE IF NOT EXISTS room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(20) UNIQUE,
    room_type VARCHAR(255) COMMENT 'consultation, surgery, recovery,…',
    status VARCHAR(20) COMMENT 'available, occupied, maintenance',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);