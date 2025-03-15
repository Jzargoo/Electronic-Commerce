CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,
                         street VARCHAR(255),
                         city VARCHAR(255),
                         state VARCHAR(255),
                         country VARCHAR(255),
                         zip_code VARCHAR(20)
);
