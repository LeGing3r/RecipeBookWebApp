package com.example.RecipeBook.item.model;

public enum Measurement {
    TEASPOON(0.0208),
    TABLESPOON(0.0625),
    FLUID_OUNCE(0.125),
    CUP(1),
    PINT(2),
    QUART(4),
    GALLON(16),
    MILLILITER(0.001),
    CENTILITER(0.01),
    DECILITER(0.1),
    LITER(1),
    MILLIGRAM(.001),
    GRAM(1),
    KILOGRAM(1000),
    OUNCE(0.0625),
    POUND(1);
    double toBase;
    double amount;

    Measurement(double toBase) {
        this.toBase = toBase;
    }

    public double scale(double amount) {
        return (amount / this.toBase) / this.toBase;
    }

    public double getAmount() {
        return amount;
    }
}
