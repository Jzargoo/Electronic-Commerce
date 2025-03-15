ALTER TABLE carts ADD COLUMN quantity INT;

CREATE TABLE orders (
    id bigserial,
    buyer_id bigint REFERENCES users,
    product_id int REFERENCES products,
    address varchar(64),
    date_dispatch date
);

ALTER TABLE products ADD COLUMN category varchar(16)