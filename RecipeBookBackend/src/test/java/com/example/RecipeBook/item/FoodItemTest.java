package com.example.RecipeBook.item;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FoodItemTest {

    @Test
    public void whenSameItemAndMeasurementCombine() {
        var staticFood = new StaticItem();
        staticFood.name = "Beet";
        staticFood.aliases.add("of beets");
        var food = FoodItem.ConvertStringToItem("1kg of beets");
        var secondFood = FoodItem.ConvertStringToItem("2kg of beets");
        food.combineWithItem(secondFood);
        assertEquals(food.stringValue, "3 kg of beets");
    }

    @Test
    public void whenSameItemMeasurementTypeDifferentStandardDifferent() {
        var staticFood = new StaticItem();
        staticFood.name = "Beet";
        staticFood.aliases.add("of beets");
        var food = FoodItem.ConvertStringToItem("1 cup of beets");
        var secondFood = FoodItem.ConvertStringToItem("1kg of beets");
        food.combineWithItem(secondFood);
        assertEquals(food.stringValue, "1.1 kg of beets");
    }

    @Test
    public void whenSameItemMeasurementTypeSameStandardDifferent() {
        var staticFood = new StaticItem();
        staticFood.name = "milk";
        staticFood.aliases.add("of milk");
        var food = FoodItem.ConvertStringToItem("1 cup of milk");
        var secondFood = FoodItem.ConvertStringToItem("500ml of milk");
        food.combineWithItem(secondFood);
        assertEquals(food.stringValue, "1 liter of milk");
    }

    @Test
    public void whenSameItemDifferentMeasurementSameTypeSameStandard() {
        var staticFood = new StaticItem();
        staticFood.name = "Milk";
        staticFood.aliases.add("of milk");
        var food = FoodItem.ConvertStringToItem("1 liter of milk");
        var secondFood = FoodItem.ConvertStringToItem("500ml of milk");
        food.combineWithItem(secondFood);
        assertEquals(food.stringValue, "1.5 liters of milk");
    }


    @Test
    public void whenSameItemNoMeasurementAndMeasurement() {
        var staticFood = new StaticItem();
        staticFood.name = "Banana";
        staticFood.aliases.addAll(List.of("of bananas", "banana"));
        var food = FoodItem.ConvertStringToItem("1 banana");
        var secondFood = FoodItem.ConvertStringToItem("500g of bananas");
        food.combineWithItem(secondFood);
        assertEquals(food.stringValue, "5 bananas");
    }

}
