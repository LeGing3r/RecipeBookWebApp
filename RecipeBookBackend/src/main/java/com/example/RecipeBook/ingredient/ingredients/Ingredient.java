package com.example.RecipeBook.ingredient.ingredients;

import com.example.RecipeBook.recipe.Recipe;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;
    private boolean needed = true;

    @Basic
    private String measurement;

    @Transient
    private Measurement measurementObject;


    @ManyToOne
    @JoinTable(
            name = "Ingredient_Recipe",
            joinColumns = {@JoinColumn(name = "ingredient_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")})
    private Recipe recipe;

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ",recipe='" + recipe.getName() + '\''
                + '}';
    }
}
