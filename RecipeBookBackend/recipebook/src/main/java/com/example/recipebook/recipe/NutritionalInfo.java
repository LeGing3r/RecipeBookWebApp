package com.example.recipebook.recipe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public NutritionalInfo(String uri,
                           int calories,
                           double totalWeight,
                           Collection<String> dietLabels,
                           Collection<String> healthLabels,
                           Collection<String> cautions) {
        this.uri = uri;
        this.calories = calories;
        this.totalWeight = totalWeight;
        this.dietLabels.addAll(dietLabels);
        this.healthLabels.addAll(healthLabels);
        this.cautions.addAll(cautions);
    }

    public NutritionalInfo() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionalInfo that = (NutritionalInfo) o;
        return calories == that.calories &&
                Double.compare(that.totalWeight, totalWeight) == 0 &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(dietLabels, that.dietLabels) &&
                Objects.equals(healthLabels, that.healthLabels) &&
                Objects.equals(cautions, that.cautions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, calories, totalWeight, dietLabels, healthLabels, cautions);
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
