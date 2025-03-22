CREATE TABLE role
(
    id        SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user
(
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(50)  NOT NULL UNIQUE,
    password            VARCHAR(255) NOT NULL,
    role_id             INT          NOT NULL,
    expired             BOOLEAN      NOT NULL DEFAULT FALSE,
    locked              BOOLEAN      NOT NULL DEFAULT FALSE,
    credentials_expired BOOLEAN      NOT NULL DEFAULT FALSE,
    disabled            BOOLEAN      NOT NULL DEFAULT FALSE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE RESTRICT
);

INSERT INTO role (role_name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
