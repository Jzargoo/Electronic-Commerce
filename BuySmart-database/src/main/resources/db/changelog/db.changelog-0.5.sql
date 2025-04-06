ALTER Table payment_methods ALTER COLUMN user_id TYPE bigint USING user_id::bigint;

ALTER TABLE payments DROP COLUMN stripe_customer_id;
ALTER TABLE users ADD COLUMN stripe_customer_id varchar(60);

CREATE TABLE season_discounts
(
    id                  SERIAL PRIMARY KEY,
    category_id         INT                                                NULL REFERENCES categories (id),
    discount_type       TEXT CHECK (discount_type IN ('FIXED', 'PERCENT')) NOT NULL,
    value               NUMERIC                                            NOT NULL,
    start_date          TIMESTAMP DEFAULT NOW(),
    end_date            TIMESTAMP,
    minimum_order_price NUMERIC
);

CREATE TABLE reviews(
    id            SERIAL PRIMARY KEY,
    user_id       BIGINT                                             NOT NULL  REFERENCES users (id),
    product_id    INT                                                NOT NULL REFERENCES products (id),
    rating        INT CHECK (rating >= 1 AND rating <= 5)            NOT NULL,
    comment       VARCHAR(1500)                                      NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

CREATE TABLE  user_activated_coupons(
    id            SERIAL PRIMARY KEY,
    user_id       BIGINT                                             NOT NULL REFERENCES users (id),
    coupon_id     INT                                                NOT NULL REFERENCES coupons (id)
);