package com.example.RecipeBook.recipe;

import edu.emory.mathcs.backport.java.util.Collections;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Class wrapping all data necessary for a single recipe
 *
 * @author Brendan Williams
 */
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String imageLocation;
    boolean chosen;
    @Convert(converter = CookingTime.CookingTimeConverter.class)
    CookingTime cookingTime;
    @Convert(converter = NutritionalInfo.NutritionConverter.class)
    NutritionalInfo nutritionalInfo;
    @Type(type = "uuid-char")
    UUID publicId;
    int portionSize;
    @Lob
    @Column(length = 100000)
    String instructions;
    @ElementCollection(targetClass = String.class)
    final Set<String> ingredients = new HashSet<>();
    @ElementCollection(targetClass = String.class)
    final Set<String> categories = new HashSet<>();

    public Recipe() {
    }

    public Recipe(RecipeDTO recipeDTO) {
        this.name = recipeDTO.name;
        this.imageLocation = recipeDTO.imageLocation;
        this.chosen = recipeDTO.chosen;
        this.cookingTime = recipeDTO.cookingTime;
        this.nutritionalInfo = recipeDTO.nutritionalInfo;
        this.publicId = recipeDTO.id;
        this.portionSize = recipeDTO.portionSize;
        this.instructions = recipeDTO.instructions;
        this.ingredients.addAll(recipeDTO.ingredients);
        this.categories.addAll(recipeDTO.categories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass().equals(o.getClass()) && id.equals(((Recipe) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void mergeWithRecipeDTO(RecipeDTO recipe) {
        this.name = recipe.name;
        this.imageLocation = recipe.imageLocation;
        this.chosen = recipe.chosen;
        this.cookingTime = recipe.cookingTime;
        this.nutritionalInfo = recipe.nutritionalInfo;
        this.instructions = recipe.instructions;
        this.ingredients.clear();
        this.ingredients.addAll(recipe.ingredients);
        this.categories.clear();
        this.categories.addAll(recipe.categories);
    }

    public Set<String> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public Set<String> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    RecipeDTO toRecipeDTO() {
        return new RecipeDTO(this);
    }
}

