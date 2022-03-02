--create table categories(
--    id                  bigserial primary key,
--    title               varchar(50) not null,
--    created_at          timestamp default current_timestamp,
--    updated_at          timestamp default current_timestamp
--);

create table if not exists products (
    id              bigserial primary key,
--   category_id     bigint not null references categories (id),
    title           varchar(255),
    price           numeric(8, 2) not null,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table orders (
    id                      bigserial primary key,
    username                varchar(255) not null,
    total_price             numeric(8, 2) not null,
    address                 varchar(255),
    phone                   varchar(255),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

create table order_items (
    id                      bigserial primary key,
    product_id              bigint not null references products (id),
    order_id                bigint not null references orders (id),
    quantity                int not null,
    price_per_product       numeric(8, 2) not null,
    price                   numeric(8, 2) not null,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

--create table categories_products (
--    category_id         bigint not null references categories (id),
--    product_id          bigint not null references products (id),
--    created_at          timestamp default current_timestamp,
--    updated_at          timestamp default current_timestamp,
--    primary key (category_id, product_id)
--);

--insert into categories (title) values
--    ('grocery'),
--    ('fruit'),
--    ('bakery_products'),
--    ('vegetables'),
--    ('dairy_products'),
--    ('fish'),
--    ('meat');

insert into products (title, price) values
    ('Milk', 100.00),
    ('bread', 10.00),
    ('cheese', 30.00),
    ('apple', 40.00),
    ('fish', 50.00),
    ('meat', 60.00),
    ('butter', 70.00),
    ('onion', 80.00),
    ('garlic', 90.00),
    ('potato', 100.00),
    ('tomato', 110.00),
    ('cucumber', 120.00),
    ('honey', 130.00),
    ('sugar', 140.00),
    ('salt', 150.00),
    ('oil', 160.00),
    ('pepper', 170.00),
    ('orange', 180.00),
    ('banana', 190.00),
    ('plum', 200.00);

insert into orders (username, total_price, address, phone) values
('Bob', 200.00, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(1, 1, 2, 100.00, 200.00);

