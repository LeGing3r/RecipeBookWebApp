package com.example.RecipeBook.config;

import com.example.RecipeBook.item.MeasurementComparator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public MeasurementComparator measurementComparator() {
        return new MeasurementComparator();
    }

}
