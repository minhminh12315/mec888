INSERT INTO mec888.roles
(id, name, description, created_at, updated_at)
VALUES(1, 'admin', 'manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'doctor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'staff', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'patient', 'patient', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.users
(id, username, password, email, phone, role_id, created_at, updated_at)
VALUES
(1, 'admin', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'admin', '0924600804', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'staff', '0334952821', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.medicines
(id, name, price, description, manufacturer, created_at, updated_at)
VALUES
(1, 'Paracetamol', 10.00, 'Pain reliever and fever reducer', 'Pharma Co.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Ibuprofen', 15.00, 'Anti-inflammatory medication', 'Health Inc.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Amoxicillin', 20.00, 'Antibiotic for bacterial infections', 'Medi Corp.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Aspirin', 5.00, 'Pain reliever and anti-inflammatory', 'Wellness Ltd.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Ciprofloxacin', 25.00, 'Antibiotic for urinary tract infections', 'Pharma Co.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Metformin', 30.00, 'Medication for type 2 diabetes', 'Health Inc.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Lisinopril', 35.00, 'Medication for high blood pressure', 'Medi Corp.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Simvastatin', 40.00, 'Cholesterol-lowering medication', 'Wellness Ltd.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Omeprazole', 45.00, 'Proton pump inhibitor for acid reflux', 'Pharma Co.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Levothyroxine', 50.00, 'Thyroid hormone replacement therapy', 'Health Inc.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.departments
(id, name, description, created_at, updated_at)
VALUES
(1, 'Cardiology', 'Heart and blood vessel health', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Neurology', 'Nervous system disorders', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Pediatrics', 'Child health and development', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Orthopedics', 'Musculoskeletal system health', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Dermatology', 'Skin health and diseases', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Gastroenterology', 'Digestive system health', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Endocrinology', 'Hormonal and metabolic disorders', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Psychiatry', 'Mental health and disorders', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Oncology', 'Cancer treatment and research', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Radiology', 'Medical imaging and diagnostics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


