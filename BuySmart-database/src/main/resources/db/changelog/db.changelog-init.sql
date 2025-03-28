--changeset jzargo: 2
CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE NOT NULL,
    password      TEXT               NOT NULL,
    phone_number  VARCHAR(15) UNIQUE NOT NULL,
    profile_image TEXT,
    created_on    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    owner_id    BIGINT         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(1000),
    price       NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_tags
(
    product_id BIGINT      NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    tag        VARCHAR(64) NOT NULL,
    PRIMARY KEY (product_id, tag)
);

CREATE TABLE carts
(
    id         BIGSERIAL PRIMARY KEY,
    buyer_id   BIGINT NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    updated_on Date DEFAULT current_date
);

CREATE TABLE cart_items
(
    cart_id    BIGINT NOT NULL REFERENCES carts (id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    quantity   int    NOT NULL
);


CREATE TABLE product_images
(
    product_id BIGINT NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    image      TEXT   NOT NULL
);
