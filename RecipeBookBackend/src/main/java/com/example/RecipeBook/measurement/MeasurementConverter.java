package com.example.RecipeBook.measurement;

import com.example.RecipeBook.NumberUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.regex.Pattern;

@Converter(autoApply = true)
public class MeasurementConverter implements AttributeConverter<Measurement, String> {
    public static final Pattern pattern = Pattern.compile("([0-9]+\\.*/*[0-9]*)([a-z]*)");

    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        if (measurement == null) {
            return null;
        }
        return measurement.toString();
    }

    @Override
    public Measurement convertToEntityAttribute(String measurementString) {
        if (measurementString == null) {
            return null;
        }
        var matcher = pattern.matcher(measurementString);
        var measurement = new Measurement();

        if (!matcher.find()) {
            return measurement;
        }

        double amount = NumberUtils.getNumberFromString(matcher.group(1));
        Unit unit = Unit.getUnitFromString(matcher.group(2));
        measurement.setAmount(amount);
        measurement.setUnit(unit);

        return measurement;
    }
}
