package com.example.recipebook.recipe;

import com.google.gson.Gson;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SqlToMongoMigration {


    @Value("${mongo.uri}")
    private String mongoUri;

    @Value("${spring.datasource.url}")
    private String mysqlUrl;

    @Value("${spring.datasource.username}")
    private String mysqlUser;

    @Value("${spring.datasource.password}")
    private String mysqlPass;

    private final Gson gson = new Gson();
    private final Logger LOGGER = LoggerFactory.getLogger(SqlToMongoMigration.class);

    public boolean test() {
        try (var conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass);
             var mongoConn = MongoClients.create(mongoUri)) {
            var recipeCollection = mongoConn.getDatabase("recipebook").getCollection("recipes");
            var relationalRecipes = getRecipesFromSql(conn);
            var mongoRecipes = getRecipesFromMongo(recipeCollection);

            return !mongoRecipes.containsAll(relationalRecipes);
        } catch (Exception e) {
            LOGGER.error("Error encountered: ", e);
            return false;
        }
    }

    public void migrate() {
        try (var mongoClient = MongoClients.create(mongoUri);
             var sqlConn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPass)) {

            var recipeCollection = mongoClient.getDatabase("recipebook").getCollection("recipes");

            var relationalRecipes = getRecipesFromSql(sqlConn);
            insertRelationalRecipesIntoMongo(relationalRecipes, recipeCollection);

        } catch (SQLException e) {
            LOGGER.error("Failed to migrate", e);
        }
    }

    @Transactional
    private void insertRelationalRecipesIntoMongo(List<Recipe> relationalRecipes, MongoCollection<Document> recipeCollection) {
        var jsonRecipes = relationalRecipes
                .parallelStream()
                .map(gson::toJson)
                .map(Document::parse)
                .distinct()
                .collect(Collectors.toList());

        recipeCollection.insertMany(jsonRecipes, new InsertManyOptions().ordered(false));

        LOGGER.info("Inserted {} recipes into recipe collection", jsonRecipes.size());
    }

    private List<Recipe> getRecipesFromMongo(MongoCollection<Document> recipeCollection) {
        return StreamSupport.stream(recipeCollection.find().spliterator(), true)
                .map(Document::toJson)
                .map(s -> gson.fromJson(s, Recipe.class))
                .collect(Collectors.toList());
    }

    private List<Recipe> getRecipesFromSql(Connection conn) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        var statement = conn.createStatement();
        var rs = statement.executeQuery("select * from recipe");
        var cookingTimeConverter = new CookingTime.CookingTimeConverter();
        var nutritionalConverter = new NutritionalInfo.NutritionConverter();

        while (rs.next()) {
            var id = rs.getLong(1);
            var chosen = rs.getBoolean(2);
            var cookingTime = cookingTimeConverter.convertToEntityAttribute(rs.getString(3));
            var imageLocation = rs.getString(4);
            var instructions = rs.getString(5);
            var name = rs.getString(6);
            var nutritionalInfo = nutritionalConverter.convertToEntityAttribute(rs.getString(7));
            var portionSize = rs.getInt(8);
            var publicId = UUID.fromString(rs.getString(9));
            var categories = getListSql(conn, id, "recipe_categories");
            var ingredients = getListSql(conn, id, "recipe_ingredients");

            recipes.add(
                    new Recipe(id, name, imageLocation, chosen, cookingTime, nutritionalInfo, publicId,
                            portionSize, instructions, ingredients, categories));
        }
        return recipes;
    }

    private List<String> getListSql(Connection conn, long id, String tableName) throws SQLException {
        List<String> list = new ArrayList<>();
        var statement = conn.createStatement();
        var rs = statement.executeQuery("select * from " + tableName + " where recipe_id = " + id);

        while (rs.next()) {
            list.add(rs.getString(2));
        }

        return list;
    }


}
