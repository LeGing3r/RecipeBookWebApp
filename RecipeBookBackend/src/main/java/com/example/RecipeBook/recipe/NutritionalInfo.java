package com.example.RecipeBook.recipe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class NutritionalInfo {
    private String uri;
    private int calories;
    private double totalWeight;
    private final Set<String> dietLabels = new HashSet<>();
    private final Set<String> healthLabels = new HashSet<>();
    private final Set<String> cautions = new HashSet<>();

    public void addDietLabels(String s) {
        dietLabels.add(s);
    }

    public void addCautions(String s) {
        cautions.add(s);
    }

    public void addHealthLabels(String s) {
        healthLabels.add(s);
    }

    @Override
    public String toString() {
        return "%s,%d,%f,%s,%s,%s".formatted(
                uri,
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
            NutritionalInfo nutritionalInfo = new NutritionalInfo();
            String[] nutritionStrings = dbData.split(",");
            nutritionalInfo.uri = nutritionStrings[0];
            nutritionalInfo.calories = Integer.parseInt(nutritionStrings[1]);
            nutritionalInfo.totalWeight = Double.parseDouble(nutritionStrings[2]);
            String[] dietLabels = nutritionStrings[3].split(";");
            String[] healthLabels = nutritionStrings[4].split(";");
            String[] cautions = nutritionStrings[5].split(";");

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
