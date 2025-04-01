CREATE TABLE IF NOT EXISTS room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(20) UNIQUE,
    room_type VARCHAR(50) COMMENT 'consultation, surgery, recovery,…',
    status VARCHAR(20) COMMENT 'available, occupied, maintenance'
);