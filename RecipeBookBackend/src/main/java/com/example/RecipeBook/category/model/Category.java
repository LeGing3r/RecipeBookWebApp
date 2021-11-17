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
    private String name;
    @Type(type = "uuid-char")
    private UUID publicId;
    @JsonIgnore
    @ManyToMany(mappedBy = "categories",
            cascade = {PERSIST, REMOVE},
            fetch = EAGER)
    private final Set<Recipe> recipes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == this || !(o instanceof Category)) {
            return true;
        }
        return id.equals(((Category) o).id);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
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

    public boolean removeRecipes(Collection<Recipe> recipes) {
        return this.recipes.removeAll(recipes);
    }

    public boolean addRecipes(Collection<Recipe> recipes) {
        return this.recipes.addAll(recipes);
    }


}