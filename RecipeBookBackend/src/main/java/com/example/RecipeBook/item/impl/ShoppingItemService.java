package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.item.Item;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ShoppingItemService implements ItemService {

    private final ShoppingItemRepository repository;

    public ShoppingItemService(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addItem(Item item) {
        return repository.save(item);
    }

    @Override
    public Collection<Item> getItems() {
        return repository.getTodoItems();
    }

    @Override
    public boolean updateList(Collection<Item> items) {
        return repository.updateItems(items);
    }
}
