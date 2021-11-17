package com.example.RecipeBook.item;

import com.example.RecipeBook.item.model.item.Item;
import com.example.RecipeBook.recipe.model.Recipe;

import java.util.Collection;

public interface ItemService {

    void setIngredients(Recipe recipe);

    void addItem(Item item);

    Collection<Item> getShoppingItems();

    void updateList(Collection<Item> items);
}
