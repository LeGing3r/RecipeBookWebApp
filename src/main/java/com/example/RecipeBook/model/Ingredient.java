package com.example.RecipeBook.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 1)
    private String qty;

    private boolean needed = true;

    private String singleName;
    private int neededQty;

    @ManyToOne
    @JoinTable(
            name = "Ingredient_Recipe",
            joinColumns = {@JoinColumn(name = "ingredient_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")})
    private Recipe recipe;
}
