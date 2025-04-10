CREATE TABLE IF NOT EXISTS treatment_steps_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    treatment_step_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_treatment_steps_service_treatment_step FOREIGN KEY (treatment_step_id) REFERENCES treatment_steps(id),
    CONSTRAINT fk_treatment_steps_service_service FOREIGN KEY (service_id) REFERENCES services(id)

);