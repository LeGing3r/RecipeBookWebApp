package com.example.RecipeBook.measurement;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MeasurementConverter implements AttributeConverter<Measurement, String> {
    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        return null;
    }

    @Override
    public Measurement convertToEntityAttribute(String s) {
        return null;
    }
}
