package com.example.RecipeBook.item.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int amount;
    @Enumerated(EnumType.STRING)
    private Measurement measurement;
    private boolean needed = false;
    private Measurement defaultMeasurement;
    private UUID publicId;

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        if (this.publicId == null)
            this.publicId = publicId;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "%d %s %s".formatted(amount, measurement, name);
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
            item.amount = Integer.parseInt(items[0]);
            item.measurement = Measurement.valueOf(items[1]);
            item.name = items[2];
            return item;
        }
    }
}
