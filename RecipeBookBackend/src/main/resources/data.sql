INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions)
VALUES ('Beet Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bbc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done');

INSERT into Ingredient (amount, measurement, name) VALUES (2,'TEASPOON','Garlic');
INSERT into Ingredient_Recipe VALUES (1,1);
INSERT into Category (name, public_id) VALUES ('Vegan', '07abaf93-d88f-4bbc-ba0c-43c4a5b4cc42');
INSERT into Recipe_Categories VALUES (1,1);


INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions)
VALUES ('Corn Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bdc-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done');

INSERT into Ingredient (amount, measurement, name) VALUES (3,'TEASPOON','Garlic');
INSERT into Ingredient_Recipe VALUES (2,2);
INSERT into Recipe_Categories VALUES (2,1);


INSERT into recipe (name, image_location, chosen, cooking_time, nutritional_info, public_id, instructions)
VALUES ('Strawberry Soup', 'C:/Users/babw0/Downloads/oh_my_jarek.jpg', false, '30,45,75','https://google.com,180,2.2,FAT;GOOD;,VEGAN,BIIIG;OILY',
        '07abaf93-d88f-4bac-ba0c-43c4a5b4cc42', 'Stir the pot,Add the matoes,Youre done');

INSERT into Ingredient (amount, measurement, name)
VALUES (1,'CUP','Garlic');
INSERT into Ingredient_Recipe VALUES (3,3);
INSERT into Category (name, public_id) VALUES ('Vegetarian', '17abaf93-d88f-4bbc-ba0c-43c4a5b4cc42');
INSERT into Recipe_Categories VALUES (3,2);

