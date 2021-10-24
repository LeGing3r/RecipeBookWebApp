package com.example.RecipeBook.model;

import com.example.RecipeBook.model.ingredients.Ingredient;
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
    //TODO: add nutritional info along with time to make
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3)
    private String name;
    private String imgLoc;
    private String imgPath;
    private boolean chosen = false;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Size(min = 1)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Recipe_Categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories = new ArrayList<>();

    @Lob
    @Size(min = 3)
    @Column(length = 100000)
    private String method;


    public boolean containsIngredient(List<Ingredient> ingredients) {
        int originalSize = ingredients.size();
        ingredients.removeAll(this.getIngredients());
        return ingredients.size() < originalSize;
    }

    public void switchChosen(){
        chosen = !chosen;
    }

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
