package com.example.RecipeBook.errors.controller;

import com.example.RecipeBook.item.ItemController;
import com.example.RecipeBook.item.ItemRepository;
import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.Item;
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