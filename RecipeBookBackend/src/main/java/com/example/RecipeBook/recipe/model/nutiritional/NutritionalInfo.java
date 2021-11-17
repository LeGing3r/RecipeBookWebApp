package com.example.RecipeBook.recipe.model.nutiritional;

import edu.emory.mathcs.backport.java.util.Collections;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class NutritionalInfo {
    private String uri;
    private int calories;
    private double totalWeight;
    private final Set<String> dietLabels = new HashSet<>();
    private final Set<String> healthLabels = new HashSet<>();
    private final Set<String> cautions = new HashSet<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Set<String> getDietLabels() {
        return Collections.unmodifiableSet(dietLabels);
    }

    public Set<String> getHealthLabels() {
        return Collections.unmodifiableSet(healthLabels);
    }

    public Set<String> getCautions() {
        return java.util.Collections.unmodifiableSet(cautions);
    }


    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

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
            String[] split = dbData.split(",");
            String[] dietLabels = split[3].split(";");
            String[] healthLabels = split[4].split(";");
            String[] cautions = split[5].split(";");
            nutritionalInfo.uri = split[0];
            nutritionalInfo.calories = Integer.parseInt(split[1]);
            nutritionalInfo.totalWeight = Double.parseDouble(split[2]);
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
