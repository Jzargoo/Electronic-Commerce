CREATE TABLE reset_password_tokens
(
    user_id     BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    token       TEXT   NOT NULL,
    expires_at  TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id)
);