ALTER TABLE carts ADD COLUMN quantity INT;

CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,
                         street VARCHAR(255),
                         city VARCHAR(255),
                         state VARCHAR(255),
                         country VARCHAR(255),
                         zip_code VARCHAR(20)
);

CREATE TABLE orders (
    id bigserial PRIMARY KEY ,
    buyer_id bigint REFERENCES users,
    product_id int REFERENCES products,
    address_id bigint REFERENCES address,
    date_dispatch date
);

ALTER TABLE products ADD COLUMN category varchar(46);

ALTER TABLE users ADD COLUMN roles VARCHAR(255);