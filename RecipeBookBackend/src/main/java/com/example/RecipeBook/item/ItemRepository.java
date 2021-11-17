package com.example.RecipeBook.item;

import com.example.RecipeBook.item.model.item.Item;

import java.util.Collection;
import java.util.Set;

public interface ItemRepository {

    Set<Item> getTodoItems();

    boolean save(Item item);

    boolean updateItems(Collection<Item> items);
}
