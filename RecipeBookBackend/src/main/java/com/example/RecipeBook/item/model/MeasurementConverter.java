package com.example.RecipeBook.item.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MeasurementConverter implements AttributeConverter<Measurement, String> {
    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        return null;
    }

    @Override
    public Measurement convertToEntityAttribute(String s) {
        return null;
    }
/*

    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        if (measurement == null)
            return null;
        return measurement.amount + ' ' + measurement.unit.name();
    }
*/
}
