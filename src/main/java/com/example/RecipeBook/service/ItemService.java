package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.ItemDtoRepository;
import com.example.RecipeBook.dao.ItemRepository;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.ItemsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemDtoRepository itemDtoRepository;

    @Autowired
    ItemRepository itemRepository;

    public void addToItemDto(Ingredient i) {
        List<Item> items = itemDtoRepository.findFirstItemDto().getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(i.getSingleName())) {
                item.increaseQty(i.getNeededQty());
                saveToItemDto(item);
                return;
            }
        }
        Item item = new Item();
        item.setName(i.getSingleName());
        item.setQty(i.getNeededQty());
        saveToItemDto(item);
    }

    public void saveToItemDto(Item item) {
        ItemsDto itemsDto = itemDtoRepository.findFirstItemDto();
        List<Item> items = itemsDto.getItems();
        for (Item i : items) {
            if (i.getName().equals(item.getName())) {
                i.setNeeded(true);
                i.setItemsDto(itemDtoRepository.findFirstItemDto());
                itemRepository.save(i);
                return;
            }
        }
        item.setItemsDto(itemDtoRepository.findFirstItemDto());
        item.setNeeded(true);
        itemRepository.save(item);
        itemDtoRepository.save(itemDtoRepository.findFirstItemDto());
    }

    public void updateTodoDto(List<Item> items) {
        ItemsDto itemsDto = itemDtoRepository.findFirstItemDto();
        for (Item i : items)
            if (!i.isNeeded())
                i.setQty(0);
        items.forEach(item -> item.setItemsDto(itemsDto));
        itemRepository.saveAll(items);
        itemDtoRepository.save(itemsDto);
    }
}
