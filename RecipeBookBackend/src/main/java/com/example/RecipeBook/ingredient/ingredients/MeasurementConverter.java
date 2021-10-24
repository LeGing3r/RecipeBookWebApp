package com.example.RecipeBook.ingredient.ingredients;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class MeasurementConverter implements AttributeConverter<Measurement, String> {
    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        return null;
    }

    @Override
    public Measurement convertToEntityAttribute(String s) {
        String[] values = s.split(" ");
        Measurement m = new Measurement();
        var unitName = values[1];
        m.unit = Arrays.stream(Unit.values()).filter(s1 -> s1.name().equals(unitName)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
        m.amount = Double.parseDouble(values[0]);
        return m;
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
