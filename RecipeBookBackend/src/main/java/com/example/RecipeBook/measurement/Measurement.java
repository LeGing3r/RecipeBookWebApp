package com.example.RecipeBook.measurement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.RecipeBook.measurement.Measurement.MeasurementStandard.IMPERIAL;
import static com.example.RecipeBook.measurement.Measurement.MeasurementStandard.METRIC;
import static com.example.RecipeBook.measurement.Measurement.MeasurementType.VOLUME;
import static com.example.RecipeBook.measurement.Measurement.MeasurementType.WEIGHT;

public enum Measurement {
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
    CENTILITER(0.01, VOLUME, METRIC, "CENTILITER OF", "CENTILITERS", "CENTILITERS OF"),
    DECILITER(0.1, VOLUME, METRIC, "DECILITER OF", "DECILITERS", "DECILITERS OF"),
    LITER(1, VOLUME, METRIC, "LITERS", "LITRES", "LITER OF", "LITRE OF", "LITERS OF", "LITRES OF"),
    MILLIGRAM(.001, WEIGHT, METRIC, "MG", "MILLIGRAMS", "MILLIGRAM OF", "MG OF", "MILLIGRAMS OF"),
    GRAM(1, WEIGHT, METRIC, "G", "GRAMS", "GRAM OF", "G OF", "GRAMS OF"),
    KILOGRAM(1000, WEIGHT, METRIC, "KG", "KILOGRAMS", "KILOGRAM OF", "KG OF", "KILOGRAMS OF"),
    OUNCE(0.0625, WEIGHT, IMPERIAL, "OZ", "OUNCES", "OUNCE OF", "OZ OF", "OUNCES OF"),
    POUND(1, WEIGHT, IMPERIAL, "LB", "LBS", "POUNDS", "POUND OF", "LB OF", "LBS OF", "POUNDS OF"),
    NONE(1, null, null);
    double toBase;
    MeasurementType type;
    MeasurementStandard standard;
    Set<String> alternateNames = new HashSet<>();

    Measurement(double toBase, MeasurementType type, MeasurementStandard standard, String... alternateNames) {
        this.toBase = toBase;
        this.type = type;
        this.standard = standard;
        this.alternateNames.addAll(List.of(alternateNames));
    }

    public double scaleToStandardBase(double amt) {
        var amount = amt * toBase;
        if (IMPERIAL.equals(this.standard)) {
            return VOLUME.equals(this.type) ? amount * 0.24 : amount * 453.6;
        }
        return amount;
    }

    public Measurement toStandardBase() {
        return VOLUME.equals(this.type) ? LITER : GRAM;
    }

    public Set<String> getAlternateNames() {
        return this.alternateNames;
    }

    public MeasurementType getType() {
        return this.type;
    }

    public String scaleFrom(Measurement measurement, double tempAmt) {
        return null;
    }

    public double compareTo(Measurement measurement) {

    }

    public enum MeasurementStandard {
        IMPERIAL,
        METRIC
    }

    public enum MeasurementType {
        VOLUME,
        WEIGHT
    }
}
