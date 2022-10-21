package com.example.shoppinglist.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.shoppinglist.item.Unit.CUP;
import static com.example.shoppinglist.item.Unit.GRAM;
import static com.example.shoppinglist.item.Unit.LITER;
import static com.example.shoppinglist.item.Unit.MILLILITER;
import static com.example.shoppinglist.item.Unit.NONE;
import static com.example.shoppinglist.item.Unit.OUNCE;
import static com.example.shoppinglist.item.Unit.PINT;
import static com.example.shoppinglist.item.Unit.POUND;
import static com.example.shoppinglist.item.Unit.TABLESPOON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasurementComparatorTest {
    private MeasurementComparator measurementComparator;

    @BeforeEach
    public void init() {
        measurementComparator = new MeasurementComparator();
    }

    @Test
    public void combineItemWithMatchingUnit() {
        var item = getItem(1, NONE, 1, NONE);
        var measurement = new Measurement(2, NONE);

        var expected = new Measurement(3, NONE);
        var actual = measurementComparator.addMeasurementToItem(item, measurement);

        assertEquals(expected, actual);
    }

    @Test
    public void combineItemWithMismatchingUnit() {
        var item = getItem(1, NONE, 1, CUP);
        item.staticItem.ratioLitersToNone = 0.24;
        var measurement = new Measurement(1, CUP);

        var expected = new Measurement(2, NONE);
        var actual = measurementComparator.addMeasurementToItem(item, measurement);
        assertEquals(expected, actual);
    }

    @Test
    public void getLargestUnitWhenAlreadyLargestUnit() {
        var expected = new Measurement(3, LITER);
        var actual = measurementComparator.getLargestUnit(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void getLargestAmountAlreadyLargestAmountAndLessThanOne() {
        var expected = new Measurement(0.5, LITER);
        var actual = measurementComparator.getLargestUnit(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void getLargestAmountSmallerUnitSmallAmount() {
        var expected = new Measurement(30, MILLILITER);
        var actual = measurementComparator.getLargestUnit(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void getLargestAmountSmallerUnitLargeAmount() {
        var expected = new Measurement(3, LITER);
        var actual = measurementComparator.getLargestUnit(new Measurement(3000, MILLILITER));
        assertEquals(expected, actual);
    }

    @Test
    public void scaleFromSameUnit() {
        var expected = new Measurement(1, LITER);
        var actual = measurementComparator.scaleFrom(new Measurement(1, LITER), LITER);
        assertEquals(expected, actual);
    }

    @Test
    public void scaleFromSameStandard() {
        var expected = new Measurement(1, LITER);
        var actual = measurementComparator.scaleFrom(new Measurement(1000, MILLILITER), LITER);
        assertEquals(expected, actual);
    }

    @Test
    public void scaleFromDifferentStandardBothBase() {
        var expected = new Measurement(0.96, LITER);
        var actual = measurementComparator.scaleFrom(new Measurement(4, CUP), LITER);
        assertEquals(expected, actual);
    }

    @Test
    public void scaleFromDifferentStandardOneBase() {
        var expected = new Measurement(1.44, LITER);
        var actual = measurementComparator.scaleFrom(new Measurement(96, TABLESPOON), LITER);
        assertEquals(expected, actual);
    }

    @Test
    public void scaleFromDifferentStandardsNeitherBase() {
        var expected = new Measurement(1440, MILLILITER);
        var actual = measurementComparator.scaleFrom(new Measurement(96, TABLESPOON), MILLILITER);
        assertEquals(expected, actual);
    }

    @Test
    public void getClosestWholeAmountNoDefaultMeasurements() {
        var measurement = new Measurement(1, NONE);
        var actual = measurementComparator.getClosestWholeAmount(measurement, null);
        assertEquals(measurement, actual);
    }

    @Test
    public void getClosestWholeAmountOneDefaultMeasurementSameUnit() {
        var measurement = new Measurement(0.5, NONE);
        var measurement2 = new Measurement(1, NONE);
        var staticItem = new StaticItem();
        staticItem.defaultMeasurement = measurement2;
        var actual = measurementComparator.getClosestWholeAmount(measurement, staticItem);
        assertEquals(measurement2, actual);
    }

    @Test
    public void getClosestWholeAmountOneDefaultMeasurementDifferentUnit() {
        var measurement = new Measurement(2, CUP);
        var measurement2 = new Measurement(1, LITER);
        var staticItem = new StaticItem();
        staticItem.defaultMeasurement = measurement2;
        var actual = measurementComparator.getClosestWholeAmount(measurement, staticItem);
        assertEquals(measurement2, actual);
    }

    @Test
    public void getClosestWholeAmountSmallerDefaultMeasurement() {
        var measurement = new Measurement(2, CUP);
        var staticItem = new StaticItem();
        staticItem.defaultMeasurement = new Measurement(1, CUP);
        var expected = new Measurement(1, PINT);
        var actual = measurementComparator.getClosestWholeAmount(measurement, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertImperialVolumeToMetricWeight() {
        var measurement = new Measurement(1, CUP);
        var item = new StaticItem();
        item.density = 500;
        var actual = measurementComparator.convertMeasurementUnit(measurement, GRAM, item);
        var expected = new Measurement(120, GRAM);
        assertEquals(expected, actual);
    }

    @Test
    public void convertMetricVolumeToMetricWeight() {
        var measurement = new Measurement(1, LITER);
        var item = new StaticItem();
        item.density = 500;
        var actual = measurementComparator.convertMeasurementUnit(measurement, GRAM, item);
        var expected = new Measurement(500, GRAM);
        assertEquals(expected, actual);
    }

    @Test
    public void convertMetricVolumeToImperialWeight() {
        var measurement = new Measurement(11.34, LITER);
        var item = new StaticItem();
        item.density = 500;
        var actual = measurementComparator.convertMeasurementUnit(measurement, OUNCE, item);
        var expected = new Measurement(200, OUNCE);
        assertEquals(expected, actual);
    }

    @Test
    public void convertImperialVolumeToImperialWeight() {
        var measurement = new Measurement(47.25, CUP);
        var item = new StaticItem();
        item.density = 1000;
        var actual = measurementComparator.convertMeasurementUnit(measurement, POUND, item);
        var expected = new Measurement(25, POUND);
        assertEquals(expected, actual);
    }

    @Test
    public void metricToMetricNoChange() {
        var measurement = new Measurement(1, LITER);
        assertEquals(measurementComparator.convertToMetric(measurement), measurement);
    }

    @Test
    public void convertImperialToMetricWeight() {
        var weightMeasurement = new Measurement(1, POUND);
        var expectedWeight = new Measurement(453.6, GRAM);
        assertEquals(expectedWeight, measurementComparator.convertToMetric(weightMeasurement));
    }

    @Test
    public void convertImperialToMetricVolume() {
        var volumeMeasurement = new Measurement(1, CUP);
        var expectedVolume = new Measurement(0.24, LITER);
        assertEquals(expectedVolume, measurementComparator.convertToMetric(volumeMeasurement));
    }

    @Test
    public void convertNoneUnitNoChange() {
        var measurement = new Measurement(1, NONE);
        assertEquals(measurement, measurementComparator.convertToMetric(measurement));
    }

    @Test
    public void givenOneCupBananaToBananaReturnsOneBanana() {
        var measurement = new Measurement(0.236, LITER);
        var staticItem = new StaticItem();
        staticItem.ratioGramsToNone = 120.0;
        staticItem.density = 951;
        var expected = new Measurement(1.8703, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertGramsToNoUnit() {
        var measurement = new Measurement(228, GRAM);
        var staticItem = new StaticItem();
        staticItem.ratioGramsToNone = 120.0;
        staticItem.density = 951;
        var expected = new Measurement(1.9, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertGramsToNoUnitWithRatioToLiters() {
        var measurement = new Measurement(228, GRAM);
        var staticItem = new StaticItem();
        staticItem.ratioLitersToNone = 0.126;
        staticItem.density = 951;
        var expected = new Measurement(1.9027590005507984, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertLitersToNoUnitWithRatioToLiters() {
        var measurement = new Measurement(0.25, LITER);
        var staticItem = new StaticItem();
        staticItem.ratioLitersToNone = 0.13;
        staticItem.density = 951;
        var expected = new Measurement(1.923076923076923, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertNoUnitWithRatioToLitersToLiters() {
        var measurement = new Measurement(1, NONE);
        var staticItem = new StaticItem();
        staticItem.ratioLitersToNone = 0.13;
        staticItem.density = 951;
        var expected = new Measurement(0.13, LITER);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, LITER, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertNoUnitWithRatioToLitersToGrams() {
        var measurement = new Measurement(230, GRAM);
        var staticItem = new StaticItem();
        staticItem.ratioLitersToNone = 0.13;
        staticItem.density = 951;
        var expected = new Measurement(1.8603898730081694, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    @Test
    public void convertNoUnitWithRatioToGramsToGrams() {
        var measurement = new Measurement(240, GRAM);
        var staticItem = new StaticItem();
        staticItem.ratioGramsToNone = 120.0;
        staticItem.density = 951;
        var expected = new Measurement(2, NONE);
        var actual = measurementComparator.combineBaseUnitWithNoUnit(measurement, NONE, staticItem);
        assertEquals(expected, actual);
    }

    private Item getItem(double initialAmount, Unit initialUnit, double defaultAmount, Unit defaultUnit, Measurement... defaultMeasurements) {
        var item = new Item();
        var staticItem = new StaticItem();

        staticItem.defaultMeasurement = new Measurement(defaultAmount, defaultUnit);

        item.measurement = new Measurement(initialAmount, initialUnit);
        item.staticItem = staticItem;

        return item;
    }
}
