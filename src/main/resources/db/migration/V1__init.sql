CREATE TABLE user (
                      id INT NOT NULL AUTO_INCREMENT,
                      username VARCHAR(120) NOT NULL,
                      email VARCHAR(120) NOT NULL,
                      password_hash VARCHAR(255) NOT NULL,
                      bio VARCHAR(512) NULL,
                      avatar_storage_key VARCHAR(255) NULL,
                      created_at DATETIME NOT NULL DEFAULT NOW(),
                      PRIMARY KEY (id),
                      UNIQUE INDEX uq_user_username (username ASC) VISIBLE,
                      UNIQUE INDEX uq_user_email (email ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE game_type (
                           id INT NOT NULL AUTO_INCREMENT,
                           type VARCHAR(45) NOT NULL,
                           PRIMARY KEY (id),
                           UNIQUE INDEX uq_game_type_type (type ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE game (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(200) NOT NULL,
                      slug VARCHAR(255) NOT NULL,
                      summary LONGTEXT NULL,
                      first_release_date DATETIME NULL,
                      cover_image_storage_key VARCHAR(255) NULL,
                      game_type_id INT NOT NULL,
                      created_at DATETIME NOT NULL DEFAULT NOW(),
                      PRIMARY KEY (id),
                      UNIQUE INDEX uq_game_slug (slug ASC) VISIBLE,
                      INDEX fk_game_game_type1_idx (game_type_id ASC) VISIBLE,
                      CONSTRAINT fk_game_game_type1
                          FOREIGN KEY (game_type_id)
                              REFERENCES game_type (id)
                              ON DELETE RESTRICT
                              ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE game_completion_status (
                                        id INT NOT NULL AUTO_INCREMENT,
                                        status VARCHAR(45) NOT NULL,
                                        PRIMARY KEY (id),
                                        UNIQUE INDEX uq_game_completion_status_status (status ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE user_game (
                           id_user INT NOT NULL,
                           id_game INT NOT NULL,
                           id_game_completion_status INT NOT NULL,
                           hours_played DECIMAL(18,2) NULL,
                           starting_date DATETIME NULL,
                           finishing_date DATETIME NULL,
                           grade DECIMAL(3,1) NULL,
                           note LONGTEXT NULL,
                           created_at DATETIME NOT NULL DEFAULT NOW(),
                           PRIMARY KEY (id_user, id_game),
                           INDEX fk_User_has_Game_Game1_idx (id_game ASC) VISIBLE,
                           INDEX fk_UserGame_GameCompletionStatus1_idx (id_game_completion_status ASC) VISIBLE,
                           CONSTRAINT fk_User_has_Game_User
                               FOREIGN KEY (id_user)
                                   REFERENCES user (id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE,
                           CONSTRAINT fk_User_has_Game_Game1
                               FOREIGN KEY (id_game)
                                   REFERENCES game (id)
                                   ON DELETE RESTRICT
                                   ON UPDATE CASCADE,
                           CONSTRAINT fk_UserGame_GameCompletionStatus1
                               FOREIGN KEY (id_game_completion_status)
                                   REFERENCES game_completion_status (id)
                                   ON DELETE RESTRICT
                                   ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE game_genre (
                            id INT NOT NULL AUTO_INCREMENT,
                            genre VARCHAR(45) NOT NULL,
                            PRIMARY KEY (id),
                            UNIQUE INDEX uq_game_genre_genre (genre ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE game_has_gamegenre (
                                    id_game INT NOT NULL,
                                    id_game_genre INT NOT NULL,
                                    PRIMARY KEY (id_game, id_game_genre),
                                    INDEX fk_Game_has_GameGenre_GameGenre1_idx (id_game_genre ASC) VISIBLE,
                                    CONSTRAINT fk_Game_has_GameGenre_Game1
                                        FOREIGN KEY (id_game)
                                            REFERENCES game (id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,
                                    CONSTRAINT fk_Game_has_GameGenre_GameGenre1
                                        FOREIGN KEY (id_game_genre)
                                            REFERENCES game_genre (id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE)
    ENGINE = InnoDB;

CREATE TABLE company (
                         id INT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(200) NOT NULL,
                         slug VARCHAR(255) NOT NULL,
                         logo_storage_key VARCHAR(255) NULL,
                         country VARCHAR(100) NULL,
                         created_at DATETIME NOT NULL DEFAULT NOW(),
                         PRIMARY KEY (id),
                         UNIQUE INDEX uq_company_slug (slug ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE game_company (
                              game_id INT NOT NULL,
                              company_id INT NOT NULL,
                              is_developer TINYINT NOT NULL DEFAULT 0,
                              is_publisher TINYINT NOT NULL DEFAULT 0,
                              is_porting TINYINT NOT NULL DEFAULT 0,
                              is_supporting TINYINT NOT NULL DEFAULT 0,
                              created_at DATETIME NOT NULL DEFAULT NOW(),
                              PRIMARY KEY (game_id, company_id),
                              INDEX fk_game_has_company_company1_idx (company_id ASC) VISIBLE,
                              CONSTRAINT fk_game_has_company_game1
                                  FOREIGN KEY (game_id)
                                      REFERENCES game (id)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE,
                              CONSTRAINT fk_game_has_company_company1
                                  FOREIGN KEY (company_id)
                                      REFERENCES company (id)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE)
    ENGINE = InnoDB;