CREATE TABLE post_logout_redirect_uris
(
    id                       SERIAL PRIMARY KEY,
    post_logout_redirect_uri VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE client_post_logout_redirect_uris
(
    client_id                   INT NOT NULL,
    post_logout_redirect_uri_id INT NOT NULL,
    PRIMARY KEY (client_id, post_logout_redirect_uri_id),
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    FOREIGN KEY (post_logout_redirect_uri_id) REFERENCES post_logout_redirect_uris (id)
);

INSERT INTO post_logout_redirect_uris (id, post_logout_redirect_uri)
VALUES (1, 'http://localhost:4200');
