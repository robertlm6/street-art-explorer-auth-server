CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE  NOT NULL,
    password VARCHAR(255),
    email    VARCHAR(100) UNIQUE NOT NULL,
    provider VARCHAR(50),
    enabled  BOOLEAN             NOT NULL DEFAULT TRUE,
    role_id  INT                 REFERENCES roles (id) ON DELETE SET NULL
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');
