package chho.ingredientstodishes.data;

import java.util.List;

import chho.ingredientstodishes.Recipe;

/**
 * Created by Gary on 2/29/2016.
 */
public class RecipeRecord {
    private List<Recipe> recipe;

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }
}
