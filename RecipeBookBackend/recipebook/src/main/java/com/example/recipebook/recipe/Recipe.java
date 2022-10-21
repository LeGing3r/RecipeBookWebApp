package com.example.recipebook.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

/**
 * Class wrapping all data necessary for a single recipe
 * <p>
 * TODO ADD WEIGHT OF RECIPE(FREQUENCY OF VISITED)
 *
 * @author Brendan Williams
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipes")
public class Recipe {
    @Id
    String id;
    String name;
    @JsonIgnore
    String imageLocation;
    boolean chosen;
    CookingTime cookingTime;
    NutritionalInfo nutritionalInfo;
    int portionSize;
    String instructions;
    Set<String> ingredients;
    Set<String> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        var r = (Recipe) o;
        if (this.id == null || r.id == null) {
            return this.name.equals(r.name);
        }
        return o != null && getClass().equals(o.getClass()) && id.equals(((Recipe) o).id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return Objects.hash(name);
        }
        return Objects.hash(id);
    }

    public void switchChosen() {
        chosen = !chosen;
    }
}


