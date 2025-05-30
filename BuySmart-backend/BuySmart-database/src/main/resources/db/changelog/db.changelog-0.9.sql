CREATE TABLE address_to_users(
    settings_id bigint REFERENCES user_settings,
    address bigint REFERENCES address,
    PRIMARY KEY (settings_id,address)
);

CREATE TABLE bank_details
(
    id serial PRIMARY KEY ,
    bankName varchar(32),
    bankAccount varchar(32),
    bik varchar(32),
    iinOrBin varchar(32),
    legalName varchar(32)
);

CREATE TABLE seller_info(
    id bigserial PRIMARY KEY,
    seller_id bigint REFERENCES users,
    bank_details int REFERENCES bank_details,
    company_name varchar(50),
    legal_address bigint,
    approved bool
)