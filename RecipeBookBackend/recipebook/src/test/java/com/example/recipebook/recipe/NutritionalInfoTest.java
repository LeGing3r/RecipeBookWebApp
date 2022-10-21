package com.example.recipebook.recipe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NutritionalInfoTest {

    private final NutritionalInfo.NutritionConverter converter = new NutritionalInfo.NutritionConverter();

    @Test
    public void convertNutritionalInfoToStringReturnsProperString() {
        var expectedString = "500,5.500000,fat,vegan;vegetarian,eating will kill you;don't eat this for god's sake";
        var calories = 500;
        var totalWeight = 5.5;
        var dietLabel = "fat";
        var healthLabel1 = "vegan";
        var healthLabel2 = "vegetarian";
        var caution1 = "eating will kill you";
        var caution2 = "don't eat this for god's sake";
        var nutritionalInfo = new NutritionalInfo("", calories, totalWeight,
                List.of(dietLabel), List.of(healthLabel1, healthLabel2), List.of(caution1, caution2));

        assertEquals(expectedString, converter.convertToDatabaseColumn(nutritionalInfo));
    }

    @Test
    public void convertStringToNutritionalInfoReturnsProperNutritionalInfo() {
        var nutritionString = "500,5.500000,fat,vegan;vegetarian,eating will kill you;don't eat this for god's sake";
        var calories = 500;
        var totalWeight = 5.5;
        var dietLabel = "fat";
        var healthLabel1 = "vegan";
        var healthLabel2 = "vegetarian";
        var caution1 = "eating will kill you";
        var caution2 = "don't eat this for god's sake";
        var expectedNutrition = new NutritionalInfo("", calories, totalWeight,
                List.of(dietLabel), List.of(healthLabel1, healthLabel2), List.of(caution1, caution2));

        var actualNutritionalInfo = converter.convertToEntityAttribute(nutritionString);

        assertEquals(expectedNutrition.getCalories(), actualNutritionalInfo.getCalories());
        assertEquals(expectedNutrition.getTotalWeight(), actualNutritionalInfo.getTotalWeight());
        assertTrue(expectedNutrition.getCautions().containsAll(actualNutritionalInfo.getCautions()));
        assertTrue(expectedNutrition.getDietLabels().containsAll(actualNutritionalInfo.getDietLabels()));
        assertTrue(expectedNutrition.getHealthLabels().containsAll(actualNutritionalInfo.getHealthLabels()));

    }

}