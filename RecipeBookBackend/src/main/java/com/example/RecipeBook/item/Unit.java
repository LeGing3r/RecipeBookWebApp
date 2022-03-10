package com.example.RecipeBook.item;

import java.util.*;

import static com.example.RecipeBook.item.Unit.UnitStandard.IMPERIAL;
import static com.example.RecipeBook.item.Unit.UnitStandard.METRIC;
import static com.example.RecipeBook.item.Unit.UnitType.VOLUME;
import static com.example.RecipeBook.item.Unit.UnitType.WEIGHT;

public enum Unit {
    PINCH(0.001302, VOLUME, IMPERIAL, "PINCH OF", "PINCHES", "PINCHES OF"),
    TEASPOON(0.0208, VOLUME, IMPERIAL, "TEASPOON OF", "TSP", "TSP OF", "TEASPOONS", "TEASPOONS OF"),
    TABLESPOON(0.0625, VOLUME, IMPERIAL, "TBSP", "TBSP OF", "TABLESPOON OF", "TABLESPOONS", "TABLESPOONS OF"),
    FLUID_OUNCE(0.125, VOLUME, IMPERIAL, "FL OZ OF", "FLUID OUNCE OF", "FLUID OUNCES", "FLUID OUNCES OF"),
    HANDFUL(1, VOLUME, IMPERIAL, "HANDFUL OF"),
    CUP(1, VOLUME, IMPERIAL, "CUP OF", "CUPS", "CUPS OF"),
    PINT(2, VOLUME, IMPERIAL, "PINT OF", "PINTS", "PINTS OF"),
    QUART(4, VOLUME, IMPERIAL, "QUART OF", "QUARTS", "QUARTS OF"),
    GALLON(16, VOLUME, IMPERIAL, "GALLON OF", "GALLONS", "GALLONS OF"),

    MILLILITER(0.001, VOLUME, METRIC, "MILLILITRE", "MILLILITERS", "MILLILITER OF", "MILLILITRE OF", "MILLILITERS OF", "MILLILITRES OF"),
    LITER(1, VOLUME, METRIC, "LITERS", "LITRES", "LITER OF", "LITRE OF", "LITERS OF", "LITRES OF"),

    MILLIGRAM(.001, WEIGHT, METRIC, "MG", "MILLIGRAMS", "MILLIGRAM OF", "MG OF", "MILLIGRAMS OF"),
    GRAM(1, WEIGHT, METRIC, "G", "GRAMS", "GRAM OF", "G OF", "GRAMS OF"),
    KILOGRAM(1000, WEIGHT, METRIC, "KG", "KILOGRAMS", "KILOGRAM OF", "KG OF", "KILOGRAMS OF"),

    OUNCE(0.0625, WEIGHT, IMPERIAL, "OZ", "OUNCES", "OUNCE OF", "OZ OF", "OUNCES OF"),
    POUND(1, WEIGHT, IMPERIAL, "LB", "LBS", "POUNDS", "POUND OF", "LB OF", "LBS OF", "POUNDS OF"),

    NONE(1, UnitType.NONE, UnitStandard.NONE, "");

    double toBase;
    UnitType type;
    UnitStandard standard;
    Set<String> alternateNames = new HashSet<>();

    Unit(double toBase, UnitType type, UnitStandard standard, String... alternateNames) {
        this.toBase = toBase;
        this.type = type;
        this.standard = standard;
        this.alternateNames.addAll(List.of(alternateNames));
    }

    public static Unit getUnitFromString(String s) {
        return Arrays.stream(Unit.values())
                .filter(unit -> unit.alternateNames.contains(s.toUpperCase(Locale.ROOT)))
                .findFirst()
                .orElse(NONE);
    }

    public double toBase() {
        return toBase;
    }

    public UnitType getType() {
        return this.type;
    }

    public UnitStandard getStandard() {
        return this.standard;
    }

    public Unit toMetric() {
        return VOLUME.equals(this.type) ? LITER : GRAM;
    }

    public double convertToMetric(double amt) {
        var amount = amt * toBase;
        if (IMPERIAL.equals(this.standard)) return VOLUME.equals(this.type) ? amount * 0.24 : amount * 453.6;
        return amount;
    }

    public Unit getNextLargestUnit() {
        return switch (this) {
            case PINCH -> TEASPOON;
            case TEASPOON -> TABLESPOON;
            case TABLESPOON -> FLUID_OUNCE;
            case FLUID_OUNCE -> CUP;
            case HANDFUL, CUP -> PINT;
            case PINT -> QUART;
            case QUART -> GALLON;
            case MILLILITER -> LITER;
            case MILLIGRAM -> GRAM;
            case GRAM -> KILOGRAM;
            case OUNCE -> POUND;
            case GALLON, POUND, LITER, NONE, KILOGRAM -> null;
        };
    }

    public double toOtherStandard() {
        if (IMPERIAL.equals(this.standard)) {
            return VOLUME.equals(this.type) ? 0.24 : 453.6;
        }
        return VOLUME.equals(this.type) ? 1 / 0.24 : 1 / 453.6;
    }

    public enum UnitStandard {
        IMPERIAL,
        METRIC,
        NONE
    }

    public enum UnitType {
        VOLUME(0),
        WEIGHT(1),
        NONE(2);
        private final int weight;

        UnitType(int weight) {
            this.weight = weight;
        }

        int compareToType(UnitType type) {
            return this.weight - type.weight;
        }
    }
}
