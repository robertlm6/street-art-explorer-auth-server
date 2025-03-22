CREATE TABLE client
(
    id                SERIAL PRIMARY KEY,
    client_id         VARCHAR(255) NOT NULL UNIQUE,
    client_secret     VARCHAR(255) NOT NULL,
    require_proof_key BOOLEAN      NOT NULL
);

CREATE TABLE client_authentication_method
(
    id        SERIAL PRIMARY KEY,
    method    VARCHAR(255) NOT NULL,
    client_id INTEGER      NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

CREATE TABLE authorization_grant_type
(
    id         SERIAL PRIMARY KEY,
    grant_type VARCHAR(255) NOT NULL,
    client_id  INTEGER      NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

CREATE TABLE redirect_uri
(
    id        SERIAL PRIMARY KEY,
    uri       VARCHAR(1024) NOT NULL,
    client_id INTEGER       NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

CREATE TABLE scope
(
    id        SERIAL PRIMARY KEY,
    scope     VARCHAR(255) NOT NULL,
    client_id INTEGER      NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);
