package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultItemService implements ItemService {

    private final DefaultItemRepository repository;

    public DefaultItemService(DefaultItemRepository repository) {
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
