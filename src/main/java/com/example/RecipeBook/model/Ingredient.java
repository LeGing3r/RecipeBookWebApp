package com.example.RecipeBook.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private int qty;
    @ManyToOne
    @JoinTable(
            name = "Ingredient_Recipe",
            joinColumns = {@JoinColumn(name = "ingredient_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")})
    private Recipe recipe;
    @ManyToOne
    private ShoppingList todo;
    private String measurement;

    public String setMeasurement(String measurement) {
        measurement = measurement.toLowerCase();
        measurement = measurement.endsWith("s") ? measurement.substring(0, measurement.length() - 1) : measurement;
        switch (measurement) {
            case "" -> measurement = "pieces";
            case "tablespoon" -> measurement = "tbsp";
            case "teaspoon" -> measurement = "tsp";
            case "gram" -> measurement = "g";
            case "kilogram" -> measurement = "kg";
        }
        this.measurement = measurement;
        return this.measurement;
    }
}
