CREATE TABLE IF NOT EXISTS invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    payment_id INT,
    invoice_number VARCHAR(50) UNIQUE,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    details TEXT,
    CONSTRAINT fk_invoices_payment FOREIGN KEY (payment_id) REFERENCES payments(id)
);
