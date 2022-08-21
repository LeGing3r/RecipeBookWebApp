package com.example.recipebook.recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NutritionalInfoTest {

    private final NutritionalInfo.NutritionConverter converter = new NutritionalInfo.NutritionConverter();

    @Test
    public void convertNutritionalInfoToStringReturnsProperString() {
        var expectedString = "500,5.500000,fat,vegan;vegetarian,eating will kill you;don't eat this for god's sake";
        var nutritionalInfo = new NutritionalInfo();

        nutritionalInfo.setCalories(500);
        nutritionalInfo.setTotalWeight(5.5);
        nutritionalInfo.addDietLabels("fat");
        nutritionalInfo.addHealthLabels("vegan");
        nutritionalInfo.addHealthLabels("vegetarian");
        nutritionalInfo.addCautions("eating will kill you");
        nutritionalInfo.addCautions("don't eat this for god's sake");

        assertEquals(expectedString, converter.convertToDatabaseColumn(nutritionalInfo));
    }

    @Test
    public void convertStringToNutritionalInfoReturnsProperNutritionalInfo() {
        var nutritionString = "500,5.500000,fat,vegan;vegetarian,eating will kill you;don't eat this for god's sake";
        var expectedNutrition = new NutritionalInfo();

        expectedNutrition.setCalories(500);
        expectedNutrition.setTotalWeight(5.5);
        expectedNutrition.addDietLabels("fat");
        expectedNutrition.addHealthLabels("vegan");
        expectedNutrition.addHealthLabels("vegetarian");
        expectedNutrition.addCautions("eating will kill you");
        expectedNutrition.addCautions("don't eat this for god's sake");

        var actualNutritionalInfo = converter.convertToEntityAttribute(nutritionString);

        assertEquals(expectedNutrition.getCalories(), actualNutritionalInfo.getCalories());
        assertEquals(expectedNutrition.getTotalWeight(), actualNutritionalInfo.getTotalWeight());
        assertTrue(expectedNutrition.getCautions().containsAll(actualNutritionalInfo.getCautions()));
        assertTrue(expectedNutrition.getDietLabels().containsAll(actualNutritionalInfo.getDietLabels()));
        assertTrue(expectedNutrition.getHealthLabels().containsAll(actualNutritionalInfo.getHealthLabels()));

    }

}