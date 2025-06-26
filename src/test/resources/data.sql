-- GIVEN_empty_cart_and_recipe_WHEN_add_recipes_THEN_recipe_added_and_total_updated
insert into carts values ( 1000, 0 );

-- GIVEN_empty_cart_and_recipe_WHEN_add_recipes_THEN_recipe_added_and_total_updated
insert into carts values ( 1001, 0 );

-- GIVEN_duplicate_recipe_WHEN_add_recipes_THEN_not_found
insert into carts values ( 1002, 0 );
insert into cart_recipes values (1002, 1);

-- GIVEN_cart_with_recipe_WHEN_remove_recipe_THEN_recipe_removed_and_total_updated
insert into carts values ( 1003, 7000 );
insert into recipes values ( 1003, 'Test', 5000 );
insert into cart_recipes values (1003, 1003);

-- GIVEN_cart_with_products_and_recipes_WHEN_find_by_id_THEN_cart_found
insert into carts values ( 1004, 2500 );
insert into products values ( 1004, 'Product 1', 1000 );
insert into recipes values ( 1004, 'Recipe 1', 1500 );
insert into cart_items values (1004, 1004);
insert into cart_recipes values (1004, 1004);