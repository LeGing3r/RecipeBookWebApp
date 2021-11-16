package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemRepository;
import com.example.RecipeBook.item.model.Item;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public class DefaultItemRepository implements ItemRepository {
    @Override
    public Item findById(UUID id) {
        return null;
    }

    @Override
    public Set<Item> getTodoItems() {
        return null;
    }

    @Override
    public boolean save(Item item) {
        return false;
    }
}
