package chho.ingredientstodishes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gary on 2/29/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientData {
    private IngredientRecord ingredients;

    public IngredientRecord getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientRecord ingredients) {
        this.ingredients = ingredients;
    }
}
