package com.example.recipebook.recipe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NutritionalInfo {
    private String uri;
    private int calories;
    private double totalWeight;
    private final List<String> dietLabels = new ArrayList<>();
    private final List<String> healthLabels = new ArrayList<>();
    private final List<String> cautions = new ArrayList<>();

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

    @Converter
    public static class NutritionConverter implements AttributeConverter<NutritionalInfo, String> {

        @Override
        public String convertToDatabaseColumn(NutritionalInfo attribute) {
            return attribute.toString();
        }

        @Override
        public NutritionalInfo convertToEntityAttribute(String dbData) {
            var nutritionalInfo = new NutritionalInfo();
            var nutritionStrings = dbData.split(",");

            nutritionalInfo.calories = Integer.parseInt(nutritionStrings[0]);
            nutritionalInfo.totalWeight = Double.parseDouble(nutritionStrings[1]);

            var dietLabels = nutritionStrings[2].split(";");
            var healthLabels = nutritionStrings[3].split(";");
            var cautions = nutritionStrings[4].split(";");

            nutritionalInfo.dietLabels.addAll(List.of(dietLabels));
            nutritionalInfo.healthLabels.addAll(List.of(healthLabels));
            nutritionalInfo.cautions.addAll(List.of(cautions));

            return nutritionalInfo;
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

}
