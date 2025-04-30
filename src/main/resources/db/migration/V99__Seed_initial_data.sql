-- 1.Role
INSERT INTO mec888.roles
(id, name, description, created_at, updated_at)
VALUES(1, 'admin', 'manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'doctor', 'doctor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'staff', 'staff', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'patient', 'patient', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2.User
INSERT INTO mec888.users
(id, username,first_name, last_name, address, password, email, phone, role_id, created_at, updated_at)
VALUES
(1, 'admin','Do', 'Dung Quang Minh', 'Ba Dinh',  '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'admin@mec888.com', '0924600804', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'staff','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'staff@mec888.com', '0334952821', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'doctor1','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor1@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'doctor2','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor2@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'doctor5','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor5@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'doctor6','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor6@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'doctor7','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor7@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'doctor8','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor8@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'doctor9','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor9@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'doctor10','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor10@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'doctor11','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor11@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'doctor12','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor12@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'doctor13','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor13@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'doctor14','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor14@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'doctor15','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor15@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'doctor16','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor16@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'doctor17','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor17@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'doctor18','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor18@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'doctor19','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor19@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'doctor20','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor20@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'doctor21','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor21@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'doctor22','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor22@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'doctor23','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor23@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'doctor24','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor24@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'doctor25','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor25@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'doctor26','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor26@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 'doctor27','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor27@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 'doctor28','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor28@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 'doctor29','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor29@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'doctor30','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor30@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 'doctor31','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor31@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 'doctor32','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor32@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 'doctor33','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor33@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 'doctor34','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor34@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 'doctor35','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor35@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(36, 'doctor36','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor36@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 'doctor37','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor37@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 'doctor38','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor38@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 'doctor39','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor39@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, 'doctor40','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor40@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(41, 'doctor41','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor41@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(42, 'doctor42','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor42@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(43, 'doctor43','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor43@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(44, 'doctor44','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor44@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(45, 'doctor45','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor45@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(46, 'doctor46','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor46@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(47, 'doctor47','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor47@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(48, 'doctor48','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor48@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(49, 'doctor49','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor49@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(50, 'doctor50','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor50@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(51, 'doctor51','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor51@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(52, 'doctor52','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor52@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(53, 'doctor53','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor53@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(54, 'doctor54','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor54@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(55, 'doctor55','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor55@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(56, 'doctor56','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor56@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(57, 'doctor57','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor57@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(58, 'doctor58','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor58@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(59, 'doctor59','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor59@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(60, 'doctor60','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor60@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(61, 'doctor61','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor61@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(62, 'doctor62','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor62@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(63, 'doctor63','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor63@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(64, 'doctor64','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor64@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(65, 'doctor65','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor65@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(66, 'doctor66','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor66@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(67, 'doctor67','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor67@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(68, 'doctor68','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor68@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(69, 'doctor69','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor69@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(70, 'doctor70','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor70@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(71, 'doctor71','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor71@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(72, 'doctor72','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor72@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(73, 'doctor73','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor73@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(74, 'doctor74','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor74@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(75, 'doctor75','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor75@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(76, 'doctor76','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor76@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(77, 'doctor77','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor77@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(78, 'doctor78','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor78@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(79, 'doctor79','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor79@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(80, 'doctor80','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor80@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(81, 'doctor81','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor81@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(82, 'doctor82','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor82@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(83, 'doctor83','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor83@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(84, 'doctor84','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor84@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(85, 'doctor85','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor85@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(86, 'doctor86','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor86@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(87, 'doctor87','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor87@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(88, 'doctor88','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor88@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(89, 'doctor89','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor89@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(90, 'doctor90','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor90@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(91, 'doctor91','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor91@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(92, 'doctor92','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor92@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(93, 'doctor93','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor93@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(94, 'doctor94','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor94@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(95, 'doctor95','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor95@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(96, 'doctor96','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor96@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(97, 'doctor97','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor97@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(98, 'doctor98','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor98@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(99, 'doctor99','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor99@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(100, 'doctor100','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'doctor100@mec888.com', '0926532450', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'patient','Do', 'Dung Quang Minh', 'Ba Dinh', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'qunnguyn956@gmail.com', '0926532450', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users
  (id, username, password, email, phone, first_name, last_name, role_id, created_at, updated_at)
VALUES
  (2001, 'dr_quang', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO',
    'quang@hospital.com', '0911111111',
    'Quang', 'Nguyễn', 2, NOW(), NOW()),
  (2002, 'dr_hung', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO',
    'hung@hospital.com', '0922222222',
    'Hưng', 'Trần', 2, NOW(), NOW()),
  (2003, 'dr_dung', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO',
    'dung@hospital.com', '0933333333',
    'Dung', 'Lê', 2, NOW(), NOW());
-- 3. Patient (nếu chưa có)
INSERT INTO users (id, username, password, email, phone, role_id, created_at, updated_at)
VALUES (3001, 'patient_xyz', '$2a$12$dOzJR0qR6YyhIGdJ/gHa7eDGF/twfro05rPysAviDfTFrhTGw4AtO', 'xyz@patient.com', '0909999999', 4, NOW(), NOW());

-- Medicine
INSERT INTO mec888.medicines
(id, name, active_ingredient, dosage, unit, form, manufacturer_code, sl_code, price, warehouse, expiry_date, usage_instructions, created_at, updated_at)
VALUES
(1, 'Paracetamol', 'Acetaminophen', '500', 'mg', 'Tablet', 'PCM-001', 'SL-001', 10.00, 'Main Warehouse', '2025-12-31', 'Take 1-2 tablets every 4-6 hours as needed for pain or fever. Do not exceed 8 tablets in 24 hours.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Ibuprofen', 'Ibuprofen', '200', 'mg', 'Capsule', 'IBU-002', 'SL-002', 15.00, 'Main Warehouse', '2026-06-30', 'Take 1-2 capsules every 4-6 hours after food. Do not exceed 6 capsules in 24 hours.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Amoxicillin', 'Amoxicillin trihydrate', '250', 'mg', 'Capsule', 'AMX-003', 'SL-003', 20.00, 'Temperature Controlled', '2025-10-15', 'Take 1 capsule three times daily with or without food. Complete the full course as prescribed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Aspirin', 'Acetylsalicylic acid', '75', 'mg', 'Tablet', 'ASP-004', 'SL-004', 5.00, 'Main Warehouse', '2026-03-20', 'Take 1 tablet daily with food. Use as directed by your physician.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Ciprofloxacin', 'Ciprofloxacin hydrochloride', '500', 'mg', 'Tablet', 'CIP-005', 'SL-005', 25.00, 'Temperature Controlled', '2025-09-25', 'Take 1 tablet twice daily with plenty of water. Complete the full course as prescribed.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Metformin', 'Metformin hydrochloride', '850', 'mg', 'Tablet', 'MET-006', 'SL-006', 30.00, 'Main Warehouse', '2026-02-10', 'Take 1 tablet twice daily with meals. Follow your doctor\'s instructions carefully.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Lisinopril', 'Lisinopril dihydrate', '10', 'mg', 'Tablet', 'LIS-007', 'SL-007', 35.00, 'Main Warehouse', '2025-11-05', 'Take 1 tablet once daily at the same time each day, with or without food.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Simvastatin', 'Simvastatin', '20', 'mg', 'Tablet', 'SIM-008', 'SL-008', 40.00, 'Temperature Controlled', '2026-05-15', 'Take 1 tablet once daily in the evening. Avoid grapefruit juice while taking this medication.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'Omeprazole', 'Omeprazole', '20', 'mg', 'Capsule', 'OME-009', 'SL-009', 45.00, 'Main Warehouse', '2025-08-20', 'Take 1 capsule once daily before breakfast. Swallow whole, do not crush or chew.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Levothyroxine', 'Levothyroxine sodium', '50', 'mcg', 'Tablet', 'LEV-010', 'SL-010', 50.00, 'Temperature Controlled', '2026-04-30', 'Take 1 tablet daily on an empty stomach, 30-60 minutes before breakfast.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Departments
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

-- 5. Room
INSERT INTO mec888.room (id,department_id, room_number, room_type, status, created_at, updated_at)
VALUES
    (1, 1, 'R101', 'Consultation', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'R202', 'Surgery', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 'R303', 'Recovery', 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5.1 Spectialization
INSERT INTO mec888.specializations (id, name) VALUES
  (1, 'Cardiology'),
  (2, 'Neurology'),
  (3, 'Pediatrics'),
  (4, 'Dermatology'),
  (5, 'Gastroenterology'),
  (6, 'Oncology'),
  (7, 'Orthopedics'),
  (8, 'Psychiatry'),
  (9, 'Ophthalmology'),
  (10,'Endocrinology');

-- 5. Doctor
INSERT INTO mec888.doctors
  (id, user_id, room_id, specialization_id, license_number, created_at, updated_at)
VALUES
  (1,  2,  1,  1,  'GX0804PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Cardiology
  (2,  4,  2,  2,  'GX0805PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Neurology
  (3,  5,  3,  3,  'GX0806PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Pediatrics
  (4,  6,  3,  4,  'GX0807PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Dermatology
  (5,  7,  3,  5,  'GX0808PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Gastroenterology
  (6,  8,  1,  6,  'GX0809PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Oncology
  (7,  9,  2,  7,  'GX0810PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Orthopedics
  (8, 10,  2,  8,  'GX0811PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Psychiatry
  (9, 11,  1,  9,  'GX0812PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- Ophthalmology
  (10,12,  1, 10,  'GX0813PT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- Endocrinology

-- 2. Bác sĩ tương ứng
INSERT INTO doctors (id, user_id, room_id, specialization_id, license_number, created_at, updated_at)
VALUES
(11, 2001, 1, 1, 'INT001', NOW(), NOW()),
(12, 2002, 3, 2, 'SUR002', NOW(), NOW()),
(13, 2003, 2, 3, 'REC003', NOW(), NOW());

-- 6. Doctor Schedule
INSERT INTO mec888.doctor_schedule
(id, doctor_id, day_of_week, start_time, end_time, work_date, created_at, updated_at)
VALUES
(1, 1, 'Monday', '07:00:00', '13:00:00', '2025-04-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'Wednesday', '13:00:00', '19:00:00', '2025-04-16', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 'Friday', '07:00:00', '13:00:00', '2025-04-11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 'Tuesday', '13:00:00', '19:00:00', '2025-04-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 4, 'Thursday', '07:00:00', '13:00:00', '2025-04-17', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 5, 'Saturday', '13:00:00', '19:00:00', '2025-04-19', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 6, 'Sunday', '07:00:00', '13:00:00', '2025-04-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 7, 'Monday', '13:00:00', '19:00:00', '2025-04-21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 8, 'Wednesday', '07:00:00', '13:00:00', '2025-04-23', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9, 'Friday', '13:00:00', '19:00:00', '2025-04-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 7. Patient
INSERT INTO mec888.patients
(id, user_id, emergency_contact, medical_history, created_at, updated_at)
VALUES(1, 101, '0123456789', 'khoe manh tu luc sinh ra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO patients (id, user_id, emergency_contact, medical_history, created_at, updated_at)
VALUES (401, 3001, 'Trần Văn B - 0977777777', 'Tiền sử đau tim nhẹ', NOW(), NOW());


-- 4. Appointment
INSERT INTO mec888.appointments
(id, doctor_id, patient_id, appointment_date, status, created_at, updated_at)
VALUES
(1, 1, 1, '2023-10-01 10:00:00', 'scheduled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, status, created_at, updated_at)
VALUES (501, 401, 11, '2025-04-06', '08:00:00', 'confirmed', NOW(), NOW());



-- 6. Treatment Steps (phân chia bác sĩ)
INSERT INTO treatment_steps (id, appointment_id, doctor_id, room_id, step_description, start_time, end_time, outcome, created_at, updated_at)
VALUES
(601, 501, 1, 1, 'Khám tổng quát ban đầu', '2025-04-06 08:00:00', '2025-04-06 08:30:00', 'Chỉ định phẫu thuật', NOW(), NOW()),
(602, 501, 2, 2, 'Phẫu thuật can thiệp', '2025-04-06 09:00:00', '2025-04-06 10:30:00', 'Phẫu thuật thành công', NOW(), NOW()),
(603, 501, 3, 3, 'Theo dõi hồi sức sau phẫu thuật', '2025-04-06 11:00:00', '2025-04-07 09:00:00', 'Ổn định, xuất viện', NOW(), NOW());

-- 7. Medical Record
INSERT INTO medical_records (id, patient_id, doctor_id, appointment_id, diagnosis, treatment, notes, created_at, updated_at)
VALUES (701, 401, 11, 501, 'Đau thắt ngực do hẹp mạch vành', 'Phẫu thuật, theo dõi hậu phẫu', 'Bệnh nhân hồi phục tốt', NOW(), NOW());

-- 8. INSERT dữ liệu cho bảng prescriptions
-- Giả sử ca khám có hồ sơ bệnh án với id = 501
INSERT INTO mec888.prescriptions
    (id, record_id, issued_date, notes, created_at, updated_at)
VALUES
    (701, 701, '2025-04-01', 'Prescription issued after consultation and surgery.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 9. INSERT dữ liệu cho bảng prescription_details
-- Chúng ta thêm 3 chi tiết đơn thuốc cho prescription id = 701
INSERT INTO mec888.prescription_details
    (id, prescription_id, medicine_id, dosage, frequency, duration, instructions, created_at, updated_at)
VALUES
    (801, 701, 1, '100 mg', 'Once daily', '7 days', 'Take with water after breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (802, 701, 2, '10 mg', 'Twice daily', '14 days', 'Take before meals', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (803, 701, 3, '20 mg', 'Once daily', '30 days', 'Take at bedtime', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 10. INSERT dữ liệu cho bảng payments
-- Ví dụ, bệnh nhân với id = 201, ca khám id = 301, thanh toán 250.00 với phương thức "card"
INSERT INTO mec888.payments
    (id, patient_id, appointment_id, amount, payment_method, payment_date, status, created_at, updated_at)
VALUES
    (901, 401, 501, 250.00, 'card', CURRENT_TIMESTAMP, 'paid', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 11. INSERT dữ liệu cho bảng invoices
-- Tạo hóa đơn cho payment id = 901
INSERT INTO mec888.invoices
    (id, payment_id, invoice_number, invoice_date, total_amount, details, created_at, updated_at)
VALUES
    (1001, 901, 'INV-20250401-901', CURRENT_TIMESTAMP, 250.00, 'Invoice for consultation, surgery, and recovery treatment.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


