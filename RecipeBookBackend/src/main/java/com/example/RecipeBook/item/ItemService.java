package com.example.RecipeBook.item;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.RecipeBook.item.Item.ItemConverter.convertFromString;

@Service
public class ItemService {

    private final ItemRepository repository;

    ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    Set<ItemDto> addItem(String item) {
        var newItem = convertFromString(item);
        repository.save(newItem);
        return getItems();
    }

    Set<ItemDto> getItems() {
        return getNeededItems();
    }

    Set<ItemDto> addToExistingItem(ItemDto itemDto, String itemString) {
        var item = repository.getItemByUUID(itemDto.id);
        var newItem = convertFromString(itemString);
        repository.updateItem(item, newItem);
        return getNeededItems();
    }

    private Set<ItemDto> getNeededItems() {
        return repository.getTodoItems().stream().map(ItemDto::new).collect(Collectors.toSet());
    }

    boolean updateList(Collection<Item> items) {
        try {
            repository.removeAll();
            items.forEach(repository::save);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    ItemDto addAliasToItem(UUID id, String newAlias) {
        var item = repository.getItemByUUID(id).staticItem;
        repository.addAliasToStaticItem(item, newAlias);
        return new ItemDto(item);
    }

    Set<ItemDto> getSimilarItems(String item) {
        var newItem = convertFromString(item).name;
        //Compare with existing items
        var items = new LinkedHashSet<>(repository.getSimilarItemsFromAlias(newItem))
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
        //Compare with static items
        var similarItems = repository.getStaticItemsFromAlias(newItem)
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
        items.addAll(similarItems);
        return items;
    }

    private void addItemToNewItemsFile(Item newItem) {

    }

}
