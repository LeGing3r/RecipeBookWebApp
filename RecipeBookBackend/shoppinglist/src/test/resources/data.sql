INSERT INTO Static_Item (default_measurement, ratio_grams_to_none, density)
VALUES ('1,NONE', 195, 812);

INSERT INTO Static_Item (default_measurement, ratio_grams_to_none, density)
VALUES ('1,NONE', 35, 10110);

INSERT INTO Static_Item (default_measurement, ratio_grams_to_none, density)
VALUES ('1,NONE', 170, 662);

INSERT INTO Static_Item (default_measurement, ratio_grams_to_none, density)
VALUES ('1,NONE', 120, 951);

INSERT INTO Static_Item_Aliases VALUES (4, 'banana');
INSERT INTO Static_Item_Aliases VALUES (4, 'bananas');
INSERT INTO Static_Item_Aliases VALUES (4, 'Banana');

INSERT INTO Item (public_id, name, measurement, actual_measurement, needed, static_item_id)
VALUES ('07abaf93-d88f-4bac-ba0c-43c4a5b4dc42', 'apple', '1,NONE', '1,NONE', true, 1);

