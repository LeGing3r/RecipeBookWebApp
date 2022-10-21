package com.example.recipebook.recipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookingTimeTest {

    private final CookingTime.CookingTimeConverter converter = new CookingTime.CookingTimeConverter();

    @Test
    public void setPrepAndActualCookingTimesGetProperTotalCookingTime() {
        var cookingTime = new CookingTime();

        cookingTime.setActualCookingTime(10L);
        cookingTime.setPrepTime(5L);

        assertEquals(15L, cookingTime.getTotalCookingTime());
    }

    @Test
    public void setPrepNotActualCookingTimesGetProperTotalCookingTime() {
        var cookingTime = new CookingTime();

        cookingTime.setPrepTime(10L);

        assertEquals(10L, cookingTime.getTotalCookingTime());
    }

    @Test
    public void doNotSetPrepSetActualCookingTimesGetProperTotalCookingTime() {
        var cookingTime = new CookingTime();

        cookingTime.setActualCookingTime(10L);

        assertEquals(10L, cookingTime.getTotalCookingTime());
    }

    @Test
    public void doNotSetPrepNorActualCookingTimesGetProperTotalCookingTime() {
        var cookingTime = new CookingTime();


        assertEquals(0L, cookingTime.getTotalCookingTime());
    }

    @Test
    public void convertStringToCookingTimeReturnsProperCookingTime() {
        var expectedCookingTime = new CookingTime();

        expectedCookingTime.setPrepTime(10L);
        expectedCookingTime.setActualCookingTime(5L);

        var cookingTimeString = "10,5,15";

        var actual = converter.convertToEntityAttribute(cookingTimeString);

        assertEquals(expectedCookingTime.getTotalCookingTime(), actual.getTotalCookingTime());
        assertEquals(expectedCookingTime.getActualCookingTime(), actual.getActualCookingTime());
        assertEquals(expectedCookingTime.getPrepTime(), actual.getPrepTime());
    }

    @Test
    public void convertCookingTimeToStringReturnsProperString() {
        var expectedCookingTimeString = "10,5,15";

        var cookingTime = new CookingTime();
        cookingTime.setPrepTime(10L);
        cookingTime.setActualCookingTime(5L);

        assertEquals(expectedCookingTimeString, cookingTime.toString());
    }

    @Test
    public void convertCookingTimeWithMissingValueToStringReturnsProperString() {
        var expectedCookingTimeString = "10,0,10";

        var cookingTime = new CookingTime();
        cookingTime.setPrepTime(10L);

        assertEquals(expectedCookingTimeString, cookingTime.toString());
    }

}