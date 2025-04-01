CREATE TABLE IF NOT EXISTS prescription_template (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    content TEXT
);