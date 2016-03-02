package chho.ingredientstodishes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Gary on 2/29/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeRecord {

    private int count;

    private List<RecipeData> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RecipeData> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeData> recipes) {
        this.recipes = recipes;
    }
}
