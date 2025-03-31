ALTER Table payment_methods ALTER COLUMN user_id TYPE bigint USING user_id::bigint;

ALTER TABLE payments DROP COLUMN stripe_customer_id;
ALTER TABLE users ADD COLUMN stripe_customer_id varchar(60);
