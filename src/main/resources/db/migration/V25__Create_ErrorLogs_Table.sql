CREATE TABLE IF NOT EXISTS error_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    error_code VARCHAR(50),
    message TEXT,
    details TEXT,
    occurred_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);