package com.example.RecipeBook.measurement;

import java.util.Arrays;

public class MeasurementUtils {

    public static Measurement fromString(String measurement) {
        return Arrays.stream(Measurement.values())
                .filter(m1 -> m1.alternateNames.contains(measurement))
                .findFirst()
                .orElseThrow();
    }

    public static String getLargestWholeAmountComparingStatic(){

    }
}
