package com.example.RecipeBook.measurement;

import java.util.Arrays;

public class MeasurementUtils {

    public static Unit fromString(String unit) {
        return Arrays.stream(Unit.values())
                .filter(m1 -> m1.alternateNames.contains(unit))
                .findFirst()
                .orElseThrow();
    }
}
