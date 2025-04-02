INSERT INTO mec888.roles
(id, name, description, created_at, updated_at)
VALUES(1, 'admin', 'manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'doctor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'staff', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'patient', 'patient', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.users
(id, username, password, email, phone, role_id, created_at, updated_at)
VALUES
(1, 'admin', 'admin', 'admin', '0924600804', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'doctor', 'doctor', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'staff', 'staff', '0334952821', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);