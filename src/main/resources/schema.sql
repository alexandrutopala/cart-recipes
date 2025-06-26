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
    cart_id int not null,
    product_id int not null,
    foreign key (cart_id) references carts(id),
    foreign key (product_id) references products(id),
    constraint uk_cart_items unique (cart_id, product_id)
);

create table recipe_items (
    recipe_id int not null,
    product_id int not null,
    foreign key (recipe_id) references recipes(id),
    foreign key (product_id) references products(id),
    constraint uk_recipe_items unique (recipe_id, product_id)
);

create table cart_recipes (
    cart_id int not null,
    recipe_id int not null,
    foreign key (cart_id) references carts(id),
    foreign key (recipe_id) references recipes(id),
    constraint uk_cart_recipes unique (cart_id, recipe_id)
);
