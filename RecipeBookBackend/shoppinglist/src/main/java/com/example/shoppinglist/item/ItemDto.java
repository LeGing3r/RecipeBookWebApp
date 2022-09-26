package com.example.shoppinglist.item;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ItemDto {

    String name;
    String measurement;
    boolean needed = true;
    UUID id;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.name = item.name;
        this.measurement = item.measurement.toString();
        this.needed = item.needed;
        this.id = item.publicId;
    }

    public ItemDto(String measurement, String name) {
        this.measurement = measurement;
        this.name = name;
    }

    public Item toItem() {
        var item = new Item();
        item.name = name;
        item.measurement = new Measurement.MeasurementConverter().convertToEntityAttribute(measurement);
        item.actualMeasurement = item.measurement;
        return item;
    }

    @Override
    public String toString() {
        return measurement + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(id, itemDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
