package com.example.shoppinglist.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;
    @Autowired
    private MeasurementComparator measurementComparator;

    void addItem(ItemDto itemDto) {
        var existingItem = repository.getSimilarItemsFromAlias(itemDto.name).stream().findFirst();
        if (existingItem.isEmpty() || existingItem.get().staticItem == null) {
            var item = itemDto.toItem();
            item.staticItem = repository.getStaticItemByName(itemDto.name);
            item.actualMeasurement = new Measurement(item.measurement.amount, item.measurement.unit);
            item.measurement = measurementComparator.getClosestWholeAmount(item.measurement, item.staticItem);
            repository.save(item);
        } else {
            addToExistingItem(itemDto, existingItem.get().publicId);
        }
    }

    Set<ItemDto> getItems() {
        return repository.getTodoItems()
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
    }

    void addToExistingItem(ItemDto itemDto, UUID itemId) {
        var item = repository.getItemByUUID(itemId);
        var newItem = itemDto.toItem();

        item.staticItem.aliases.add(newItem.name);
        item.actualMeasurement = measurementComparator.addMeasurementToItem(item, newItem.measurement);

        if (item.staticItem == null) {
            item.measurement = item.actualMeasurement;
        } else {
            item.measurement = measurementComparator.getClosestWholeAmount(item.actualMeasurement, item.staticItem);
        }
        repository.save(item);
    }

    void updateList(Collection<ItemDto> itemDtos) {
        itemDtos.forEach(this::updateItem);
    }

    Set<ItemDto> getSimilarItems(String alias) {
        return repository.getSimilarItemsFromAlias(alias)
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
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

    private void addItemToNewItemsFile(Item newItem) {

    }
}
