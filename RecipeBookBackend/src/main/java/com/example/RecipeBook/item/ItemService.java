package com.example.RecipeBook.item;

import com.example.RecipeBook.item.model.item.Item;

import java.util.Collection;

public interface ItemService {

    boolean addItem(Item item);

    Collection<Item> getItems();

    boolean updateList(Collection<Item> items);
}
