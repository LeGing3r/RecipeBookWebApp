package com.example.RecipeBook.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    //TODO: add measurement back to ingredients
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;
    private boolean needed = true;
    private String singleName;
    private int neededQty;

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
                + ", singleName='" + singleName + '\''
                + '}';
    }
}
