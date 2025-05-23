CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) null,
    last_name VARCHAR(255) null,
    gender VARCHAR(255) null,
    date_of_birth DATE null,
    address VARCHAR(255) null,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    career varchar(255) NULL,
    ethnicity varchar(255) NULL,
    nationality varchar(255) NULL,
    place_of_origin varchar(255) NULL,
    role_id BIGINT NOT NULL,
    otp varchar(10) NULL,
    otp_expired_date DATE NULL,
    avatar_url VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
);