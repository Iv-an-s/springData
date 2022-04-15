create table categories(
    id                  bigserial primary key,
    title               varchar(50) not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

create table if not exists products (
    id              bigserial primary key,
    category_id     bigint not null references categories (id),
    title           varchar(255),
    price           numeric(8, 2) not null,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table orders (
    id                      bigserial primary key,
    username                varchar(255) not null,
    total_price             numeric(8, 2) not null,
    city                    varchar(255),
    address                 varchar(255),
    phone                   varchar(255),
    status                  varchar(255),
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

insert into categories (title) values
    ('grocery'),
    ('fruit'),
    ('bakery_products'),
    ('vegetables'),
    ('dairy_products'),
    ('fish'),
    ('meat'),
    ('no_category');

insert into products (title, price, category_id) values
    ('Milk', 100.00, 5),
    ('bread', 10.00, 3),
    ('cheese', 30.00, 5),
    ('apple', 40.00, 2),
    ('fish', 50.00, 6),
    ('meat', 60.00, 7),
    ('butter', 70.00, 5),
    ('onion', 80.00, 4),
    ('garlic', 90.00, 4),
    ('potato', 100.00, 4),
    ('tomato', 110.00, 4),
    ('cucumber', 120.00, 4),
    ('honey', 130.00, 1),
    ('sugar', 140.00, 1),
    ('salt', 150.00, 1),
    ('oil', 160.00, 1),
    ('pepper', 170.00, 1),
    ('orange', 180.00, 3),
    ('banana', 190.00, 2),
    ('plum', 200.00, 2);

insert into orders (username, total_price, city, address, phone) values
('bob', 200.00, 'Moscow', 'Red Square 1', '+7(495)000-00-01');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(1, 1, 2, 100.00, 200.00);

