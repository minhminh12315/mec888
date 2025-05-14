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
(10, 'Levothyroxine', 'Levothyroxine sodium', '50', 'mcg', 'Tablet', 'LEV-010', 'SL-010', 50.00, '2026-04-30', 'Take 1 tablet daily on an empty stomach, 30-60 minutes before breakfast.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
(1, 1, 'Monday', '08:00:00', '12:00:00', '2025-05-12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Tuesday', '13:00:00', '19:00:00', '2025-05-13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Wednesday', '08:00:00', '12:00:00', '2025-05-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 'Wednesday', '13:00:00', '19:00:00', '2025-05-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 3, 'Thursday', '08:00:00', '12:00:00', '2025-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 3, 'Thursday', '13:00:00', '19:00:00', '2025-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 4, 'Friday', '08:00:00', '12:00:00', '2025-05-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 4, 'Friday', '13:00:00', '19:00:00', '2025-05-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 5, 'Saturday', '08:00:00', '12:00:00', '2025-05-17', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 12. Appointments
INSERT INTO mec888.appointments
(id, patient_id, doctor_id, appointment_date, appointment_time, status, created_at, updated_at)
VALUES
(1, 1, 1, '2025-05-13', '14:00:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, '2025-05-14', '09:30:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 3, '2025-05-15', '16:00:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 4, '2025-05-16', '13:30:00', 'confirmed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 5, '2025-05-17', '10:00:00', 'pending', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 1, '2025-05-20', '15:30:00', 'pending', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 13. Treatment Steps
INSERT INTO mec888.treatment_steps
(id, appointment_id, doctor_id, step_description, start_time, end_time, outcome, created_at, updated_at)
VALUES
(1, 1, 1, 'Initial cardiac assessment', '2025-05-13 14:00:00', '2025-05-13 14:30:00', 'ECG shows normal rhythm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 5, 'Skin examination', '2025-05-13 14:45:00', '2025-05-13 15:15:00', 'No concerning skin lesions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 2, 'Neurological evaluation', '2025-05-14 09:30:00', '2025-05-14 10:15:00', 'Mild tension headaches', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 3, 'Pediatric checkup', '2025-05-15 16:00:00', '2025-05-15 16:45:00', 'Normal development for age', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 4, 'Cancer screening', '2025-05-16 13:30:00', '2025-05-16 14:30:00', 'No signs of malignancy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 14. Medical Records
INSERT INTO mec888.medical_records
(id, patient_id, doctor_id, appointment_id, diagnosis, treatment, notes, created_at, updated_at)
VALUES
(1, 1, 1, 1, 'Stable angina', 'Prescribed nitrates and follow-up monitoring', 'Patient to return in 3 months for follow-up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 2, 'Tension headaches', 'Stress management counseling, OTC pain relief', 'Patient advised on relaxation techniques', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 3, 3, 'Seasonal allergies', 'Antihistamines prescribed', 'Patient to avoid known allergens', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 4, 4, 'Routine check-up, no abnormalities', 'Maintain current diabetes medication', 'Blood glucose levels well controlled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 15. Prescriptions
INSERT INTO mec888.prescriptions
(id, record_id, issued_date, notes, created_at, updated_at)
VALUES
(1, 1, '2025-05-13', 'For angina management, patient education provided', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '2025-05-14', 'For headache management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, '2025-05-15', 'For allergy control, seasonal use', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, '2025-05-16', 'Continuation of current diabetes regimen', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 16. Prescription Details
INSERT INTO mec888.prescription_details
(id, prescription_id, medicine_id, dosage, frequency, duration, instructions, created_at, updated_at)
VALUES
(1, 1, 4, '75 mg', 'Once daily', '30 days', 'Take with breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 7, '10 mg', 'Once daily', '30 days', 'Take in the evening', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 1, '500 mg', 'As needed', '14 days', 'Take for headache pain, maximum 4 tablets daily', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 9, '20 mg', 'Once daily', '30 days', 'Take in the morning before breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 6, '850 mg', 'Twice daily', '90 days', 'Take with meals', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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