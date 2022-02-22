package com.example.RecipeBook.item;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    UUID publicId;
    String name;
    @Convert(converter = Measurement.MeasurementConverter.class)
    Measurement measurement = new Measurement();
    @Convert(converter = Measurement.MeasurementConverter.class)
    Measurement actualMeasurement;
    boolean needed = true;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "staticItemId")
    StaticItem staticItem;


    public Item() {
    }

    Item(String name) {
        this.name = name;
    }

    public UUID getPublicId() {
        return publicId;
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
        return name;
    }

    private double getAmount() {
        return measurement.getAmount();
    }

    private Unit getUnit() {
        return measurement.getUnit();
    }
}
