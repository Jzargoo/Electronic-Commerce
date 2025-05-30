CREATE TABLE review_images
(
    review_id BIGINT NOT NULL REFERENCES reviews (id) ON DELETE CASCADE,
    image      TEXT   NOT NULL
);

CREATE TABLE user_settings(
    id bigserial NOT NULL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    language varchar(2) NOT NULL DEFAULT 'en',
    theme    varchar(20) NOT NULL DEFAULT 'light',
    currency varchar(3) NOT NULL DEFAULT 'USD',
    notifications_enabled boolean NOT NULL DEFAULT true
);