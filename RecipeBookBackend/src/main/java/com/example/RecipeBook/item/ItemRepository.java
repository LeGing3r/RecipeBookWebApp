package com.example.RecipeBook.item;

import com.example.RecipeBook.item.model.Item;

import java.util.Set;
import java.util.UUID;

public interface ItemRepository {

    Item findById(UUID id);
    Set<Item> getTodoItems();
    boolean save(Item item);
}
