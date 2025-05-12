;


CREATE TABLE payment_methods
(
                                 id          BIGSERIAL PRIMARY KEY,
                                 stripe_id   VARCHAR(255) UNIQUE NOT NULL,
                                 user_id     INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
                                 type        VARCHAR(20) NOT NULL CHECK (type IN ('CARD', 'PAYPAL', 'BANK_TRANSFER')),
                                 last4       VARCHAR(4) NULL,
                                 brand       VARCHAR(20) NULL,
                                 created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE payments
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            INT                 NOT NULL REFERENCES users (id),
    stripe_payment_id  VARCHAR(255) UNIQUE NOT NULL,
    stripe_customer_id VARCHAR(255)        NULL,
    amount             DECIMAL(10, 2)      NOT NULL,
    currency           VARCHAR(3)          NOT NULL,
    payment_method_id  BIGINT NULL REFERENCES payment_methods (id) ON DELETE SET NULL,
    order_id           INT                 NULL REFERENCES orders (id),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status             VARCHAR(20)         NOT NULL CHECK (status IN ('PENDING', 'PAID', 'FAILED', 'REFUNDED')),
    metadata           JSON                NULL
);




CREATE TABLE categories(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE coupons
(
    id              SERIAL PRIMARY KEY,
    code            TEXT UNIQUE                                        NOT NULL,
    discount_type   TEXT CHECK (discount_type IN ('FIXED', 'PERCENT')) NOT NULL,
    category_id     INT                                                NULL REFERENCES categories (id),
    value           NUMERIC                                            NOT NULL,
    minimum_order_price NUMERIC DEFAULT 0
);

CREATE TABLE product_discounts
(
    id            SERIAL PRIMARY KEY,
    product_id    INT                                                NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    discount_type TEXT CHECK (discount_type IN ('FIXED', 'PERCENT')) NOT NULL,
    value         NUMERIC                                            NOT NULL,
    start_date    TIMESTAMP DEFAULT NOW(),
    end_date      TIMESTAMP,
    minimum_order_price NUMERIC DEFAULT 0
);

ALTER TABLE products RENAME COLUMN category TO category_id;
ALTER TABLE products ALTER COLUMN category_id TYPE INT USING category_id::integer;
ALTER TABLE products
    ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL;
ALTER TABLE products ADD COLUMN discount_id INT REFERENCES product_discounts;