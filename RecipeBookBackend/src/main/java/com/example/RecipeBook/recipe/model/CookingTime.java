package com.example.RecipeBook.recipe.model;

public class CookingTime {
    private Integer id;
    private long prepTime;
    private long actualCookingTime;

    public long getTotalCookingTime() {
        return prepTime + actualCookingTime;
    }

    public long getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(long prepTime) {
        this.prepTime = prepTime;
    }

    public long getActualCookingTime() {
        return actualCookingTime;
    }

    public void setActualCookingTime(long actualCookingTime) {
        this.actualCookingTime = actualCookingTime;
    }
}
