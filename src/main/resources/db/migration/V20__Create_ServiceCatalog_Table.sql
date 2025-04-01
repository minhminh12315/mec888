CREATE TABLE IF NOT EXISTS service_catalog (
    id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2)
);
