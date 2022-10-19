package com.example.recipebook;

import com.example.recipebook.recipe.SqlToMongoMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RecipeBookApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeBookApplication.class);

    public static void main(String[] args) {
        var context = SpringApplication.run(RecipeBookApplication.class, args);
        var migration = context.getBean(SqlToMongoMigration.class);
        if (migration.test()) {
            LOGGER.info("Migration needed");
            migration.migrate();
        } else {
            LOGGER.info("Migration skipped");
        }
    }

    @Bean
    public SqlToMongoMigration sqlToMongoMigration() {
        return new SqlToMongoMigration();
    }
}
