INSERT INTO mec888.roles
(id, name, description, created_at, updated_at)
VALUES(1, 'admin', 'manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'doctor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'staff', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'patient', 'patient', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.users
(id, username, password, email, phone, role_id, created_at, updated_at)
VALUES
(1, 'admin', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'admin@mec888.com', '0924600804', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'staff', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'staff@mec888.com', '0334952821', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'doctor1', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor1@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'doctor2', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor2@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'doctor5', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor5@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'doctor6', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor6@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'doctor7', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor7@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'doctor8', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor8@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'doctor9', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor9@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'doctor10', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor10@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'doctor11', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor11@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'doctor12', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor12@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'doctor13', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor13@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'doctor14', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor14@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'doctor15', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor15@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'doctor16', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor16@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'doctor17', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor17@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'doctor18', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor18@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'doctor19', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor19@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'doctor20', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor20@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'doctor21', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor21@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'doctor22', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor22@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'doctor23', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor23@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'doctor24', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor24@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'doctor25', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor25@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'doctor26', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor26@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 'doctor27', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor27@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 'doctor28', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor28@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 'doctor29', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor29@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'doctor30', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor30@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 'doctor31', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor31@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 'doctor32', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor32@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 'doctor33', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor33@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 'doctor34', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor34@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 'doctor35', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor35@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(36, 'doctor36', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor36@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 'doctor37', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor37@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 'doctor38', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor38@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 'doctor39', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor39@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, 'doctor40', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor40@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(41, 'doctor41', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor41@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(42, 'doctor42', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor42@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(43, 'doctor43', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor43@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44, 'doctor44', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor44@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(45, 'doctor45', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor45@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(46, 'doctor46', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor46@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 'doctor47', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor47@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 'doctor48', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor48@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(49, 'doctor49', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor49@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(50, 'doctor50', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor50@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'doctor51', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor51@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(52, 'doctor52', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor52@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 'doctor53', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor53@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(54, 'doctor54', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor54@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 'doctor55', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor55@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(56, 'doctor56', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor56@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(57, 'doctor57', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor57@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(58, 'doctor58', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor58@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(59, 'doctor59', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor59@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(60, 'doctor60', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor60@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(61, 'doctor61', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor61@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(62, 'doctor62', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor62@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(63, 'doctor63', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor63@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(64, 'doctor64', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor64@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(65, 'doctor65', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor65@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 'doctor66', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor66@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(67, 'doctor67', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor67@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(68, 'doctor68', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor68@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(69, 'doctor69', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor69@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 'doctor70', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor70@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 'doctor71', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor71@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 'doctor72', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor72@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 'doctor73', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor73@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 'doctor74', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor74@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 'doctor75', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor75@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(76, 'doctor76', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor76@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(77, 'doctor77', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor77@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(78, 'doctor78', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor78@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(79, 'doctor79', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor79@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(80, 'doctor80', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor80@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 'doctor81', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor81@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 'doctor82', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor82@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(83, 'doctor83', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor83@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 'doctor84', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor84@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 'doctor85', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor85@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 'doctor86', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor86@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 'doctor87', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor87@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(88, 'doctor88', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor88@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(89, 'doctor89', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor89@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 'doctor90', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor90@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(91, 'doctor91', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor91@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(92, 'doctor92', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor92@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(93, 'doctor93', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor93@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(94, 'doctor94', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor94@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 'doctor95', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor95@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 'doctor96', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor96@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 'doctor97', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor97@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, 'doctor98', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor98@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 'doctor99', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor99@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, 'doctor100', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor100@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'patient', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'patient@gmail.com', '0926532450', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
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

INSERT INTO mec888.doctors
(id, user_id, department_id, specialization, license_number, created_at, updated_at)
values
(1, 2, 1, '123', 'GX0804PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 2, '123', 'GX0805PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.doctor_schedule
(id, doctor_id, day_of_week, start_time, end_time, created_at, updated_at)
values
(1, 1, 'Monday', '08:00:00', '12:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Wednesday', '14:00:00', '18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Friday', '09:00:00', '15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.patients
(id, user_id, emergency_contact, medical_history, created_at, updated_at)
VALUES(1, 101, '0123456789', 'khoe manh tu luc sinh ra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO mec888.appointments
(id, doctor_id, patient_id, appointment_date, status, created_at, updated_at)
VALUES
(1, 1, 1, '2023-10-01 10:00:00', 'scheduled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


