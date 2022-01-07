package com.example.RecipeBook.category.model;

import java.util.UUID;

public class CategoryWithoutRecipes {
    UUID id;
    String name;

    public CategoryWithoutRecipes(Category category){
        this.name = category.name;
        this.id = category.publicId;
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
}
