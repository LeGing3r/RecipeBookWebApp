package com.example.RecipeBook.category.model;

import com.example.RecipeBook.recipe.model.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String name;
    @Type(type = "uuid-char")
    UUID publicId;
    @JsonIgnore
    @ManyToMany(mappedBy = "categories",
            cascade = {PERSIST, REMOVE},
            fetch = EAGER)
    final Set<Recipe> recipes = new HashSet<>();

    public Category() {
    }

    public Category(CategoryWithoutRecipes categoryWithoutRecipes){
        this.name = categoryWithoutRecipes.name;
        this.publicId = categoryWithoutRecipes.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this || !(o instanceof Category)) {
            return true;
        }
        return id.equals(((Category) o).id);

    }

    public Set<Recipe> getRecipes() {
        return Collections.unmodifiableSet(recipes);
    }

    public boolean addRecipe(Recipe recipe) {
        return recipes.add(recipe);
    }

    public boolean removeRecipe(Recipe recipe) {
        return recipes.remove(recipe);
    }

}