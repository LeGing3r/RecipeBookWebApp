package com.example.RecipeBook.category;

import com.example.RecipeBook.recipe.Recipe;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "categories",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", recipe qty=" + recipes.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Category))
            return false;
        Category c = (Category) o;
        return name.equals(c.name);

    }
}