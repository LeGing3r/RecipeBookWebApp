package com.example.RecipeBook.recipe.model;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.item.model.Item;
import com.example.RecipeBook.recipe.model.nutiritional.NutritionalInfo;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

/**
 * Class wrapping all data necessary for a single recipe
 *
 * @author Brendan Williams
 */
@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3)
    private String name;
    private String imageLocation;
    private boolean chosen = false;
    @OneToOne
    private CookingTime cookingTime;
    @Convert(converter = NutritionalInfo.NutritionConverter.class)
    private NutritionalInfo nutritionalInfo;
    private UUID publicId;

    @OneToMany(mappedBy = "recipe", cascade = ALL)
    private final Set<Item> items = new HashSet<>();

    @ManyToMany(
            fetch = EAGER,
            cascade = {PERSIST, REMOVE})
    @JoinTable(
            name = "Recipe_Categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private final Set<Category> categories = new HashSet<>();

    @Lob
    @Column(length = 100000)
    private String instructions;


    public void switchChosen() {
        chosen = !chosen;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isChosen() {
        return chosen;
    }

    public CookingTime getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(CookingTime cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Set<Item> getIngredients() {
        return Collections.unmodifiableSet(items);
    }

    public boolean addIngredients(List<Item> items) {
        return this.items.addAll(items);
    }

    public boolean removeIngredients(List<Item> items) {
        return this.items.removeAll(items);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public boolean addCategories(List<Category> categories) {
        return this.categories.addAll(categories);
    }

    public boolean removeCategories(List<Category> categories) {
        return this.categories.removeAll(categories);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass().equals(o.getClass()) && id.equals(((Recipe) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
