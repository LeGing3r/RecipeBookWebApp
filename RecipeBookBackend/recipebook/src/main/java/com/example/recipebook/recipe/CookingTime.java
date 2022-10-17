package com.example.recipebook.recipe;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class CookingTime {
    private long prepTime;
    private long actualCookingTime;
    private long totalCookingTime;

    public CookingTime() {
    }

    public CookingTime(long prepTime, long actualCookingTime) {
        this.prepTime = prepTime;
        this.actualCookingTime = actualCookingTime;
        this.totalCookingTime = prepTime + actualCookingTime;
    }

    public void setPrepTime(long prepTime) {
        this.prepTime = prepTime;
        this.totalCookingTime = prepTime + actualCookingTime;
    }

    public void setActualCookingTime(long actualCookingTime) {
        this.actualCookingTime = actualCookingTime;
        this.totalCookingTime = prepTime + actualCookingTime;
    }

    public long getTotalCookingTime() {
        return totalCookingTime;
    }

    public long getPrepTime() {
        return prepTime;
    }

    public long getActualCookingTime() {
        return actualCookingTime;
    }

    @Override
    public String toString() {
        return "%d,%d,%d".formatted(prepTime, actualCookingTime, totalCookingTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookingTime that = (CookingTime) o;
        return prepTime == that.prepTime && actualCookingTime == that.actualCookingTime && totalCookingTime == that.totalCookingTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prepTime, actualCookingTime, totalCookingTime);
    }

    public static class CookingTimeConverter implements AttributeConverter<CookingTime, String> {
        @Override
        public String convertToDatabaseColumn(CookingTime attribute) {
            return attribute == null ? "0,0,0" : attribute.toString();
        }

        @Override
        public CookingTime convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return new CookingTime(0, 0);
            }
            var cookingTime = new CookingTime();
            var values = dbData.split(",");

            cookingTime.prepTime = Long.parseLong(values[0]);
            cookingTime.actualCookingTime = Long.parseLong(values[1]);
            cookingTime.totalCookingTime = Long.parseLong(values[2]);

            return cookingTime;
        }
    }
}
