package com.example.RecipeBook.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3)
    private String name;

    @OneToMany(mappedBy = "recipe")
    @Size(min = 1)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Recipe_Categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories = new ArrayList<>();

    private boolean chosen;
    @Size(min = 3)
    private String method;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                " , name=' " + name + '\'' +
                ", categories='" +
                categories.stream().map(Category::getName).collect(Collectors.toList())
                + "', " +
                ingredients.stream().map(Ingredient::getName).collect(Collectors.toList())
                + '\'' +
                '}';
    }
}
