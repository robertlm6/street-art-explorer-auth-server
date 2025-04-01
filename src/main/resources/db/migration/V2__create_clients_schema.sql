CREATE TABLE clients
(
    id            SERIAL PRIMARY KEY,
    client_id     VARCHAR(100) UNIQUE NOT NULL,
    client_secret VARCHAR(255)        NOT NULL
);

CREATE TABLE authentication_methods
(
    id          SERIAL PRIMARY KEY,
    auth_method VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE client_authentication_methods
(
    client_id      INT NOT NULL,
    auth_method_id INT NOT NULL,
    PRIMARY KEY (client_id, auth_method_id),
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (auth_method_id) REFERENCES authentication_methods (id)
);

CREATE TABLE grant_types
(
    id         SERIAL PRIMARY KEY,
    grant_type VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE client_grant_types
(
    client_id     INT NOT NULL,
    grant_type_id INT NOT NULL,
    PRIMARY KEY (client_id, grant_type_id),
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (grant_type_id) REFERENCES grant_types (id)
);

CREATE TABLE redirect_uris
(
    id           SERIAL PRIMARY KEY,
    redirect_uri VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE client_redirect_uris
(
    client_id       INT NOT NULL,
    redirect_uri_id INT NOT NULL,
    PRIMARY KEY (client_id, redirect_uri_id),
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (redirect_uri_id) REFERENCES redirect_uris (id)
);

CREATE TABLE scopes
(
    id    SERIAL PRIMARY KEY,
    scope VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE client_scopes
(
    client_id INT NOT NULL,
    scope_id  INT NOT NULL,
    PRIMARY KEY (client_id, scope_id),
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (scope_id) REFERENCES scopes (id)
);

INSERT INTO authentication_methods (id, auth_method)
VALUES (1, 'client_secret_basic');

INSERT INTO grant_types (id, grant_type)
VALUES (1, 'authorization_code'),
       (2, 'refresh_token');

INSERT INTO scopes (id, scope)
VALUES (1, 'openid');

INSERT INTO redirect_uris (id, redirect_uri)
VALUES (1, 'https://oauthdebugger.com/debug');
