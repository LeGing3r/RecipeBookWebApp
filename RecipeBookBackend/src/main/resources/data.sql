INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Beet Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bbc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done', 2);
INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Corn Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bdc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done', 8);
INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions, portion_size)
VALUES ('Strawberry Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bac-ba0c-43c4a5b4cc42', 'Stir the pot\n,Add the matoes\n,Youre done', 8);

INSERT into Recipe_Ingredients VALUES (1,'2.0 TEASPOON Garlic');
INSERT into Recipe_Ingredients VALUES (1,'1.0 GRAM Tomato');
INSERT into Recipe_Categories VALUES (1,'Vegan');
INSERT into Recipe_Categories VALUES (2,'Vegetarian');
INSERT into Recipe_Categories VALUES (3,'Moo');

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ('apple', 1, 'NONE', 195, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'apricot',1, 'NONE', 35, 1.2);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'avocado', 1, 'NONE', 170, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'banana', 1, 'NONE', 120, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'blackberry', 250, 'GRAM', 2.45, 70);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'blueberry', 250, 'GRAM', 0.5, 170);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cherry', 250, 'GRAM', 5, 190);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'shredded coconut', 500, 'GRAM', 85, 85);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cranberry', 500, 'GRAM', 1.13, 106);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'fig', 1, 'NONE', 50, 3);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'grapefruit', 1, 'NONE', 246, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'grape', 'GRAM', 5, 160);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'guava', 1, 'NONE', 200, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'jackfruit', 1, 'NONE', 6800, .02);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'kiwi', 1, 'NONE', 75, 2.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'lemon', 1, 'NONE', 100, 5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'lime', 1, 'NONE', 50, 8);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'mango', 1, 'NONE', 200, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cantaloupe', 1, 'NONE', 1360, 0.2);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'honeydew', 1, 'NONE', 1800, 0.2);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'nectarine', 1, 'NONE', 150, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'olive', 250, 'GRAM', 5, 36);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'orange', 1, 'NONE', 130, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'papaya', 1, 'NONE', 450, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'peach', 1, 'NONE', 150, 0.375);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'pear', 1, 'NONE', 180, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'pineapple', 1, 'NONE', 1590, 7);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'plum', 1, 'NONE', 65, 2);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'pomegranate', 1, 'NONE', 255, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'grapefruit', 1, 'NONE', 4500, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'raspberry', 250, 'GRAM', 5, 123);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'strawberry', 500, 'GRAM', 12, 96);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'watermelon', 1, 'NONE', 8000, 0.03);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'artichoke', 1, 'NONE', 368, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'asparagus', 500, 'GRAM', 22, 180);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'clove of garlic', 1, 'NONE', 5, 48);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'green beans', 500, 'GRAM', 5, 60);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'beet', 1, 'NONE', 113, 0.375);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'bell pepper', 1, 'NONE', 170, 2);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'broccoli', 500, 'GRAM', 225, 0.3);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'brussel sprouts', 500, 'GRAM', 14, 6);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cabbage', 1, 'NONE', 9070, 8.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'carrot', 1, 'NONE', 60, 2.75);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cauliflower', 500, 'GRAM', 500, 0.25);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'stalk of celery', 1, 'NONE', 450, 0.22);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'corn', 500, 'GRAM', 0.3, 125);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'cucumber', 1, 'NONE', 250, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'kale', 500, 'GRAM', 500, 0.25);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'lettuce', 1, 'NONE', 800, 0.09);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'mushroom', 1, 'NONE', 15, 4);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'yellow onion', 1, 'NONE', 160, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'white onion', 1, 'NONE', 160, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'red onion', 1, 'NONE', 160, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'parsnip', 1, 'NONE', 115, 0.5);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'pea', 500, 'GRAM', 0.2, 134);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'potato', 1, 'NONE', 184, 0.24);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'snow pea', 1, 'NONE', 2.5, 25);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'spinach', 500, 'GRAM', 500, 0.06);

INSERT INTO Static_Item (name, default_amount, default_measurement, amount_in_grams, amount_in_liters)
VALUES ( 'squash', 1, 'NONE', 200, 2.75);