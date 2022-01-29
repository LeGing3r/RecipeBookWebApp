package com.example.RecipeBook.recipe;

import com.example.RecipeBook.nutiritional.NutritionalInfo;

import java.util.*;

public class RecipeDTO {
    String name;
    String imageLocation;
    boolean chosen;
    CookingTime cookingTime;
    NutritionalInfo nutritionalInfo;
    UUID id;
    int portionSize;
    String instructions;
    Set<String> ingredients = new HashSet<>();
    Set<String> categories = new HashSet<>();

    public RecipeDTO() {
    }

    public RecipeDTO(Recipe recipe) {
        this.name = recipe.name;
        this.imageLocation = recipe.imageLocation;
        this.chosen = recipe.chosen;
        this.cookingTime = recipe.cookingTime;
        this.nutritionalInfo = recipe.nutritionalInfo;
        this.id = recipe.publicId;
        this.portionSize = recipe.portionSize;
        this.instructions = recipe.instructions;
        this.ingredients = recipe.ingredients;
        this.categories = recipe.categories;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public CookingTime getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(CookingTime cookingTime) {
        this.cookingTime = cookingTime;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public Set<String> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public boolean addIngredients(Collection<String> ingredients) {
        return this.ingredients.addAll(ingredients);
    }

    public boolean removeIngredients(Collection<String> ingredients) {
        return this.ingredients.removeAll(ingredients);
    }

    public boolean addCategories(Collection<String> categories) {
        return this.categories.addAll(categories);
    }

    public boolean removeCategories(Collection<String> category) {
        return this.categories.removeAll(category);
    }
}
