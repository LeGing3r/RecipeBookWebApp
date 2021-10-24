package com.example.RecipeBook.shoppinglist;

import com.example.RecipeBook.ingredient.ingredients.Ingredient;
import com.example.RecipeBook.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemDtoRepository itemDtoRepository;

    @Autowired
    ItemRepository itemRepository;

    public void addToItemDto(Recipe recipe, int ingredientId) {
        Ingredient ingredientTemp = null;
        for (Ingredient ingredient : recipe.getIngredients()) {
            if ((ingredient.getId() != null) && (ingredient.getId().equals(ingredientId)))
                ingredientTemp = ingredient;
        }

        if (ingredientTemp == null) {
            throw new IllegalStateException("Recipe does not have ingredient");
        }

        List<Item> items = itemDtoRepository.returnItemDto().getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(ingredientTemp.getSingleName())) {
                item.increaseQty(ingredientTemp.getNeededQty());
                saveToItemDto(item);
                return;
            }
        }
        Item item = new Item();
        item.setName(ingredientTemp.getSingleName());
        item.setQty(ingredientTemp.getNeededQty());
        saveToItemDto(item);
    }

    public void saveToItemDto(Item item) {
        ItemsDto itemsDto = itemDtoRepository.returnItemDto();
        List<Item> items = itemsDto.getItems();
        for (Item i : items) {
            if (i.getName().equals(item.getName())) {
                i.setNeeded(true);
                i.increaseQty(1);
                i.setItemsDto(itemDtoRepository.returnItemDto());
                itemRepository.save(i);
                return;
            }
        }
        item.setItemsDto(itemDtoRepository.returnItemDto());
        item.setNeeded(true);
        itemRepository.save(item);
        itemDtoRepository.save(itemDtoRepository.returnItemDto());
    }

    public void updateTodoDto(List<Item> items) {
        ItemsDto itemsDto = itemDtoRepository.returnItemDto();
        for (Item i : items)
            if (!i.isNeeded())
                i.setQty(0);
        items.forEach(item -> item.setItemsDto(itemsDto));
        itemRepository.saveAll(items);
        itemDtoRepository.save(itemsDto);
    }

    public ItemsDto listOfNeededItems() {
        ItemsDto itemsDto = itemDtoRepository.returnItemDto();
        List<Item> itemList = itemsDto.getItems()
                .stream()
                .filter(Item::isNeeded)
                .collect(Collectors.toList());
        itemsDto.setItems(itemList);
        return itemsDto;
    }
}
