-- Inserting sample products
INSERT INTO products (id, name, price_in_cents) VALUES
    (1, 'Organic Apples (1lb)', 299),
    (2, 'Whole Grain Bread', 399),
    (3, 'Free Range Eggs (12)', 499),
    (4, 'Organic Milk (1gal)', 449),
    (5, 'Grass-Fed Ground Beef (1lb)', 799),
    (6, 'Organic Chicken Breast (1lb)', 699),
    (7, 'Wild Caught Salmon (1lb)', 1299),
    (8, 'Organic Spinach (1lb)', 349),
    (9, 'Avocados (each)', 199),
    (10, 'Organic Tomatoes (1lb)', 349),
    (11, 'Almond Butter (16oz)', 899),
    (12, 'Quinoa (1lb)', 599),
    (13, 'Extra Virgin Olive Oil (16oz)', 799),
    (14, 'Greek Yogurt (32oz)', 449),
    (15, 'Dark Chocolate Bar', 349);

-- Inserting sample recipes (prices will be updated in a later statement)
INSERT INTO recipes (id, name, price_in_cents) VALUES
   (1, 'Breakfast Smoothie', 0),
   (2, 'Grilled Chicken Salad', 0),
   (3, 'Beef Tacos', 0),
   (4, 'Salmon with Quinoa', 0),
   (5, 'Avocado Toast', 0),
   (6, 'Vegetable Stir Fry', 0),
   (7, 'Greek Yogurt Parfait', 0);

-- Associating products with recipes
INSERT INTO recipe_items (recipe_id, product_id) VALUES
    -- Breakfast Smoothie (banana, almond butter, milk, yogurt)
    (1, 4), (1, 11), (1, 14),
    -- Grilled Chicken Salad (chicken, spinach, tomatoes, olive oil)
    (2, 6), (2, 8), (2, 10), (2, 13),
    -- Beef Tacos (beef, tomatoes, avocados)
    (3, 5), (3, 10), (3, 9),
    -- Salmon with Quinoa (salmon, quinoa, spinach)
    (4, 7), (4, 12), (4, 8),
    -- Avocado Toast (avocados, bread)
    (5, 9), (5, 2),
    -- Vegetable Stir Fry (spinach, tomatoes, olive oil)
    (6, 8), (6, 10), (6, 13),
    -- Greek Yogurt Parfait (yogurt, apples)
    (7, 14), (7, 1);

-- Inserting sample shopping carts
INSERT INTO carts (id, total_in_cents) VALUES
       (1, 0),  -- Empty cart
       (2, 0),  -- Only products
       (3, 0),  -- Only recipes
       (4, 0);  -- Mixed cart

-- Adding products to carts
INSERT INTO cart_items (cart_id, product_id) VALUES
-- Cart 2 has individual products
(2, 1), (2, 3), (2, 4),
-- Cart 4 has mixed items
(4, 2), (4, 5), (4, 15);

-- Adding recipes to carts
INSERT INTO cart_recipes (cart_id, recipe_id) VALUES
-- Cart 3 has recipes only
(3, 1), (3, 4),
-- Cart 4 has mixed items and recipes
(4, 2), (4, 7);

-- Update recipe prices based on their contents
UPDATE recipes SET price_in_cents = (
    SELECT SUM(p.price_in_cents)
    FROM recipe_items ri JOIN products p ON ri.product_id = p.id
    WHERE ri.recipe_id = recipes.id
    );

-- Update cart totals based on their contents
UPDATE carts SET total_in_cents = (
      SELECT COALESCE(SUM(p.price_in_cents), 0)
      FROM cart_items ci
               JOIN products p ON ci.product_id = p.id
      WHERE ci.cart_id = carts.id
      ) + (
      SELECT COALESCE(SUM(r.price_in_cents), 0)
      FROM cart_recipes cr
               JOIN recipes r ON cr.recipe_id = r.id
      WHERE cr.cart_id = carts.id
      );


