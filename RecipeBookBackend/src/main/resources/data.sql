INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Beet Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bbc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done', 2);

INSERT into Recipe_Ingredients VALUES (1,'2.0 TEASPOON Garlic');
INSERT into Recipe_Ingredients VALUES (1,'1.0 GRAM Tomato');
INSERT into Category (name, public_id) VALUES ('Vegan', '07abaf93-d88f-4bbc-ba0c-43c4a5b4cc42');
INSERT into Recipe_Categories VALUES (1,1);

INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Corn Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bdc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done', 8);

INSERT into Recipe_Categories VALUES (2,1);

INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Strawberry Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bac-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done', 8);

INSERT into Category (name, public_id) VALUES ('Vegetarian', '17abaf93-d88f-4bbc-ba0c-43c4a5b4cc42');
INSERT into Recipe_Categories VALUES (3,2);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('apple', 'NONE', 195, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('apricot', 'NONE', 35, 5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('avocado', 'NONE', 170, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('banana', 'NONE', 120, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('blackberry', 'GRAM', 2.45, 70);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('blueberry', 'GRAM', 0.5, 170);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cherry', 'GRAM', 5, 190);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('shredded coconut', 'GRAM', 85, 85);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cranberry', 'GRAM', 1.13, 106);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('fig', 'NONE', 50, 3);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('grapefruit', 'NONE', 246, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('grape', 'GRAM', 5, 160);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('guava', 'NONE', 200, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('jackfruit', 'NONE', 6800, .02);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('kiwi', 'NONE', 75, 2.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('lemon', 'NONE', 100, 5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('lime', 'NONE', 50, 8);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('mango', 'NONE', 200, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cantaloupe', 'NONE', 1360, 0.2);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('honeydew', 'NONE', 1800, 0.2);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('nectarine', 'NONE', 150, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('olive', 'GRAM', 5, 36);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('orange', 'NONE', 130, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('papaya', 'NONE', 450, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('peach', 'NONE', 150, 1.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('pear', 'NONE', 180, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('pineapple', 'NONE', 1590, 7);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('plum', 'NONE', 65, 2);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('pomegranate', 'NONE', 255, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('grapefruit', 'NONE', 4500, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('raspberry', 'GRAM', 5, 123);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('strawberry', 'GRAM', 12, 96);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('watermelon', 'NONE', 8000, 0.03);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('artichoke', 'NONE', 368, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('asparagus', 'GRAM', 22, 180);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('clove of garlic', 'NONE', 5, 48);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('green beans', 'GRAM', 5, 60);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('beet', 'NONE', 113, 1.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('bell pepper', 'NONE', 170, 2);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('broccoli', 'GRAM', 225, 0.3);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('brussel sprouts', 'GRAM', 14, 6);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cabbage', 'NONE', 9070, 8.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('carrot', 'NONE', 60, 2.75);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cauliflower', 'GRAM', 500, 0.25);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('stalk of celery', 'NONE', 450, 0.22);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('corn', 'GRAM', 0.3, 125);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('cucumber', 'NONE', 250, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('kale', 'GRAM', 500, 0.25);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('lettuce', 'NONE', 800, 0.09);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('mushroom', 'NONE', 15, 4);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('yellow onion', 'NONE', 160, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('white onion', 'NONE', 160, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('red onion', 'NONE', 160, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('parsnip', 'NONE', 115, 0.5);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('pea', 'GRAM', 0.2, 134);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('potato', 'NONE', 184, 1);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('snow pea', 'NONE', 2.5, 25);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('spinach', 'GRAM', 500, 0.06);

INSERT INTO Item (name, default_measurement, amount_in_grams, amount_in_cups)
VALUES ('squash', 'NONE', 200, 2.75);

--Squash (yellow)	1 	7 oz	200 g
--Squash (butternut) 	1 	2.5 lb. 	1.1 kg
--Sweet Potato	1 	4 oz 	113 g
--Tomato	1 	6 oz 	170 g
--Zucchini 	1 	7 oz 	200 g