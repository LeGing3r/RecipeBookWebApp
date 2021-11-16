package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemRepository;
import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import com.example.RecipeBook.recipe.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultItemService implements ItemService {
    private final ItemRepository itemRepository;

    public DefaultItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public void setIngredients(Recipe recipe) {
        recipe.getIngredients()
                .stream()
                .peek(ingredient -> ingredient.setRecipe(recipe))
                .forEach(itemRepository::save);
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public Collection<Item> getShoppingItems() {
        return null;
    }

    @Override
    public void updateList(Collection<Item> items) {

    }

}
