package com.example.RecipeBook.ingredient;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConversionObj {
    private String contents;
    private String name;
    private static List<String> metricUnits = List.of("grams", "g", "ml", "gram");
    private static List<String> imperialUnits = List.of("cup", "cups", "lbs", "lb", "pounds");

    public static List<String> getUnits() {
        List<String> allUnits = new ArrayList<>();
        allUnits.addAll(metricUnits);
        allUnits.addAll(imperialUnits);
        return allUnits;
    }
}
