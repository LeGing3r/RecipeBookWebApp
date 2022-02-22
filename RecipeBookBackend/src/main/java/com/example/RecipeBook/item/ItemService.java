package com.example.RecipeBook.item;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ItemService {

    private final ItemRepository repository;

    private final MeasurementComparator measurementComparator;

    ItemService(ItemRepository repository, MeasurementComparator measurementComparator) {
        this.repository = repository;
        this.measurementComparator = measurementComparator;
    }

    Set<ItemDto> addItem(ItemDto itemDto) {
        var item = itemDto.toItem();
        item.staticItem = repository.getStaticItemByName(item.name);
        repository.save(item);
        return getItems();
    }

    Set<ItemDto> getItems() {
        return repository.getTodoItems()
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
    }

    Set<ItemDto> addToExistingItem(ItemDto itemDto, UUID itemId) {
        var item = repository.getItemByUUID(itemId);
        var newItem = itemDto.toItem();

        item.staticItem.aliases.add(newItem.name);
        item.actualMeasurement = measurementComparator.addMeasurementToItem(item, newItem.measurement);

        if (item.staticItem == null) {
            item.measurement = item.actualMeasurement;
        } else {
            item.measurement = measurementComparator.getClosestWholeAmount(item.measurement, item.staticItem.defaultMeasurement);
        }

        return getItems();
    }

    void updateList(Collection<ItemDto> itemDtos) {
        itemDtos.forEach(this::updateItem);
    }

    private void updateItem(ItemDto itemDto) {
        var item = repository.getItemByUUID(itemDto.id);
        var updatedItem = itemDto.toItem();
        if (!updatedItem.needed) {
            repository.removeItem(item.publicId);
            return;
        }
        if (!item.name.equals(updatedItem.name)) {
            item.staticItem.aliases.add(updatedItem.name);
            item.name = updatedItem.name;
        }
        if (!item.measurement.equals(updatedItem.measurement)) {
            item.measurement = updatedItem.measurement;
            item.actualMeasurement = updatedItem.measurement;
        }
        item.needed = updatedItem.needed;
    }

    Set<ItemDto> getSimilarItems(String alias) {
       return repository.getSimilarItemsFromAlias(alias)
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
    }

    private void addItemToNewItemsFile(Item newItem) {

    }

}
