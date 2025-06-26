create table carts (
    id bigint not null primary key,
    total_in_cents int not null
);

create table products (
    id bigint not null primary key,
    name varchar(255) not null,
    price_in_cents int not null
);

create table recipes (
    id bigint not null primary key,
    name varchar(255) not null,
    price_in_cents int not null
);

create table cart_items (
    id identity primary key,
    cart_id int not null,
    product_id int not null,
    foreign key (cart_id) references carts(id),
    foreign key (product_id) references products(id)
);

create table recipe_items (
    id identity primary key,
    recipe_id int not null,
    product_id int not null,
    foreign key (recipe_id) references recipes(id),
    foreign key (product_id) references products(id)
);

create table cart_recipes (
    id identity primary key,
    cart_id int not null,
    recipe_id int not null,
    foreign key (cart_id) references carts(id),
    foreign key (recipe_id) references recipes(id)
);
