package chho.ingredientstodishes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gary on 2/29/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeData {
    private RecipeRecord recipes;

    public RecipeRecord getRecipes() {
        return recipes;
    }

    public void setRecipes(RecipeRecord recipes) {
        this.recipes = recipes;
    }
}
