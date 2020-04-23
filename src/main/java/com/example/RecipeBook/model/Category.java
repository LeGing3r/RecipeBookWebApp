package com.example.RecipeBook.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "categories",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Set<Recipe> recipes = new HashSet<>();

    @Override
    public String toString() {
        return "Category{" +
                ", name='" + name + '\'' +
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