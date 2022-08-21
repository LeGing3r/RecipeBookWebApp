package com.example.shoppinglist.item;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    @Mock
    ItemService itemService;

    @Mock
    ItemRepository ingredientRepository;

    @Mock
    Item item;

    ItemController itemController;
    List<Item> itemList;

}