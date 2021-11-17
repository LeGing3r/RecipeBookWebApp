package com.example.RecipeBook.recipe.model;

import javax.persistence.*;

public class CookingTime {
    private long prepTime;
    private long actualCookingTime;
    private long totalCookingTime;

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

    @Override
    public String toString() {
        return "%d,%d,%d".formatted(prepTime, actualCookingTime, totalCookingTime);
    }

    public static class CookingTimeConverter implements AttributeConverter<CookingTime, String> {
        @Override
        public String convertToDatabaseColumn(CookingTime attribute) {
            return attribute.toString();
        }

        @Override
        public CookingTime convertToEntityAttribute(String dbData) {
            CookingTime cookingTime = new CookingTime();
            String[] values = dbData.split(",");
            cookingTime.prepTime = Long.parseLong(values[0]);
            cookingTime.actualCookingTime = Long.parseLong(values[1]);
            cookingTime.prepTime = Long.parseLong(values[2]);
            return cookingTime;
        }
    }
}
