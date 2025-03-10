--changeset jzargo: 1
CREATE TABLE users
(
    id             BIGSERIAL PRIMARY KEY,
    username       VARCHAR(32) UNIQUE NOT NULL,
    password       VARCHAR(128) NOT NULL,
    phone_number   VARCHAR(15) UNIQUE NOT NULL,
    profile_image  VARCHAR(64),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products
(
    product_id     SERIAL PRIMARY KEY,
    owner_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name           VARCHAR(255) NOT NULL,
    description    TEXT,
    price          DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    product_image  VARCHAR(64),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_tags
(
    product_id     INT NOT NULL REFERENCES products(product_id) ON DELETE CASCADE,
    tag            VARCHAR(64) NOT NULL,
    PRIMARY KEY (product_id, tag)
);

CREATE TABLE shopping_cart
(
    owner_id   BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    buyer_id   BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id INT NOT NULL REFERENCES products(product_id) ON DELETE CASCADE,
    payment    REAL NOT NULL CHECK (payment >= 0),
    quantity   INT NOT NULL CHECK (quantity > 0),
    added_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (owner_id, buyer_id, product_id)
);