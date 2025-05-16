-- Consistently use schema prefix throughout
-- Standardize IDs for readability and maintenance
-- Group related data together

-- 1. Roles
INSERT INTO mec888.roles
(id, name, description, created_at, updated_at)
VALUES
(1, 'admin', 'Hospital administrator', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'Medical doctor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'Hospital staff', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'patient', 'Patient', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Departments (consolidated with specializations for consistency)
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

-- 3. Specializations (aligned with departments)
INSERT INTO mec888.specializations
(id, name)
VALUES
(1, 'Cardiology'),
(2, 'Neurology'),
(3, 'Pediatrics'),
(4, 'Dermatology'),
(5, 'Gastroenterology'),
(6, 'Oncology'),
(7, 'Orthopedics'),
(8, 'Psychiatry'),
(9, 'Ophthalmology'),
(10, 'Endocrinology');

-- 4. Rooms
INSERT INTO mec888.room
(id, department_id, room_number, room_type, status, created_at, updated_at)
VALUES
(1, 1, 'C101', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'S102', 'Surgery', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'R103', 'Recovery', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'C201', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 'S202', 'Surgery', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'C301', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 4, 'C401', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 5, 'C501', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 6, 'C601', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 7, 'C701', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Services
INSERT INTO mec888.services
(id, room_id, name, description, price, created_at, updated_at)
VALUES
(1, 1, 'General Consultation', 'Basic health consultation with a general practitioner', 300000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'X-Ray', 'Radiographic imaging for diagnostic purposes', 500000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'MRI Scan', 'Magnetic resonance imaging for detailed internal scans', 2000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'Blood Test', 'Comprehensive blood panel analysis', 150000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'Ultrasound', 'Diagnostic sonographic imaging', 700000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'Vaccination', 'Administration of vaccines for disease prevention', 250000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 3, 'Physical Therapy', 'Rehabilitation sessions for physical recovery', 1000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 2, 'Surgery Room Fee', 'Room charges for surgical operations', 5000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 3, 'Post-Operative Care', 'Recovery care services after surgery', 300000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 'Emergency Care', 'Immediate care services for critical conditions', 1000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 6. Room Service Relationships
INSERT INTO mec888.room_service
(id, room_id, service_id, created_at, updated_at)
VALUES
(1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 3, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 3, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 3, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 5, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 7, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 7. Medicines
INSERT INTO mec888.medicines
(id, name, active_ingredient, dosage, unit, form, manufacturer_code, sl_code, price, expiry_date, usage_instructions, created_at, updated_at)
VALUES
(1, 'Paracetamol', 'Acetaminophen', '500', 'mg', 'Tablet', 'PCM-001', 'SL-001', 10.00, '2025-12-31', 'Take 1-2 tablets every 4-6 hours as needed for pain or fever. Do not exceed 8 tablets in 24 hours.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Ibuprofen', 'Ibuprofen', '200', 'mg', 'Capsule', 'IBU-002', 'SL-002', 15.00, '2026-06-30', 'Take 1-2 capsules every 4-6 hours after food. Do not exceed 6 capsules in 24 hours.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Amoxicillin', 'Amoxicillin trihydrate', '250', 'mg', 'Capsule', 'AMX-003', 'SL-003', 20.00, '2025-10-15', 'Take 1 capsule three times daily with or without food. Complete the full course as prescribed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Aspirin', 'Acetylsalicylic acid', '75', 'mg', 'Tablet', 'ASP-004', 'SL-004', 5.00, '2026-03-20', 'Take 1 tablet daily with food. Use as directed by your physician.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Ciprofloxacin', 'Ciprofloxacin hydrochloride', '500', 'mg', 'Tablet', 'CIP-005', 'SL-005', 25.00, '2025-09-25', 'Take 1 tablet twice daily with plenty of water. Complete the full course as prescribed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Metformin', 'Metformin hydrochloride', '850', 'mg', 'Tablet', 'MET-006', 'SL-006', 30.00, '2026-02-10', 'Take 1 tablet twice daily with meals. Follow your doctor\'s instructions carefully.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Lisinopril', 'Lisinopril dihydrate', '10', 'mg', 'Tablet', 'LIS-007', 'SL-007', 35.00, '2025-11-05', 'Take 1 tablet once daily at the same time each day, with or without food.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Simvastatin', 'Simvastatin', '20', 'mg', 'Tablet', 'SIM-008', 'SL-008', 40.00, '2026-05-15', 'Take 1 tablet once daily in the evening. Avoid grapefruit juice while taking this medication.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Omeprazole', 'Omeprazole', '20', 'mg', 'Capsule', 'OME-009', 'SL-009', 45.00, '2025-08-20', 'Take 1 capsule once daily before breakfast. Swallow whole, do not crush or chew.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Levothyroxine', 'Levothyroxine sodium', '50', 'mcg', 'Tablet', 'LEV-010', 'SL-010', 50.00, '2026-04-30', 'Take 1 tablet daily on an empty stomach, 30-60 minutes before breakfast.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Antibiotics
(11, 'Azithromycin', 'Azithromycin dihydrate', '500', 'mg', 'Tablet', 'AZI-011', 'SL-011', 55.00, '2026-01-15', 'Take once daily for 3-5 days. Complete the full course as prescribed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Cefuroxime', 'Cefuroxime axetil', '250', 'mg', 'Tablet', 'CEF-012', 'SL-012', 60.00, '2025-12-10', 'Take twice daily with food. Complete full course of medication.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Anti-inflammatory
(13, 'Diclofenac', 'Diclofenac sodium', '50', 'mg', 'Tablet', 'DIC-013', 'SL-013', 35.00, '2026-03-25', 'Take with food to minimize stomach irritation. Do not exceed recommended dose.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Prednisone', 'Prednisone', '5', 'mg', 'Tablet', 'PRE-014', 'SL-014', 25.00, '2026-02-28', 'Take as directed, typically in the morning. Do not stop abruptly.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Cardiovascular
(15, 'Atorvastatin', 'Atorvastatin calcium', '20', 'mg', 'Tablet', 'ATO-015', 'SL-015', 65.00, '2026-05-20', 'Take once daily in the evening. Avoid grapefruit while taking this medication.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'Amlodipine', 'Amlodipine besylate', '5', 'mg', 'Tablet', 'AML-016', 'SL-016', 45.00, '2026-04-15', 'Take once daily with or without food. Take at the same time each day.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Respiratory
(17, 'Montelukast', 'Montelukast sodium', '10', 'mg', 'Tablet', 'MON-017', 'SL-017', 70.00, '2026-06-10', 'Take once daily in the evening for asthma or allergies.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'Salbutamol', 'Salbutamol sulfate', '2', 'mg', 'Tablet', 'SAL-018', 'SL-018', 30.00, '2025-11-20', 'Take as needed for breathlessness or wheezing. Do not exceed recommended dose.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Gastrointestinal
(19, 'Famotidine', 'Famotidine', '20', 'mg', 'Tablet', 'FAM-019', 'SL-019', 40.00, '2026-07-15', 'Take before meals and at bedtime for acid reduction.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'Domperidone', 'Domperidone maleate', '10', 'mg', 'Tablet', 'DOM-020', 'SL-020', 35.00, '2026-01-30', 'Take 15-30 minutes before meals and at bedtime if needed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Nervous System
(21, 'Amitriptyline', 'Amitriptyline hydrochloride', '25', 'mg', 'Tablet', 'AMI-021', 'SL-021', 40.00, '2026-08-10', 'Take in the evening or at bedtime. May cause drowsiness.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'Alprazolam', 'Alprazolam', '0.5', 'mg', 'Tablet', 'ALP-022', 'SL-022', 50.00, '2025-12-05', 'Take as directed. May cause drowsiness. Do not consume alcohol.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Antihistamines
(23, 'Cetirizine', 'Cetirizine hydrochloride', '10', 'mg', 'Tablet', 'CET-023', 'SL-023', 25.00, '2026-05-05', 'Take once daily for allergy symptoms. May cause drowsiness.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'Loratadine', 'Loratadine', '10', 'mg', 'Tablet', 'LOR-024', 'SL-024', 30.00, '2026-04-20', 'Take once daily on an empty stomach for allergy relief.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Topical medications
(25, 'Hydrocortisone Cream', 'Hydrocortisone', '1', '%', 'Cream', 'HYD-025', 'SL-025', 75.00, '2026-03-15', 'Apply a thin layer to affected area 2-3 times daily. Do not use on broken skin.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 8. Users (consolidated with consistent IDs)
INSERT INTO mec888.users
(id, username, first_name, last_name, address, password, email, phone, role_id, gender, date_of_birth, career, ethnicity, nationality, place_of_origin, created_at, updated_at)
VALUES
-- Admin
(1, 'admin', 'Minh', 'Do Dung Quang', 'Ba Dinh, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'admin@mec888.com', '0924600804', 1, 'Male', '1985-01-15', 'Administrator', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Staff
(2, 'staff1', 'Linh', 'Nguyen Thi', 'Hai Ba Trung, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'staff1@mec888.com', '0334952821', 3, 'Female', '1990-05-20', 'Staff', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Doctors
(11, 'dr_quang', 'Quang', 'Nguyen', 'Cau Giay, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'quang@hospital.com', '0911111111', 2, 'Male', '1980-03-10', 'Doctor', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'dr_hung', 'Hung', 'Tran', 'District 1, HCMC', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'hung@hospital.com', '0922222222', 2, 'Male', '1975-11-25', 'Doctor', 'Kinh', 'Vietnamese', 'Ho Chi Minh City', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'dr_dung', 'Dung', 'Le', 'Hai Chau, Da Nang', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'dung@hospital.com', '0933333333', 2, 'Female', '1982-07-15', 'Doctor', 'Kinh', 'Vietnamese', 'Da Nang', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'dr_mai', 'Mai', 'Pham', 'Hoan Kiem, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'mai@hospital.com', '0944444444', 2, 'Female', '1985-09-20', 'Doctor', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'dr_tuan', 'Tuan', 'Hoang', 'District 3, HCMC', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'tuan@hospital.com', '0955555555', 2, 'Male', '1978-12-05', 'Doctor', 'Kinh', 'Vietnamese', 'Ho Chi Minh City', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Patients
(101, 'patient1', 'XYZ', 'Nguyen', 'Minh Khai, Hoang Mai, Ha Noi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'xyz@patient.com', '0909999999', 4, 'Male', '2000-01-01', 'Patient', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 'patient2', 'Lan', 'Tran', 'Thanh Xuan, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'lan@patient.com', '0908888888', 4, 'Female', '1995-06-15', 'Teacher', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(103, 'patient3', 'Hoa', 'Le', 'Long Bien, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'hoa@patient.com', '0907777777', 4, 'Female', '1990-03-25', 'Engineer', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(104, 'patient4', 'Minh', 'Pham', 'Dong Da, Hanoi', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'minh@patient.com', '0906666666', 4, 'Male', '1985-09-10', 'Banker', 'Kinh', 'Vietnamese', 'Hanoi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 9. Doctors (specialization relationships)
INSERT INTO mec888.doctors
(id, user_id, room_id, specialization_id, license_number, created_at, updated_at)
VALUES
(1, 11, 1, 1, 'CARD001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Cardiologist
(2, 12, 4, 2, 'NEUR002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Neurologist
(3, 13, 6, 3, 'PEDI003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Pediatrician
(4, 14, 5, 6, 'ONCO004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Oncologist
(5, 15, 7, 4, 'DERM005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Dermatologist

-- 10. Patients (medical history)
INSERT INTO mec888.patients
(id, user_id, emergency_contact, medical_history, created_at, updated_at)
VALUES
(1, 101, 'Tran Van A - 0977777777', 'History of mild heart disease, hypertension', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 102, 'Nguyen Van B - 0988888888', 'No significant medical history', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 103, 'Pham Van C - 0999999999', 'Allergic to penicillin, asthma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 104, 'Le Van D - 0966666666', 'Type 2 diabetes, controlled with medication', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 11. Doctor Schedule
INSERT INTO mec888.doctor_schedule
(id, doctor_id, day_of_week, start_time, end_time, work_date, created_at, updated_at)
VALUES
(1, 1, 'Monday', '07:00:00', '12:00:00', '2025-05-12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Tuesday', '13:00:00', '19:00:00', '2025-05-13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Wednesday', '07:00:00', '12:00:00', '2025-05-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'Wednesday', '13:00:00', '19:00:00', '2025-05-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 'Thursday', '07:00:00', '12:00:00', '2025-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'Thursday', '13:00:00', '19:00:00', '2025-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 4, 'Friday', '07:00:00', '12:00:00', '2025-05-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 'Friday', '13:00:00', '19:00:00', '2025-05-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 5, 'Saturday', '07:00:00', '12:00:00', '2025-05-17', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 12. Appointments
INSERT INTO mec888.appointments
(id, patient_id, doctor_id, appointment_date, appointment_time, status, created_at, updated_at)
VALUES
-- Current appointments
(1, 3, 5, '2025-05-13', '14:00:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 1, '2025-05-14', '09:30:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, '2025-05-15', '16:00:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 3, '2025-05-16', '13:30:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical appointments for Patient 1
(7, 1, 1, '2025-01-15', '10:30:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 5, '2025-02-20', '11:15:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 3, '2025-03-10', '09:00:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 2, '2025-04-05', '14:30:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical appointments for Patient 2
(13, 4, 5, '2025-01-25', '13:45:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 4, 1, '2025-02-28', '09:15:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 4, 4, '2025-03-18', '16:00:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 4, 3, '2025-04-22', '11:30:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical appointments for Patient 3
(19, 2, 4, '2025-01-10', '15:15:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 2, 3, '2025-02-15', '10:45:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 2, 1, '2025-03-25', '13:30:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 2, 5, '2025-04-18', '09:45:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical appointments for Patient 4
(26, 3, 4, '2025-01-20', '14:15:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 3, 5, '2025-02-10', '11:45:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 3, 2, '2025-03-15', '15:30:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 3, 1, '2025-04-25', '10:00:00', 'completed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 13. Treatment Steps
INSERT INTO mec888.treatment_steps
(id, appointment_id, doctor_id, step_description, start_time, end_time, outcome, created_at, updated_at)
VALUES
(1, 1, 1, 'Initial cardiac assessment', '2025-05-13 14:00:00', '2025-05-13 14:30:00', 'ECG shows normal rhythm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 5, 'Skin examination', '2025-05-13 14:45:00', '2025-05-13 15:15:00', 'No concerning skin lesions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, 'Neurological evaluation', '2025-05-14 09:30:00', '2025-05-14 10:15:00', 'Mild tension headaches', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 3, 'Pediatric checkup', '2025-05-15 16:00:00', '2025-05-15 16:45:00', 'Normal development for age', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 4, 'Cancer screening', '2025-05-16 13:30:00', '2025-05-16 14:30:00', 'No signs of malignancy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 7, 1, 'General follow-up', '2025-01-15 10:30:00', '2025-01-15 11:00:00', 'Stable', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 8, 5, 'Dermatology follow-up', '2025-02-20 11:15:00', '2025-02-20 11:45:00', 'Improved skin condition', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 9, 3, 'Pediatric growth check', '2025-03-10 09:00:00', '2025-03-10 09:30:00', 'Normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 10, 2, 'Neurology review', '2025-04-05 14:30:00', '2025-04-05 15:00:00', 'No abnormalities', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 13, 5, 'Skin evaluation', '2025-01-25 13:45:00', '2025-01-25 14:15:00', 'No issues', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 14, 1, 'General consultation', '2025-02-28 09:15:00', '2025-02-28 09:45:00', 'Stable vitals', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 15, 4, 'Cancer screening', '2025-03-18 16:00:00', '2025-03-18 16:30:00', 'Negative', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 16, 3, 'Child health check', '2025-04-22 11:30:00', '2025-04-22 12:00:00', 'Healthy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 19, 4, 'Oncology follow-up', '2025-01-10 15:15:00', '2025-01-10 15:45:00', 'Clear', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 20, 3, 'Child fever review', '2025-02-15 10:45:00', '2025-02-15 11:15:00', 'Resolved', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 21, 1, 'Routine checkup', '2025-03-25 13:30:00', '2025-03-25 14:00:00', 'Normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 22, 5, 'Dermatitis treatment', '2025-04-18 09:45:00', '2025-04-18 10:15:00', 'Improving', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 26, 4, 'Cancer follow-up', '2025-01-20 14:15:00', '2025-01-20 14:45:00', 'No recurrence', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 27, 5, 'Skin rash assessment', '2025-02-10 11:45:00', '2025-02-10 12:15:00', 'Prescribed cream', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 28, 2, 'Headache consultation', '2025-03-15 15:30:00', '2025-03-15 16:00:00', 'Likely tension type', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 29, 1, 'Routine examination', '2025-04-25 10:00:00', '2025-04-25 10:30:00', 'All normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.treatment_steps_service (treatment_step_id, service_id, created_at, updated_at)
VALUES
-- Step 1: Initial cardiac assessment → General Consultation + Blood Test
(1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Step 2: Skin examination → General Consultation
(2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Step 3: Neurological evaluation → MRI Scan
(3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Step 4: Pediatric checkup → Vaccination
(4, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Step 5: Cancer screening → Ultrasound + Blood Test
(5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- General Consultation
(7, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Ultrasound
(8, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Vaccination
(9, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- MRI
(10, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Ultrasound
(11, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Blood Test
(13, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 14. Medical Records
INSERT INTO mec888.medical_records
(id, patient_id, doctor_id, appointment_id, diagnosis, treatment, notes, created_at, updated_at)
VALUES
-- Current medical records
(1, 3, 5, 1, 'Stable angina', 'Prescribed nitrates and follow-up monitoring', 'Patient to return in 3 months for follow-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 1, 2, 'Tension headaches', 'Stress management counseling, OTC pain relief', 'Patient advised on relaxation techniques', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 3, 'Seasonal allergies', 'Antihistamines prescribed', 'Patient to avoid known allergens', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 3, 4, 'Routine check-up, no abnormalities', 'Maintain current diabetes medication', 'Blood glucose levels well controlled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical medical records for Patient 1
(5, 1, 1, 7, 'Neurological assessment', 'Prescribed migraine medication, relaxation techniques', 'Patient to monitor headache triggers', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 5, 8, 'Pediatric check-up', 'Recommended dietary adjustments', 'All vitals within normal range', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1, 3, 9, 'Cardiac follow-up', 'Continued current medication, dosage adjusted', 'Symptoms improved since last visit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 2, 10, 'Oncology screening', 'No treatment needed', 'All tests negative, next screening in 1 year', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical medical records for Patient 2
(9, 4, 5, 13, 'Upper respiratory infection', 'Prescribed antibiotics', 'Follow up if symptoms persist', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 4, 1, 14, 'Oncology review', 'No treatment required', 'All tests negative', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 4, 4, 15, 'Neurological follow-up', 'Adjusted medication, recommended lifestyle changes', 'Patient showing improvement', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 4, 3, 16, 'Eczema', 'Prescribed topical corticosteroids', 'Patient to avoid triggers', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical medical records for Patient 3
(13, 2, 4, 19, 'Hypertension', 'Prescribed antihypertensive medication', 'Diet and exercise recommendations provided', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 2, 3, 20, 'Dizziness', 'Inner ear examination, balance tests', 'No serious concerns found', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 2, 1, 21, 'Psoriasis', 'Prescribed topical treatment', 'Patient responding well to treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2, 5, 22, 'Oncology screening', 'No abnormalities detected', 'Recommended annual follow-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical medical records for Patient 4
(17, 3, 4, 26, 'Contact dermatitis', 'Prescribed antihistamines and topical cream', 'Patient advised to identify and avoid allergens', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 3, 5, 27, 'Hypertension', 'Lifestyle modifications recommended', 'Blood pressure slightly elevated but manageable', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 3, 2, 28, 'Respiratory infection', 'Prescribed antibiotics', 'Patient advised to rest and increase fluid intake', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 3, 1, 29, 'Chronic back pain', 'Prescribed pain relievers and physical therapy', 'MRI showed mild disc degeneration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 15. Prescriptions
INSERT INTO mec888.prescriptions
(id, record_id, issued_date, notes, created_at, updated_at)
VALUES
-- Current prescriptions
(1, 1, '2025-05-13', 'For angina management, patient education provided', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '2025-05-14', 'For headache management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, '2025-05-15', 'For allergy control, seasonal use', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, '2025-05-16', 'Continuation of current diabetes regimen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Historical prescriptions (aligned with medical records)
(5, 5, '2025-01-15', 'For neurological assessment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 6, '2025-02-20', 'For pediatric check-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 7, '2025-03-10', 'For cardiac follow-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 8, '2025-04-05', 'For oncology screening', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 9, '2025-01-25', 'For respiratory infection treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 10, '2025-02-28', 'For oncology review', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 11, '2025-03-18', 'For neurological follow-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 12, '2025-04-22', 'For dermatology treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 13, '2025-01-10', 'For hypertension management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 14, '2025-02-15', 'For dizziness treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 15, '2025-03-25', 'For psoriasis treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 16, '2025-04-18', 'For oncology screening', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 17, '2025-01-20', 'For dermatitis treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 18, '2025-02-10', 'For hypertension management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 19, '2025-03-15', 'For respiratory infection treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 20, '2025-04-25', 'For chronic back pain management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 16. Prescription Details
INSERT INTO mec888.prescription_details
(id, prescription_id, medicine_id, dosage, frequency, duration, instructions, created_at, updated_at)
VALUES
(1, 4, 4, '75 mg', 'Once daily', '30 days', 'Take with breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 7, '10 mg', 'Once daily', '30 days', 'Take in the evening', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 1, '500 mg', 'As needed', '14 days', 'Take for headache pain, maximum 4 tablets daily', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 9, '20 mg', 'Once daily', '30 days', 'Take in the morning before breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 6, '850 mg', 'Twice daily', '90 days', 'Take with meals', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 1 (Angina management)
(6, 4, 8, '20 mg', 'Once daily', '30 days', 'Take in the evening with food to lower cholesterol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 3, 9, '20 mg', 'Once daily', '30 days', 'Take before breakfast for stomach protection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 2 (Headache management)
(8, 5, 2, '200 mg', 'Every 6 hours', '14 days', 'Take as needed for inflammation and pain relief', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2, 9, '20 mg', 'Once daily', '14 days', 'Take to protect stomach while on pain medication', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 3 (Allergy control)
(10, 1, 3, '250 mg', 'Twice daily', '7 days', 'Take if signs of secondary infection appear', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 1, '500 mg', 'As needed', '30 days', 'Take for fever or discomfort, maximum 4 daily', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 4 (Diabetes management)
(12, 2, 8, '20 mg', 'Once daily', '90 days', 'Take in the evening to manage cholesterol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 4, 7, '10 mg', 'Once daily', '90 days', 'Take in the morning to control blood pressure', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 5 (Neurological assessment)
(14, 5, 1, '500 mg', 'Every 6 hours', '14 days', 'Take for migraine pain as needed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3, 2, '200 mg', 'Every 8 hours', '14 days', 'Take for inflammation and pain', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 6 (Pediatric check-up)
(16, 6, 3, '250 mg', 'Three times daily', '10 days', 'Complete full course even if feeling better', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 8, 1, '250 mg', 'Every 6 hours', '10 days', 'Take for fever or discomfort', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 7 (Cardiac follow-up)
(18, 7, 4, '75 mg', 'Once daily', '90 days', 'Take with breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 9, 7, '10 mg', 'Once daily', '90 days', 'Take in the evening to control blood pressure', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 10, 9, '20 mg', 'Once daily', '90 days', 'Take for stomach protection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescription 8 (Oncology screening)
(21, 11, 1, '500 mg', 'As needed', '30 days', 'Take for discomfort after screening procedure', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 8, 9, '20 mg', 'Once daily', '14 days', 'Take for stomach protection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- For prescriptions 9-20 (Various medical conditions)
(23, 9, 3, '250 mg', 'Three times daily', '10 days', 'Take with food for respiratory infection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 12, 1, '500 mg', 'As needed', '7 days', 'Take for discomfort after screening procedure', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 13, 2, '200 mg', 'Every 6 hours', '30 days', 'Take for chronic headache management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 15, 5, '500 mg', 'Twice daily', '14 days', 'Take for skin infection treatment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 14, 7, '10 mg', 'Once daily', '90 days', 'Take for blood pressure management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 16, 1, '500 mg', 'Every 6 hours', '10 days', 'Take for dizziness-related discomfort', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 17, 5, '500 mg', 'Twice daily', '14 days', 'Take for psoriasis-related infection prevention', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 19, 1, '500 mg', 'As needed', '7 days', 'Take for post-screening discomfort', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 18, 2, '200 mg', 'Every 8 hours', '14 days', 'Take for skin inflammation', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 20, 7, '10 mg', 'Once daily', '90 days', 'Take for hypertension management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 13, 3, '250 mg', 'Three times daily', '10 days', 'Complete full course for respiratory infection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 12, 2, '200 mg', 'Every 6 hours', '30 days', 'Take for chronic back pain management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 14, 10, '50 mcg', 'Once daily', '90 days', 'Take for thyroid management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 1 (Angina management) - adding cardiovascular medications
(36, 5, 15, '20 mg', 'Once daily', '30 days', 'Take in the evening to manage cholesterol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 3, 16, '5 mg', 'Once daily', '30 days', 'Take in the morning to control blood pressure', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 2 (Headache management) - adding nervous system medications
(38, 2, 21, '25 mg', 'Once daily', '30 days', 'Take at bedtime for migraine prevention', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 7, 13, '50 mg', 'Twice daily', '14 days', 'Take with meals for pain relief', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 3 (Allergy control) - adding antihistamines and respiratory meds
(40, 8, 23, '10 mg', 'Once daily', '30 days', 'Take in the morning for daily allergy control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(41, 10, 17, '10 mg', 'Once daily', '30 days', 'Take in the evening to prevent nighttime symptoms', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(42, 11, 18, '2 mg', 'As needed', '30 days', 'Take only when experiencing breathing difficulties', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 4 (Diabetes management) - adding gastro protection
(43, 17, 19, '20 mg', 'Twice daily', '90 days', 'Take before meals to reduce acid production', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44, 19, 15, '20 mg', 'Once daily', '90 days', 'Take in the evening to control cholesterol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 5 (Neurological assessment) - adding anxiolytics
(45, 20, 22, '0.5 mg', 'Twice daily', '14 days', 'Take for acute anxiety, may cause drowsiness', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(46, 18, 21, '25 mg', 'Once daily', '30 days', 'Take at bedtime for migraine prevention', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 6 (Pediatric check-up) - adding antihistamine for child
(47, 6, 24, '5 mg', 'Once daily', '14 days', 'Half tablet for child under 12, for seasonal allergies', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 7 (Cardiac follow-up) - adding additional cardiac meds
(48, 7, 15, '20 mg', 'Once daily', '90 days', 'Take in the evening for cholesterol management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(49, 9, 16, '5 mg', 'Once daily', '90 days', 'Take in the morning for blood pressure control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 12 (Eczema treatment) - adding topical medication
(50, 12, 25, '1%', 'Three times daily', '14 days', 'Apply thin layer to affected areas, avoid face', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 15, 23, '10 mg', 'Once daily', '14 days', 'Take at night to control itching', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 13 (Hypertension) - complete cardiovascular regimen
(52, 13, 16, '5 mg', 'Once daily', '90 days', 'Take in the morning for blood pressure control', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 16, 4, '75 mg', 'Once daily', '90 days', 'Take with breakfast for cardiovascular protection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(54, 14, 15, '20 mg', 'Once daily', '90 days', 'Take in the evening for cholesterol management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 15 (Psoriasis) - advanced skin treatment
(55, 15, 25, '1%', 'Twice daily', '30 days', 'Apply to affected areas after bathing', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(56, 16, 14, '5 mg', 'Once daily', '14 days', 'Take in the morning, taper down over 2 weeks', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(57, 17, 23, '10 mg', 'Once daily', '30 days', 'Take at night to control itching', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 19 (Respiratory infection) - complete respiratory treatment
(58, 19, 11, '500 mg', 'Once daily', '5 days', 'Take for 5 full days to treat infection', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(59, 20, 18, '2 mg', 'As needed', '14 days', 'Take for shortness of breath, up to 4 times daily', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(60, 18, 17, '10 mg', 'Once daily', '14 days', 'Take in the evening to prevent night symptoms', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Enhanced prescription 20 (Chronic back pain) - comprehensive pain management
(61, 16, 13, '50 mg', 'Three times daily', '30 days', 'Take with meals for pain management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(62, 12, 21, '25 mg', 'Once daily', '30 days', 'Take at bedtime for pain and sleep', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(63, 11, 19, '20 mg', 'Twice daily', '30 days', 'Take before meals to protect stomach', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- 17. Payments
INSERT INTO mec888.payments
(id, patient_id, appointment_id, amount, payment_method, payment_date, status, created_at, updated_at)
VALUES
(1, 1, 1, 450000.00, 'card', '2025-05-13', 'paid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 300000.00, 'cash', '2025-05-14', 'paid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 3, 550000.00, 'insurance', '2025-05-15', 'paid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 4, 2150000.00, 'card', '2025-05-16', 'paid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 18. Invoices
INSERT INTO mec888.invoices
(id, payment_id, invoice_number, invoice_date, total_amount, details, created_at, updated_at)
VALUES
(1, 1, 'INV-20250513-001', '2025-05-13', 450000.00, 'Consultation fee, ECG, blood pressure monitoring, and prescription', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'INV-20250514-002', '2025-05-14', 300000.00, 'Neurological consultation and assessment', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'INV-20250515-003', '2025-05-15', 550000.00, 'Pediatric consultation, vaccination, and prescription', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 'INV-20250516-004', '2025-05-16', 2150000.00, 'Oncology consultation, MRI scan, and blood tests', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);