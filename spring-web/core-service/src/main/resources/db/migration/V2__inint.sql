create table cart_items (
    id                  bigserial primary key,
    cart_key            varchar(255),
    product_id          bigint,
    product_title       varchar(255),
    price               double precision,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);