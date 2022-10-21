package com.example.recipebook.recipe;

import lombok.Data;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Data
public class NutritionalInfo {
    private final String uri;
    private final int calories;
    private final double totalWeight;
    private final List<String> dietLabels;
    private final List<String> healthLabels;
    private final List<String> cautions;

    public void addDietLabels(String s) {
        if (!dietLabels.contains(s)) {
            dietLabels.add(s);
        }
    }

    public void addCautions(String s) {
        if (!cautions.contains(s)) {
            cautions.add(s);
        }
    }

    public void addHealthLabels(String s) {
        if (!healthLabels.contains(s)) {
            healthLabels.add(s);
        }
    }

//    public enum Nutrients {
//        ENERC_KCAL,
//        FAT,
//        FASAT,
//        FATRN,
//        SUGAR,
//        PROCNT,
//        CHOLE,
//        NA,
//        K,
//        FE,
//        VITA_RAE,
//        VITC,
//        VITB6A,
//        DITB12,
//        VIDD
//    }

    @Override
    public String toString() {
        return "%d,%f,%s,%s,%s".formatted(
                calories,
                totalWeight,
                String.join(";", dietLabels),
                String.join(";", healthLabels),
                String.join(";", cautions)
        );
    }

    @Deprecated(since = "RDBM connection is removed")
    @Converter
    public static class NutritionConverter implements AttributeConverter<NutritionalInfo, String> {

        @Override
        public String convertToDatabaseColumn(NutritionalInfo attribute) {
            return attribute == null ? "0,0.0, , ," : attribute.toString();
        }

        @Override
        public NutritionalInfo convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return new NutritionalInfo("", 0, 0.0, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
            }
            var nutritionStrings = dbData.split(",");
            var calories = Integer.parseInt(nutritionStrings[0]);
            var totalWeight = Double.parseDouble(nutritionStrings[1]);
            var dietLabels = nutritionStrings[2].split(";");
            var healthLabels = nutritionStrings[3].split(";");
            var cautions = nutritionStrings[4].split(";");

            return new NutritionalInfo("", calories, totalWeight, List.of(dietLabels), List.of(healthLabels), List.of(cautions));
        }
    }


}
