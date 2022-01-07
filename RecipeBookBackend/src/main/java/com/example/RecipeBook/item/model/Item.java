package com.example.RecipeBook.item.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID publicId;
    private String name;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Measurement measurement;
    private Boolean needed = false;
    @Enumerated(EnumType.STRING)
    private Measurement defaultMeasurement;
    private Double amountInGrams;
    private Double amountInCups;
    private String stringValue;
    @ElementCollection(targetClass = String.class)
    private final Set<String> aliases = new HashSet<>();

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }

    public Measurement getDefaultMeasurement() {
        return defaultMeasurement;
    }

    public void setDefaultMeasurement(Measurement defaultMeasurement) {
        this.defaultMeasurement = defaultMeasurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public Boolean getNeeded() {
        return needed;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public void setNeeded(Boolean needed) {
        this.needed = needed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return (id != null && item.id != null && id.equals(item.id)) || this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%f %s %s", amount, measurement.getString(), name);
    }

    public boolean addAlias(String newAlias) {
        return aliases.add(newAlias);
    }

    public static class IngredientConverter implements AttributeConverter<Item, String> {
        @Override
        public String convertToDatabaseColumn(Item attribute) {
            return attribute.toString();
        }

        @Override
        public Item convertToEntityAttribute(String dbData) {
            String[] items = dbData.split(" ");
            Item item = new Item();
            item.amount = Double.parseDouble(items[0]);
            item.measurement = Measurement.valueOf(items[1]);
            item.name = items[2];
            return item;
        }
    }
}
