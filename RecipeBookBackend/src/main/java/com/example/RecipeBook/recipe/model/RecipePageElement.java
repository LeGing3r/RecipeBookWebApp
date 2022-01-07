package com.example.RecipeBook.recipe.model;

import com.example.RecipeBook.category.model.CategoryWithoutRecipes;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipePageElement {
    private UUID id;
    private String name;
    private String image;
    private boolean chosen;
    private Set<CategoryWithoutRecipes> categories;

    public RecipePageElement() {
    }

    public RecipePageElement(Recipe recipe) {
        this.id = recipe.publicId;
        this.name = recipe.name;
        this.image = recipe.imageLocation;
        this.chosen = recipe.chosen;
        this.categories = recipe.getCategories()
                .stream()
                .map(CategoryWithoutRecipes::new)
                .collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public Set<CategoryWithoutRecipes> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryWithoutRecipes> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipePageElement that = (RecipePageElement) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
