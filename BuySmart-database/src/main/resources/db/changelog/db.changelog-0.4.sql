CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION  prevent_paid_modification() RETURNS TRIGGER AS $$
BEGIN
    IF OLD.status = 'PAID' AND NEW.status NOT IN ('REFUNDED') THEN
        RAISE EXCEPTION 'PAID платежи можно изменить только на REFUNDED';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_payment_status_transition() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'PAID' AND OLD.status NOT IN ('PENDING') THEN
        RAISE EXCEPTION 'Платеж можно перевести в PAID только из PENDING';
    END IF;

    IF NEW.status = 'REFUNDED' AND OLD.status NOT IN ('PAID') THEN
        RAISE EXCEPTION 'Возврат возможен только из PAID';
    END IF;

    IF NEW.status = 'FAILED' AND OLD.status NOT IN ('PENDING') THEN
        RAISE EXCEPTION 'FAILED возможен только из PENDING';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


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



CREATE OR REPLACE TRIGGER block_paid_modifications
    BEFORE UPDATE ON payments
    FOR EACH ROW
EXECUTE FUNCTION prevent_paid_modification();

CREATE OR REPLACE TRIGGER enforce_payment_status_transition
    BEFORE UPDATE ON payments
    FOR EACH ROW
EXECUTE FUNCTION check_payment_status_transition();

CREATE OR REPLACE TRIGGER update_payments_updated_at
    BEFORE UPDATE
    ON payments
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();



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