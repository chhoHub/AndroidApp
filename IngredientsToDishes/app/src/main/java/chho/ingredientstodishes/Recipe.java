package chho.ingredientstodishes;

import java.io.Serializable;

/**
 * Created by Gary on 2/13/2016.
 */
public class Recipe implements Serializable {

    private String name;
    private String[] ingredients;
    private String[] directions;
    private int cookingtime;

    public Recipe(String name, String[] ingredients, String[] directions, int cookingtime) {
        this.name = name;
        this.ingredients = ingredients;
        this.directions = directions;
        this.cookingtime = cookingtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    public int getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(int cookingtime) {
        this.cookingtime = cookingtime;
    }
}
