package com.example.recipebook.recipe;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class RecipePageElement {
    private String id;
    private String name;
    private String image;
    private boolean chosen;
    private Set<String> categories;

    public RecipePageElement(Recipe recipe) {
        this.id = recipe.id;
        this.name = recipe.name;
        this.image = recipe.imageLocation;
        this.chosen = recipe.chosen;
        this.categories = recipe.categories;
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
