package com.example.RecipeBook.ingredient.ingredients;

public enum Measurement {
    TEASPOON(0.0208),
    TABLESPOON(0.0625),
    CUP(1),
    MILLILITER(0.001),
    CENTILITER(0.01),
    DECILITER(0.1),
    LITER(1),
    GRAM(1),
    KILOGRAM(1000),
    OUNCE(0.0625),
    POUND(1);
    double toBase;
    double amount;

    Measurement(double toBase) {
        this.toBase = toBase;
    }

    private double scale(double amount) {
        return (amount / this.toBase) / this.toBase;
    }
}
