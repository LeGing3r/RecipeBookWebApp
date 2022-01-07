package com.example.RecipeBook.item.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Measurement {
    PINCH(0.001302,"VOLUME", "PINCHES"),
    TEASPOON(0.0208, "TSP", "TEASPOONS"),
    TABLESPOON(0.0625, "TBSP", "TABLESPOONS"),
    FLUID_OUNCE(0.125, "FL_OZ", "FLUID_OUNCES"),
    HANDFUL(1),
    CUP(1, "C", "CUPS"),
    PINT(2, "P", "PINTS", "PNT"),
    QUART(4, "Q", "QRT", "QUARTS"),
    GALLON(16, "G", "GLN", "GALLONS"),
    MILLILITER(0.001, "ML", "MILLILITERS", "MILLILITRE", "MILLILITRES"),
    CENTILITER(0.01, "CL", "CENTILITERS"),
    DECILITER(0.1, "DL", "DECILITERS"),
    LITER(1, "L", "LITRE", "LITERS", "LITRES"),
    MILLIGRAM(.001, "MG", "MILLIGRAMS"),
    GRAM(1, "WEIGHT","G", "GRAMS"),
    KILOGRAM(1000, "KG", "KILOGRAMS"),
    OUNCE(0.0625, "OZ", "OUNCES"),
    POUND(1, "LB", "LBS", "POUNDS"),
    NONE(1, "");
    double toBase;
    Set<String> alternateNames = new HashSet<>();

    Measurement(double toBase, String... alternateNames) {
        this.toBase = toBase;
        this.alternateNames.addAll(List.of(alternateNames));
    }

    public double scale(double amount) {
        return (amount / this.toBase) / this.toBase;
    }

    public static Measurement fromString(String measurement) {
        return Arrays.stream(values())
                .filter(m1 -> m1.alternateNames.contains(measurement))
                .findFirst()
                .orElseThrow();
    }

    public String getString() {
        if (this.toString().equals("NONE"))
            return "";
        return toString();
    }
}
