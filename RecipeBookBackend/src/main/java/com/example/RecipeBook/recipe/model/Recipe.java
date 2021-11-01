package com.example.RecipeBook.recipe.model;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.ingredient.model.Ingredient;
import com.example.RecipeBook.ingredient.model.nutiritional.NutritionalInfo;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Class wrapping all data necessary for a single recipe
 *
 * @author Brendan Williams
 */
@Entity
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3)
    private String name;
    private String imageLocation;
    private boolean chosen = false;
    private CookingTime cookingTime;
    private NutritionalInfo nutritionalInfo;
    private UUID publicId;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Size(min = 1)
    private final List<Ingredient> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Recipe_Categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private final Set<Category> categories = new HashSet<>();

    @Lob
    @Size(min = 3)
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

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public boolean addIngredients(List<Ingredient> ingredients) {
        return this.ingredients.addAll(ingredients);
    }

    public boolean removeIngredients(List<Ingredient> ingredients) {
        return this.ingredients.removeAll(ingredients);
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
