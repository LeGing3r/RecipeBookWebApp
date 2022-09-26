package com.example.shoppinglist.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StaticItemRepository staticItemRepository;
    @Autowired
    private MeasurementComparator measurementComparator;

    Set<ItemDto> getItems() {
        return itemRepository.getTodoItems()
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toSet());
    }

    void addItem(ItemDto itemDto) {
        var existingItem = itemRepository.findByNameIgnoreCase(itemDto.name);
        if (!existingItem.isEmpty()) {
            addToExistingItem(itemDto, existingItem.iterator().next().publicId);
            return;
        }
        var item = itemDto.toItem();
        item.publicId = UUID.randomUUID();
        item.staticItem = staticItemRepository.findWhereAliasContains(item.name)
                .stream()
                .findFirst()
                .orElse(null);

        if (item.staticItem == null) {
            addItemToNewItemsFile(item);
        }

        itemRepository.save(item);
    }

    void addToExistingItem(ItemDto itemDto, UUID itemId) {
        var item = itemRepository.findByPublicId(itemId);
        var newItem = itemDto.toItem();

        item.staticItem.aliases.add(newItem.name);
        item.actualMeasurement = measurementComparator.addMeasurementToItem(item, newItem.measurement);

        if (item.staticItem == null) {
            item.measurement = item.actualMeasurement;
        } else {
            item.measurement = measurementComparator.getClosestWholeAmount(item.actualMeasurement, item.staticItem);
        }
        itemRepository.save(item);
    }

    void updateList(Collection<ItemDto> itemDtos) {
        itemDtos.forEach(this::updateItem);
    }

    Set<ItemDto> getNonExistingFoodItems(Collection<ItemDto> items) {
        var persistedItems = itemRepository.findAll()
                .stream()
                .flatMap(item -> item.staticItem.aliases.stream())
                .collect(Collectors.toList());

        return items.stream()
                .parallel()
                .filter(item -> !persistedItems.contains(item.name))
                .collect(Collectors.toSet());
    }

    void addFoodItems(Collection<ItemDto> items) {
        items.forEach(this::addFoodItem);
    }

    private void addFoodItem(ItemDto itemDto) {
        var item = itemRepository.findItemsByStaticItemAliases(itemDto.name)
                .iterator()
                .next();

        if (item == null) {
            addItemToNewItemsFile(itemDto.toItem());
        } else {
            addToExistingItem(itemDto, item.publicId);
            return;
        }
        addItem(itemDto);

    }

    private void updateItem(ItemDto itemDto) {
        var item = itemRepository.findByPublicId(itemDto.id);
        var updatedItem = itemDto.toItem();
        if (!updatedItem.needed) {
            itemRepository.delete(item);
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
        try {
            var itemFile = Files.createFile(Path.of(getClass().getResource("newItems.txt").getPath())).toFile();
            try (var fos = new FileOutputStream(itemFile)) {
                fos.write(newItem.toString().getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
