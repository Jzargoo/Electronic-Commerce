ALTER TABLE products RENAME COLUMN product_id TO id;

Create Table Product_images
(
    product_id int REFERENCES products,
    image varchar(64)
);

ALTER TABLE products DROP COLUMN product_image;