create table if not exists day_products (
    id              bigserial primary key,
    product_id      bigint not null,
    title           varchar(255),
    quantity        int,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table if not exists month_products (
    id              bigserial primary key,
    product_id      bigint not null,
    title           varchar(255),
    quantity        int,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);




