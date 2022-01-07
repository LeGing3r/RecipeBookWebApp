package com.example.RecipeBook.item;

import com.example.RecipeBook.errors.AliasExistsException;
import com.example.RecipeBook.item.model.Item;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final Pattern amountPattern = Pattern.compile("^\\d*/?\\d*");
    private final Pattern wordPattern = Pattern.compile("\\s?[a-zA-Z]*\\s?");

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public boolean addItem(Item item) {
        return repository.save(item);
    }

    public Collection<Item> getItems() {
        return repository.getTodoItems();
    }

    public boolean updateList(Collection<Item> items) {
        Set<Item> changedItems = items.stream()
                .filter(item -> repository.containsItem(item.getName()))
                .map(this::getItemString)
                .collect(Collectors.toSet());
        return repository.updateItems(changedItems);
    }

    public Item addAliasToItem(UUID id, String newAlias) {
        var existingItem = repository.getItemWithAlias(newAlias);
        var item = repository.getItemByUUID(id);
        if (existingItem.isPresent()) {
            throw new AliasExistsException();
        }
        repository.addAliasToItem(item, newAlias);
        return item;
    }

    public Item getItem(UUID id) {
        return repository.getItemByUUID(id);
    }

    private Item getItemString(Item item) {
        String newString = item.getStringValue();
        int amount = 0;
        var amountMatcher = amountPattern.matcher(newString);
        var wordMatcher = wordPattern.matcher(newString);
        if (amountMatcher.matches()) {
            amount = Integer.parseInt(amountMatcher.group());
        }
        return item;
    }
}
