CREATE TABLE IF NOT EXISTS medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active_ingredient VARCHAR(255),
    dosage VARCHAR(100),
    unit VARCHAR(50),
    form VARCHAR(50),
    manufacturer_code VARCHAR(50),
    sl_code VARCHAR(20),
    price DECIMAL(10,2),
    expiry_date DATE,
    usage_instructions TEXT, -- description
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);