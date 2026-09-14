CREATE TABLE `user` (
                        id          INT AUTO_INCREMENT PRIMARY KEY,
                        name        VARCHAR(120) NOT NULL,
                        email       VARCHAR(160) NOT NULL,
                        password    VARCHAR(255) NOT NULL,
                        created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                        CONSTRAINT uk_user_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE application (
                             id             INT AUTO_INCREMENT PRIMARY KEY,
                             user_id        INT NOT NULL,
                             title          VARCHAR(160) NOT NULL,
                             company_name   VARCHAR(160) NOT NULL,
                             description    VARCHAR(512),
                             job_url        VARCHAR(500),
                             status         VARCHAR(20) NOT NULL,
                             salary_min     DECIMAL(18,2),
                             salary_max     DECIMAL(18,2),
                             board_position INT NOT NULL,
                             applied_at     DATE NOT NULL,
                             due_date       DATETIME(6),
                             created_at     DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                             updated_at     DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                             CONSTRAINT chk_application_status CHECK (status IN
                                                                      ('APPLIED','SCREENING','TECHNICAL','FINAL','OFFER','REJECTED','GHOSTED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE curriculum (
                            id           INT AUTO_INCREMENT PRIMARY KEY,
                            user_id      INT NOT NULL,
                            label        VARCHAR(80) NOT NULL,
                            storage_key  VARCHAR(255) NOT NULL,
                            created_at   DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE application_curriculum (
                                        application_id INT NOT NULL,
                                        curriculum_id  INT NOT NULL,
                                        PRIMARY KEY (application_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE application
    ADD CONSTRAINT fk_application_user
        FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE;

ALTER TABLE curriculum
    ADD CONSTRAINT fk_curriculum_user
        FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE CASCADE;

ALTER TABLE application_curriculum
    ADD CONSTRAINT fk_ac_application
        FOREIGN KEY (application_id) REFERENCES application (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_ac_curriculum
        FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE RESTRICT;

CREATE INDEX idx_application_board ON application (user_id, status, board_position);
CREATE INDEX idx_application_applied ON application (user_id, applied_at DESC);
CREATE INDEX idx_curriculum_user ON curriculum (user_id);
CREATE INDEX idx_ac_curriculum ON application_curriculum (curriculum_id);