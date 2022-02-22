package com.example.RecipeBook.item;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementConverterTest {
    @ParameterizedTest
    @MethodSource("matcherArguments")
    public void matcherMatchesMatchingMatch(String toMatch, String expectedAmount, String expectedUnit) {
        var matcher = Measurement.MeasurementConverter.pattern.matcher(toMatch);
        assertThat(matcher.find()).isTrue();

        String amount = matcher.group(1);
        String unit = matcher.group(2);

        assertThat(amount).isEqualTo(expectedAmount);
        assertThat(unit).isEqualTo(expectedUnit);
    }

    static Stream<Arguments> matcherArguments() {
        return Stream.of(
                Arguments.of("1/2kg", "1/2", "kg"),
                Arguments.of("1kg", "1", "kg"),
                Arguments.of("0.5kg", "0.5", "kg"),
                Arguments.of("1", "1", "")
        );
    }
}