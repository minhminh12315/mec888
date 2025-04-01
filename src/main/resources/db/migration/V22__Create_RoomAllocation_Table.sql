CREATE TABLE IF NOT EXISTS room_allocation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT,
    room_id INT,
    allocated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_room_allocation_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    CONSTRAINT fk_room_allocation_room FOREIGN KEY (room_id) REFERENCES room(id)
);
