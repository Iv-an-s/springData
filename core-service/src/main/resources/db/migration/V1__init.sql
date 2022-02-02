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
    price           int,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table users (
    id                  bigserial primary key,
    username            varchar(36) not null,
    password            varchar(80) not null,
    email               varchar(50) unique,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

create table roles (
    id                  bigserial primary key,
    name                varchar(50) not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

create table users_roles (
    user_id             bigint not null references users (id),
    role_id             bigint not null references roles (id),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$12$0mHGg0a3rqKC2Kk810kBYuzjBOwecu1dYzCHHsUU4K8CsgjoOLkRm', 'bob_johnson@gmail.com'),
       ('john', '$2a$12$0mHGg0a3rqKC2Kk810kBYuzjBOwecu1dYzCHHsUU4K8CsgjoOLkRm', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table orders (
    id                      bigserial primary key,
    user_id                 bigint not null references users (id),
    total_price             int not null,
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
    price_per_product       int not null,
    price                   int not null,
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
    ('meat');

insert into products (title, price, category_id) values
    ('Milk', 100, 5),
    ('bread', 10, 3),
    ('cheese', 30, 5),
    ('apple', 40, 2),
    ('fish', 50, 6),
    ('meat', 60, 7),
    ('butter', 70, 5),
    ('onion', 80, 4),
    ('garlic', 90, 4),
    ('potato', 100, 4),
    ('tomato', 110, 4),
    ('cucumber', 120, 4),
    ('honey', 130, 1),
    ('sugar', 140, 1),
    ('salt', 150, 1),
    ('oil', 160, 1),
    ('pepper', 170, 1),
    ('orange', 180, 2),
    ('banana', 190, 2),
    ('plum', 200, 2);

insert into orders (user_id, total_price, address, phone) values
(1, 200, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price) values
(1, 1, 2, 100, 200);

