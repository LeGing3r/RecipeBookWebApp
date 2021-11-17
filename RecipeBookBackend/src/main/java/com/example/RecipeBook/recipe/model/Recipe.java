package com.example.RecipeBook.recipe.model;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.item.model.item.IngredientItem;
import com.example.RecipeBook.recipe.model.nutiritional.NutritionalInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;

/**
 * Class wrapping all data necessary for a single recipe
 *
 * @author Brendan Williams
 */
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String imageLocation;
    private boolean chosen = false;
    @Convert(converter = CookingTime.CookingTimeConverter.class)
    private CookingTime cookingTime;
    @Convert(converter = NutritionalInfo.NutritionConverter.class)
    private NutritionalInfo nutritionalInfo;
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID publicId;
    @Lob
    @Column(length = 100000)
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = ALL)
    private final Set<IngredientItem> ingredients = new HashSet<>();

    @ManyToMany(cascade = PERSIST)
    @JoinTable(
            name = "Recipe_Categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private final Set<Category> categories = new HashSet<>();

    public void switchChosen() {
        chosen = !chosen;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
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

    public Set<IngredientItem> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public boolean addIngredients(Collection<IngredientItem> ingredients) {
        return this.ingredients.addAll(ingredients);
    }

    public boolean removeIngredients(Collection<IngredientItem> ingredients) {
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

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        if (this.publicId == null)
            this.publicId = publicId;
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

    public void mergeWithNewRecipe(Recipe recipe) {
        name = recipe.name;
        imageLocation = recipe.imageLocation;
        chosen = recipe.chosen;
        cookingTime = recipe.cookingTime;
        nutritionalInfo = recipe.nutritionalInfo;
        instructions = recipe.instructions;
        ingredients.clear();
        ingredients.addAll(recipe.ingredients);
        categories.clear();
        categories.addAll(recipe.categories);
    }
}

