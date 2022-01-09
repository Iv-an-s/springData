create table if not exists products (id bigserial primary key, title varchar(255), price int);
insert into products (title, price) values
('bread', 10),
('milk', 20),
('cheese', 30),
('apple', 40),
('fish', 50),
('meat', 60),
('butter', 70),
('onion', 80),
('garlic', 90),
('potato', 100),
('tomato', 110),
('cucumber', 120),
('honey', 130),
('sugar', 140),
('salt', 150),
('oil', 160),
('pepper', 170),
('orange', 180),
('banana', 190),
('plum', 200);

create table users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
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
    id          bigserial primary key,
    user_id     bigint not null references users (id),
    total_price int not null,
    address     varchar(255),
    phone       varchar(255)
);

create table order_items (
    id                  bigserial primary key,
    product_id          bigint not null references products (id),
    order_id            bigint not null references orders (id),
    quantity            int not null,
    pricePerProduct     int not null,
    price               int not null
);